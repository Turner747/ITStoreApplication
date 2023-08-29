package com.assessmenttwo.app.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {


    @RequestMapping("/401")
    public String handle401() {
        return "error/error-401";
    }

    @RequestMapping("/404")
    public String handle404() {
        return "error/error-404";
    }

    @RequestMapping("/500")
    public String handle500() {
        return "error/error-500";
    }
}
