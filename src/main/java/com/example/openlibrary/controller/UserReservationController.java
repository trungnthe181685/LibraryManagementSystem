package com.example.openlibrary.controller;

import com.example.openlibrary.model.Book;
import com.example.openlibrary.model.Reservation;
import com.example.openlibrary.model.User;
import com.example.openlibrary.repository.BookRepository;
import com.example.openlibrary.repository.ReservationRepository;
import com.example.openlibrary.repository.UserRepository;
import com.example.openlibrary.service.UserService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/user/reservations")
public class UserReservationController {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private ReservationRepository reservationRepository;
	
	
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/borrow/{bookId}")
	public String borrowBook(@PathVariable Long bookId, Model model, @AuthenticationPrincipal Object principal, RedirectAttributes redirectAttrs) {
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

		Book book = bookRepository.findById(bookId).orElse(null);
		if (book == null || book.getAvailableCopies() <= 0) {
			redirectAttrs.addFlashAttribute("message", "Book not available.");
			return "redirect:/books";
		}

		// Create a reservation
		Reservation reservation = new Reservation();
		reservation.setBook(book);
		reservation.setUser(user);
		reservation.setCreatedDate(LocalDate.now());
		reservation.setStatus(Reservation.Status.RESERVED);

		reservationRepository.save(reservation);

		int activeReservations = reservationRepository.countActiveReservationsByBook(book);
		book.setAvailableCopies(Math.max(book.getTotalCopies() - activeReservations, 0));
		book.setStock(book.getAvailableCopies() > 0 ? "In Stock" : "Out of Stock");
		bookRepository.save(book);

		redirectAttrs.addFlashAttribute("message", "Book reserved successfully!");
		return "redirect:/books";
	}
}
