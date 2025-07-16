package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.model.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;

	// Login form
	@GetMapping("/login")
	public String showLoginForm() {
		return "login";
	}

	// Login handler
	@PostMapping("/login")
	public String login(@RequestParam String email, @RequestParam String password, Model model) {
		User user = userRepository.findByEmailAndPassword(email, password);
		if (user != null) {
			model.addAttribute("user", user);
			return "redirect:/home";
		} else {
			model.addAttribute("error", "Invalid credentials");
			return "login";
		}
	}

	// Signup form
	@GetMapping("/signup")
	public String showSignupForm() {
		return "signup";
	}

	// Signup handler
	@PostMapping("/signup")
	public String signup(@ModelAttribute User user, Model model) {
		userRepository.save(user);
		return "redirect:/login";
	}

	// View all books
	@GetMapping("/books")
	public String viewBooks(Model model) {
		List<Book> books = bookRepository.findAll();
		model.addAttribute("books", books);
		return "books"; // books.html
	}

	// Create book (staff/admin)
	@PostMapping("/books")
	public String createBook(@ModelAttribute Book book) {
		bookRepository.save(book);
		return "redirect:/books";
	}

	// Update book (staff/admin)
	@PostMapping("/books/update/{id}")
	public String updateBook(@PathVariable Long id, @ModelAttribute Book bookDetails) {
		Book book = bookRepository.findById(id).orElseThrow();
		book.setBookName(bookDetails.getBookName());
		book.setStock(bookDetails.getStock());
		book.setDescription(bookDetails.getDescription());
		book.setTotalCopies(bookDetails.getTotalCopies());
		book.setAvailableCopies(bookDetails.getAvailableCopies());
		book.setImageURL(bookDetails.getImageURL());
		book.setAuthor(bookDetails.getAuthor());
		book.setBookCategory(bookDetails.getBookCategory());
		bookRepository.save(book);
		return "redirect:/books";
	}

	// Delete book (admin)
	@GetMapping("/books/delete/{id}")
	public String deleteBook(@PathVariable Long id) {
		bookRepository.deleteById(id);
		return "redirect:/books";
	}
}
