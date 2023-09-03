package com.assessmenttwo.app.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/400")
    public String handle400() {
        return "error/error-400";
    }

    @RequestMapping("/401")
    public String handle401() {
        return "error/error-401";
    }

    @RequestMapping("/404")
    public String handle404() {
        return "error/error-404";
    }

    @RequestMapping("/500")
    public String handle500(HttpServletRequest request, Model model) {
        Exception e = (Exception) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        model.addAttribute("exception", e);
        return "error/error-500";
    }
}
