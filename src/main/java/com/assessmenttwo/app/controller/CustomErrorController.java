package com.assessmenttwo.app.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.UNAUTHORIZED.value()) {
                return "error-401";
            }
            else if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error-404";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error-500";
            }
        }
        return "error";
    }

    @RequestMapping("/401")
    public String handle401() {
        return "error-401"; // This should match the name of your Thymeleaf or JSP template
    }

    @RequestMapping("/404")
    public String handle404() {
        return "error-404"; // This should match the name of your Thymeleaf or JSP template
    }

    @RequestMapping("/500")
    public String handle500() {
        return "error-500"; // This should match the name of your Thymeleaf or JSP template
    }
}
