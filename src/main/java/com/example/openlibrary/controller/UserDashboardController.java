package com.example.openlibrary.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.openlibrary.model.BorrowRecord;
import com.example.openlibrary.model.Notification;
import com.example.openlibrary.model.Reservation;
import com.example.openlibrary.model.User;
import com.example.openlibrary.repository.BorrowRecordRepository;
import com.example.openlibrary.repository.NotificationRepository;
import com.example.openlibrary.repository.ReservationRepository;
import com.example.openlibrary.repository.UserRepository;
import com.example.openlibrary.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserDashboardController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BorrowRecordRepository borrowRecordRepo;
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	
	@GetMapping("/cart")
	public String cart() {
		return "cart";
	}

	@GetMapping("/profile")
	public String profile(@RequestParam(value = "tab", defaultValue = "info") String tab,
	                      Model model,
	                      @AuthenticationPrincipal Object principal) {
	    if (principal == null)
	        return "redirect:/login";

	    User user = null;

	    if (principal instanceof org.springframework.security.oauth2.core.user.DefaultOAuth2User oauthUser) {
	        String email = oauthUser.getAttribute("email");
	        user = userRepository.findByEmail(email);
	    } else if (principal instanceof org.springframework.security.core.userdetails.User springUser) {
	        String email = springUser.getUsername();
	        user = userRepository.findByEmail(email);
	    }

	    if (user == null)
	        return "redirect:/login";

	    model.addAttribute("user", user);
	    model.addAttribute("tab", tab);

	    if ("reservations".equals(tab)) {
	        List<Reservation> reservations = user.getReservations(); // adjust this line to your setup
	        Map<Long, List<BorrowRecord>> recordsMap = new HashMap<>();
	        for (Reservation reservation : reservations) {
	            List<BorrowRecord> records = borrowRecordRepo.findByReservation(reservation);
	            recordsMap.put(reservation.getReservationID(), records);
	        }
	        model.addAttribute("reservations", reservations);
	        model.addAttribute("recordsMap", recordsMap);
	    } else if ("notifications".equals(tab)) {
	        List<Notification> notifications = notificationRepository.findByUserOrderByCreatedAtDesc(user);
	        model.addAttribute("notifications", notifications);
	    }


	    return "profile";
	}



	@PostMapping("/profile/update")
	public String updateProfile(@ModelAttribute("user") User updatedUser, @AuthenticationPrincipal Object principal,
	                            RedirectAttributes redirectAttrs) {
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

	    // Update fields
	    user.setName(updatedUser.getName());
	    user.setEmail(updatedUser.getEmail());
	    user.setPhone(updatedUser.getPhone());
	    user.setGender(updatedUser.getGender());
	    user.setDob(updatedUser.getDob());
	    user.setAvatar(updatedUser.getAvatar());
	    if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
	        user.setPassword(updatedUser.getPassword());
	    }

	    userService.saveUser(user);

	    redirectAttrs.addFlashAttribute("message", "Profile updated successfully!");
	    return "redirect:/profile";
	}
	
	@PostMapping("/notifications/mark-all-read")
	public String markAllAsRead(@AuthenticationPrincipal Object principal,
	                            RedirectAttributes redirectAttrs) {
	    if (principal == null)
	        return "redirect:/login";

	    User user = null;

	    if (principal instanceof org.springframework.security.oauth2.core.user.DefaultOAuth2User oauthUser) {
	        String email = oauthUser.getAttribute("email");
	        user = userRepository.findByEmail(email);
	    } else if (principal instanceof org.springframework.security.core.userdetails.User springUser) {
	        String email = springUser.getUsername();
	        user = userRepository.findByEmail(email);
	    }

	    if (user == null)
	        return "redirect:/login";

	    // Mark all unread notifications as read
	    List<Notification> unread = notificationRepository.findByUserAndIsReadFalse(user);
	    for (Notification n : unread) {
	        n.setRead(true);
	    }
	    notificationRepository.saveAll(unread);

	    redirectAttrs.addAttribute("tab", "notifications");
	    return "redirect:/profile";
	}


}
