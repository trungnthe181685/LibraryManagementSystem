package com.example.openlibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.openlibrary.model.User;
import com.example.openlibrary.repository.UserRepository;
import com.example.openlibrary.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginHandler(@RequestParam String email,
                               @RequestParam String password,
                               Model model,
                               HttpSession session) {
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
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
        newUser.setPassword(password);
        userService.saveUser(newUser);

        return "redirect:/login";
    }

    @GetMapping("/forgotpassword")
    public String forgotPassword() {
        return "ForgotPassword";
    }

    @GetMapping("/forgotpassword2")
    public String forgotPassword2() {
        return "forgotpassword2";
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
