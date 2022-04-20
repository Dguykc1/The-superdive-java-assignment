package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ResultControllerClass {
    @RequestMapping(value = "/result",method = RequestMethod.GET)
    public String getPage(){
        return "result";
    }
}
