package com.example.openlibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.openlibrary.model.User;
import com.example.openlibrary.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserDashboardController {
	@GetMapping("/cart")
	public String cart() {
		return "cart";
	}

	@Autowired
	private UserService userService;

	@GetMapping("/profile")
	public String profile(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
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
