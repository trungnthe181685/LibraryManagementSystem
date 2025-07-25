package com.example.openlibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.openlibrary.model.User;
import com.example.openlibrary.repository.UserRepository;
import com.example.openlibrary.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserDashboardController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/cart")
	public String cart() {
		return "cart";
	}

	@GetMapping("/profile")
	public String profile(Model model, @AuthenticationPrincipal Object principal) {
	    if (principal == null)
	        return "redirect:/login";

	    User user = null;

	    if (principal instanceof org.springframework.security.oauth2.core.user.DefaultOAuth2User oauthUser) {
	        // Get user by email (OAuth2)
	        String email = oauthUser.getAttribute("email");
	        user = userRepository.findByEmail(email); // replace with your actual method
	    } else if (principal instanceof org.springframework.security.core.userdetails.User springUser) {
	        // Get user by email (form login)
	        String email = springUser.getUsername();
	        user = userRepository.findByEmail(email); // same here
	    }

	    if (user == null)
	        return "redirect:/login";

	    model.addAttribute("user", user);
	    return "profile";
	}


	@PostMapping("/profile/update")
	public String updateProfile(@ModelAttribute("user") User updatedUser, HttpSession session,
	                            RedirectAttributes redirectAttrs) {
	    User sessionUser = (User) session.getAttribute("user");
	    if (sessionUser == null)
	        return "redirect:/login";

	    // Update fields
	    sessionUser.setName(updatedUser.getName());
	    sessionUser.setEmail(updatedUser.getEmail());
	    if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
	        sessionUser.setPassword(updatedUser.getPassword());
	    }

	    userService.saveUser(sessionUser);

	    redirectAttrs.addFlashAttribute("message", "Profile updated successfully!");
	    return "redirect:/profile";
	}

}
