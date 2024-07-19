package com.rungroup.web.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorControllerImpl implements ErrorController {

    public String statusCodeDescription(int statusCode) {
        return switch (statusCode) {
            case 401 -> "You are not logged in.";
            case 403 -> "You have no permission do perform this operation.";
            case 404 -> "We could not find this page.";
            default -> "Error";
        };
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        //String message = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            model.addAttribute("status", statusCode);
            String description = statusCodeDescription(statusCode);
            model.addAttribute("description", description);
        }
        return "custom-error";
    }
}
