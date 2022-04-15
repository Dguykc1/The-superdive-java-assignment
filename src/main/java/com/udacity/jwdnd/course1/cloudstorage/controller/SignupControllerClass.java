package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.UserModelClass;
import com.udacity.jwdnd.course1.cloudstorage.services.UserClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/signup")
public class SignupControllerClass {
  private final  UserClientService userClientService;


    @PostMapping()
    public String signupUser(@ModelAttribute UserModelClass user, Model model) {
        String signupErrorMessage = null;

        if (!userClientService.isUsernameAvailable(user.getUsername())) {
            signupErrorMessage = "The username already exists.";
        }

        if (signupErrorMessage == null) {
            int rowsAdded = userClientService.createUser(user);
            if (rowsAdded < 0) {
                signupErrorMessage = "There was an error signing you up. Please try again.";
            }
        }

        if (signupErrorMessage == null) {
            model.addAttribute("displaySuccessfulSignUp", true);

return "login";
        } else {
            model.addAttribute("signupErrorMessage", signupErrorMessage);
        }

        return "signup";
    }
    public SignupControllerClass(UserClientService userClientService) {
        this.userClientService = userClientService;
    }


    @GetMapping()
    public String getSignUpPage(Model signUpmodel){
        return "signup";
    }

}
