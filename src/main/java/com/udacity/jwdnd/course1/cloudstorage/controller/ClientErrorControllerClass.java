package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ClientErrorControllerClass implements ErrorController {
    @GetMapping("/error")
    public String handleError(HttpServletRequest httpServletRequest){
       Object status = httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null){
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()){
                return "error-500";
            }
            if (statusCode == HttpStatus.NOT_FOUND.value()){
                return "error-404";

            }
        }
        return "error";
    }




    @Override
    public String getErrorPath() {
        return null;
    }
}
