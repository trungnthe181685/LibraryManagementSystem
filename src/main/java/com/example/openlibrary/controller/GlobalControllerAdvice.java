package com.example.openlibrary.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.openlibrary.model.User;
import com.example.openlibrary.service.UserService;

import jakarta.servlet.http.HttpSession;



@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public User getCurrentUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }
}