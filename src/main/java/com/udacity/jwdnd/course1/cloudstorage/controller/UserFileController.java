package com.udacity.jwdnd.course1.cloudstorage.controller;
import com.udacity.jwdnd.course1.cloudstorage.model.UserFileClassModel;
import com.udacity.jwdnd.course1.cloudstorage.model.UserModelClass;

import com.udacity.jwdnd.course1.cloudstorage.services.ReplyActionMessages;

import com.udacity.jwdnd.course1.cloudstorage.services.UserClientService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserFileService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/files")
public class UserFileController {
    private final Logger logger = LoggerFactory.getLogger(UserFileController.class);

    private  final UserFileService userFileService;
    private final UserClientService userService;
    private final ReplyActionMessages userActionMessages;

    public UserFileController(UserFileService userFileService, UserClientService userService, ReplyActionMessages userActionMessages) {
        this.userFileService = userFileService;
        this.userService = userService;
        this.userActionMessages = userActionMessages;
    }
    @RequestMapping(value="/{fileId}/view", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    ResponseEntity<Resource> downloadFile(@PathVariable Integer fileId){
        try {
            UserFileClassModel file = userFileService.findById(fileId);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(file.getContenttype()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(new ByteArrayResource(file.getFiledata()));

        }catch (Exception e){
            logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }

    }
    @PostMapping("/upload")
    public String uploadUserFile(@RequestParam("fileUpload") MultipartFile multipartFile,
                             UserFileClassModel file,
                             RedirectAttributes redirectAttributes,
                             Authentication authentication) throws Exception{

        UserModelClass user = userService.getUser(authentication.getName());
        Integer userId = user.getUserid();
        file.setUserid(userId);

        if (userFileService.findOneFile(multipartFile.getOriginalFilename()) !=null ){
            redirectAttributes.addFlashAttribute("errorMessage", userActionMessages.fileNameAlreadyExists);

        }else if (multipartFile.getOriginalFilename().isEmpty()){
            redirectAttributes.addFlashAttribute("errorMessage", userActionMessages.didNotSelectAFile);

        }
        else {

            try {
                userFileService.upload(file, multipartFile);

                redirectAttributes.addFlashAttribute("successMessage", userActionMessages.fileUploadSuccess);
                return "redirect:/result";

            }catch (Exception e){
                logger.error(e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", userActionMessages.fileUploadFailed);
                return "redirect:/result";
            }

        }

        return "redirect:/result";
    }

    @RequestMapping("/{fileId}/delete")
    public String deleteCredential(@PathVariable Integer fileId, RedirectAttributes redirectAttributes){

        try{
            userFileService.delete(fileId);

            redirectAttributes.addFlashAttribute("successMessage", userActionMessages.fileDeleteSuccessful);
            return "redirect:/result";

        }catch (Exception e){
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", userActionMessages.fileDeleteNotSuccessful);
            return "redirect:/result";
        }

    }



}
