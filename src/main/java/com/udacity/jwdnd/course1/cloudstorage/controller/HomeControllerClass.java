package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.UserCredentialsClass;
import com.udacity.jwdnd.course1.cloudstorage.model.UserModelClass;
import com.udacity.jwdnd.course1.cloudstorage.model.UserNoteModel;
import com.udacity.jwdnd.course1.cloudstorage.services.*;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeControllerClass {
    UserNoteService userNoteService;
    UserFileService userFileService;
    UserClientService userClientService;
    UserCredentialService userCredentialService;
    EncryptionService userEncryptionService;

    public HomeControllerClass(UserNoteService userNoteService, UserFileService userFileService, UserClientService userClientService, UserCredentialService userCredentialService, EncryptionService userEncryptionService) {
        this.userNoteService = userNoteService;
        this.userFileService = userFileService;
        this.userClientService = userClientService;
        this.userCredentialService = userCredentialService;
        this.userEncryptionService = userEncryptionService;
    }

    @GetMapping
    public String getHomePage(Model model, Authentication authentication){
        UserModelClass user = userClientService.getUser(authentication.getName());

        String emptyString = "";
        UserCredentialsClass credential = new UserCredentialsClass(0, emptyString, emptyString, emptyString, emptyString, user.getUserid());
        UserNoteModel note = new UserNoteModel(0, emptyString, emptyString, user.getUserid());

        model.addAttribute("clientNotes",userNoteService.getNotes(user.getUserid()));
        model.addAttribute("listOfCredentials", userCredentialService.listAllCredentials(user.getUserid()));
        model.addAttribute("itemInCredential", credential);
        model.addAttribute("encryptionService", userEncryptionService);
        model.addAttribute("itemInTheNote", note);
        model.addAttribute("listOfFiles", userFileService.listAll(user.getUserid()));

        return "home";
    }

}
