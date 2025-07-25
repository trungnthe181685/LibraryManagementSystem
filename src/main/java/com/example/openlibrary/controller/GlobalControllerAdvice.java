package com.example.openlibrary.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.openlibrary.model.User;
import com.example.openlibrary.service.UserService;



@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public User getCurrentUser(Principal principal) {
        if (principal == null) {
            System.out.println("Principal is null");
        	return null;
        }

        String email;

        if (principal instanceof OAuth2AuthenticationToken oauth2) {
            Map<String, Object> attributes = oauth2.getPrincipal().getAttributes();
            email = (String) attributes.get("email");
            System.out.println("OAuth2 login email: " + email);
        } else {
            email = principal.getName();
            System.out.println("Form login email: " + email);
        }

        User user = userService.findByEmail(email);
        System.out.println("Loaded user: " + user);
        return user;
    }
}