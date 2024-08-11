package com.rungroup.web.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CustomErrorController implements ErrorController {

    public final String NO_DESCRIPTION_ERROR = "Error";
    private final Map<Integer, String> statusCodeMessages = new HashMap<>();

    public CustomErrorController() {
        statusCodeMessages.put(401, "You are not logged in.");
        statusCodeMessages.put(403, "You are not permitted to do this.");
        statusCodeMessages.put(404, "We could not find this page");
        statusCodeMessages.put(415, "This content type is not supported.");
    }

    public String statusCodeDescription(int statusCode) {
        String description = statusCodeMessages.get(statusCode);
        if (description == null) {
            return NO_DESCRIPTION_ERROR;
        }
        return description;
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object requestStatus = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String requestMessage = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        if (requestStatus != null) {
            int statusCode = Integer.parseInt(requestStatus.toString());
            model.addAttribute("status", statusCode);
            //String message = statusCodeDescription(statusCode);
            model.addAttribute("message", requestMessage);
        }
        return "custom-error";
    }
}
