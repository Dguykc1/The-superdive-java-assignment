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
@RequestMapping("/notes")
public class UserNoteControllerClass {
    private final Logger logger = LoggerFactory.getLogger(UserNoteControllerClass.class);

    UserNoteService noteService;
     UserClientService userService;
     ReplyActionMessages userActionMessages;

    public UserNoteControllerClass(UserNoteService noteService,
                                   UserClientService userService,
                                   ReplyActionMessages userActionMessages) {

        this.noteService = noteService;
        this.userService = userService;
        this.userActionMessages = userActionMessages;
    }

    @PostMapping
    public String createOrUpdateNote(@ModelAttribute UserNoteModel note,
                                     Authentication authentication,
                                     RedirectAttributes redirectAttributes){

        UserModelClass user = userService.getUser(authentication.getName());

        Integer userId = user.getUserid();
        note.setUserid(userId);

        if (noteService.findByTitleAndDesc(note.getNoteTitle(), note.getNoteDescription()) != null){
            redirectAttributes.addFlashAttribute("errorMessage", userActionMessages.noteAlreadyExists);
        }else

        if (note.getNoteid() > 0){

            try {
                noteService.update(note);

                redirectAttributes.addFlashAttribute("successMessage", userActionMessages.noteUpdateSuccessful);
                return "redirect:/result";

            }catch (Exception e){
                logger.error(e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", userActionMessages.noteCreationNotSuccessful);
                return "redirect:/result";
            }

        }else {

            try {
                noteService.createNote(note);

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

    @RequestMapping("/{noteId}/delete")
    public String deleteNote(@PathVariable Integer noteId, RedirectAttributes redirectAttributes){
        try {

            noteService.deleteNote(noteId);

            redirectAttributes.addFlashAttribute("successMessage", userActionMessages.noteDeleteSuccessful);
            return "redirect:/result";

        }catch (Exception e){
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", userActionMessages.noteDeleteNotSuccessful);
            return "redirect:/result";
        }

    }
}
