package com.udacity.jwdnd.course1.cloudstorage.controller;
import com.udacity.jwdnd.course1.cloudstorage.model.UserModelClass;
import com.udacity.jwdnd.course1.cloudstorage.model.UserNoteModel;
import com.udacity.jwdnd.course1.cloudstorage.services.ReplyActionMessages;
import com.udacity.jwdnd.course1.cloudstorage.services.UserClientService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserNoteService;
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
@RequestMapping("/clientNotes")
public class UserNoteControllerClass {
    private final Logger logger = LoggerFactory.getLogger(UserNoteControllerClass.class);

    UserNoteService userNoteService;
     UserClientService userClientService;
     ReplyActionMessages userActionMessages;

    public UserNoteControllerClass(UserNoteService noteService,
                                   UserClientService userService,
                                   ReplyActionMessages userActionMessages) {

        this.userNoteService = noteService;
        this.userClientService = userService;
        this.userActionMessages = userActionMessages;
    }

    @PostMapping
    public String createOrUpdateNote(@ModelAttribute UserNoteModel noteModel,
                                     Authentication authentication,
                                     RedirectAttributes redirectAttributes){

        UserModelClass user = userClientService.getUser(authentication.getName());

        Integer userid = user.getUserid();
        noteModel.setUserid(userid);

        if (userNoteService.findByTitleAndDesc(noteModel.getNotetitle(), noteModel.getNotedescription()) != null){
            redirectAttributes.addFlashAttribute("errorMessage", userActionMessages.noteAlreadyExists);
        }else

        if (noteModel.getNoteid() > 0){

            try {
                userNoteService.update(noteModel);

                redirectAttributes.addFlashAttribute("successMessage", userActionMessages.noteUpdateSuccessful);
                return "redirect:/result";

            }catch (Exception e){
                logger.error(e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", userActionMessages.noteCreationNotSuccessful);
                return "redirect:/result";
            }

        }else {

            try {
                userNoteService.createNote(noteModel);

                redirectAttributes.addFlashAttribute("successMessage", userActionMessages.noteCreationSuccessful);
                return "redirect:/result";

            }catch (Exception e){
                logger.error(e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", userActionMessages.noteCreationNotSuccessful);
                return "redirect:/result";
            }

        }

        return "redirect:/result";
    }

    @RequestMapping("/{noteid}/delete")
    public String deleteNote(@PathVariable Integer noteid, RedirectAttributes redirectAttributes){
        try {

            userNoteService.deleteNote(noteid);

            redirectAttributes.addFlashAttribute("successMessage", userActionMessages.noteDeleteSuccessful);
            return "redirect:/result";

        }catch (Exception e){
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", userActionMessages.noteDeleteNotSuccessful);
            return "redirect:/result";
        }

    }
}
