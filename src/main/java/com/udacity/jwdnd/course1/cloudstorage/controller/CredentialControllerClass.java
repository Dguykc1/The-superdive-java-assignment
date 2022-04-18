package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.UserCredentialsClass;
import com.udacity.jwdnd.course1.cloudstorage.model.UserModelClass;

import com.udacity.jwdnd.course1.cloudstorage.services.ReplyActionMessages;

import com.udacity.jwdnd.course1.cloudstorage.services.UserClientService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserCredentialService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/credentials")
public class CredentialControllerClass {
    private final Logger logger = LoggerFactory.getLogger(CredentialControllerClass.class);

    private final UserCredentialService credentialService;
    private final UserClientService userService;
    private final ReplyActionMessages userActionMessages;

    public CredentialControllerClass(UserCredentialService credentialService,
                                     UserClientService userService,
                                     ReplyActionMessages userActionMessages) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.userActionMessages = userActionMessages;
    }
    @RequestMapping("/{credentialid}/delete")
    public String deleteCredential(@PathVariable Integer credentialid, RedirectAttributes redirectAttributes){

        try {
            credentialService.deleteCredential(credentialid);
            redirectAttributes.addFlashAttribute("successMessage", userActionMessages.credentialDeletionSuccessful);
            return "redirect:/result";

        }catch (Exception e){
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", userActionMessages.credentialCreationNotSuccessful);
            return "redirect:/result";
        }


    }
    @PostMapping
    public String createOrUpdateCredential(@ModelAttribute UserCredentialsClass credential,
                                           Authentication authentication,
                                           RedirectAttributes redirectAttributes){

        UserModelClass user = userService.getUser(authentication.getName());
        Integer userid = user.getUserid();
        credential.setUserid(userid);

        if (credential.getCredentialid()!=null){
            try {
                credentialService.create(credential);
                redirectAttributes.addFlashAttribute("successMessage", userActionMessages.credentialUpdateSuccessful);
                return "redirect:/result";

            }catch (Exception e){
                logger.error(e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", userActionMessages.credentialUpdateNotSuccessful);
                return "redirect:/result";
            }

        }else {
            try {
                credentialService.create(credential);
                redirectAttributes.addFlashAttribute("successMessage", userActionMessages.credentialCreationSuccessful);
                return "redirect:/result";

            }catch (Exception e){
                logger.error(e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", userActionMessages.credentialCreationNotSuccessful);
                return "redirect:/result";
            }
        }

    }





}
