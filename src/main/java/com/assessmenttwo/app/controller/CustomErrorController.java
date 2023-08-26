package com.assessmenttwo.app.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/401")
    public String handle401() {
        return "error/error-401"; // This should match the name of your Thymeleaf or JSP template
    }

    @RequestMapping("/404")
    public String handle404() {
        return "error/error-404"; // This should match the name of your Thymeleaf or JSP template
    }

    @RequestMapping("/500")
    public String handle500() {
        return "error/error-500"; // This should match the name of your Thymeleaf or JSP template
    }
}
