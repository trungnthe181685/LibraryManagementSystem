package com.example.openlibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelpController {

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/help/send")
    @ResponseBody
    public ResponseEntity<String> sendHelpMessage(@RequestParam String email,
                                                  @RequestParam String message) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom("aidlsloth@gmail.com");
            mail.setTo("tuankietle1209@gmail.com"); // Replace with real admin email
            mail.setSubject("❓ Help Request from " + email);
            mail.setText(message);
            mailSender.send(mail);
            return ResponseEntity.ok("Message sent");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to send: " + e.getMessage());
        }
    }
    @GetMapping("/test-mail")
    @ResponseBody
    public String testMail() {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("aidlsloth@gmail.com");
        mail.setTo("tuankietle1209@gmail.com");
        mail.setSubject("📬 Test from OpenLibrary");
        mail.setText("If you received this, your mail config is working!");

        mailSender.send(mail);
        return "Mail sent!";
    }
}
