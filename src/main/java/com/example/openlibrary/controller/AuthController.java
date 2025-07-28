package com.example.openlibrary.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.openlibrary.model.User;
import com.example.openlibrary.repository.UserRepository;
import com.example.openlibrary.service.UserService;

@Controller
public class AuthController {
    @Autowired
    private JavaMailSender mailSender;
	
    @Autowired
    private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/signup")
    public String showSignupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String name,
    					 @RequestParam String email,
    					 @RequestParam String password,
    					 Model model) {
        if(userService.findByEmail(email) != null) {
        	model.addAttribute("error", "Email already exists");
        	return "signup";
        }
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRole("member");
        userService.saveUser(newUser);

        return "redirect:/login";
    }
    

    @PostMapping("/forgotpassword")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            model.addAttribute("error", "No account found with that email.");
            return "forgotpassword";
        }

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        userRepository.save(user);

        // Send email
        String resetLink = "http://localhost:8080/resetpassword?token=" + token;
        sendEmail(user.getEmail(), resetLink);

        model.addAttribute("message", "A reset link has been sent to your email.");
        return "forgotpassword";
    }

    private void sendEmail(String to, String link) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Password Reset Request");
        message.setText("Click the link below to reset your password:\n" + link);
        mailSender.send(message);
    }

    @GetMapping("/resetpassword")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        User user = userRepository.findByResetToken(token);
        if (user == null) {
            model.addAttribute("error", "Invalid reset token.");
            return "resetpassword";
        }

        model.addAttribute("token", token);
        return "resetpassword";
    }

    @PostMapping("/resetpassword")
    public String processResetPassword(@RequestParam("token") String token,
                                       @RequestParam("password") String password,
                                       Model model) {
        User user = userRepository.findByResetToken(token);
        if (user == null) {
            model.addAttribute("error", "Invalid token.");
            return "resetpassword";
        }

        user.setPassword(password); // ✅ encode if needed
        user.setResetToken(null);   // ✅ clear token
        userRepository.save(user);

        model.addAttribute("message", "Password reset successful.");
        return "login";
    }

    @GetMapping("/forgotpassword")
    public String forgotPassword() {
        return "forgotpassword";
    }

    @GetMapping("/GoogleLogin")
    public String googleLoginSuccess(@AuthenticationPrincipal OAuth2User oauth2User,
                                     Model model) {
        if (oauth2User != null) {
            model.addAttribute("name", oauth2User.getAttribute("name"));
        }
        return "redirect:/home";
    }
    
}
