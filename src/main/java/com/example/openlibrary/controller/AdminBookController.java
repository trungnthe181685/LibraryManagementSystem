package com.example.openlibrary.controller;

import com.example.openlibrary.model.Author;
import com.example.openlibrary.model.Book;
import com.example.openlibrary.model.BookCategory;
import com.example.openlibrary.model.Publisher;
import com.example.openlibrary.repository.AuthorRepository;
import com.example.openlibrary.repository.BookCategoryRepository;
import com.example.openlibrary.repository.BookRepository;
import com.example.openlibrary.repository.PublisherRepository;
import com.example.openlibrary.repository.ReservationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/books")
public class AdminBookController {

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private BookCategoryRepository categoryRepository;
	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private PublisherRepository publisherRepository;

	@GetMapping
	public String showBooks(Model model) {
		List<Book> books = bookRepository.findAll();
		model.addAttribute("books", books);
		model.addAttribute("book", new Book());
		model.addAttribute("authors", authorRepository.findAll());
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("publishers", publisherRepository.findAll());
		return "admin/books";
	}

	@PostMapping("/add")
	public String addBook(@RequestParam("authorID") Long authorID, @RequestParam("publisherID") Long publisherID,
			@RequestParam(value = "categories", required = false) List<Long> bookCategoryID, @ModelAttribute Book book,
			RedirectAttributes redirectAttributes) {

		Author author = authorRepository.findById(authorID).orElse(null);
		Publisher publisher = publisherRepository.findById(publisherID).orElse(null);
		List<BookCategory> categories = (bookCategoryID != null) ? categoryRepository.findAllById(bookCategoryID)
				: new ArrayList<>();

		book.setAuthor(author);
		book.setPublisher(publisher);
		book.setCategories(categories);
		book.setAvailableCopies(book.getTotalCopies());
		book.setStock(book.getAvailableCopies() > 0 ? "In Stock" : "Out of Stock");

		// 🔥 rentalPrice already populated by @ModelAttribute

		bookRepository.save(book);
		redirectAttributes.addFlashAttribute("toastMessage", "Book added successfully!");
		return "redirect:/admin/books";
	}

	@PostMapping("/edit/{id}")
	public String editBook(@PathVariable("id") Long id, @RequestParam String bookName, @RequestParam String description,
			@RequestParam int totalCopies, @RequestParam double rentalPrice,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate publishedDate,
			@RequestParam("publisherID") Long publisherID, @RequestParam String imageURL, @RequestParam Long authorID,
			@RequestParam(required = false) List<Long> categories, RedirectAttributes redirectAttributes) {

		Optional<Book> optionalBook = bookRepository.findById(id);
		if (optionalBook.isEmpty()) {
			redirectAttributes.addFlashAttribute("toastMessage", "Book not found.");
			return "redirect:/admin/books";
		}
		
		Publisher publisher = publisherRepository.findById(publisherID).orElse(null);
		Book book = optionalBook.get();
		book.setBookName(bookName);
		book.setDescription(description);
		book.setTotalCopies(totalCopies);
		book.setRentalPrice(rentalPrice);
		book.setPublishedDate(publishedDate);
		book.setPublisher(publisher);
		book.setImageURL(imageURL);

		// Set author
		Author author = authorRepository.findById(authorID).orElse(null);
		book.setAuthor(author);

		// Set categories
		if (categories != null && !categories.isEmpty()) {
			List<BookCategory> selectedCategories = categoryRepository.findAllById(categories);
			book.setCategories(selectedCategories);
		} else {
			book.setCategories(new ArrayList<>());
		}

		// Update stock
		book.setAvailableCopies(book.getTotalCopies());

		if (book.getAvailableCopies() > 0) {
			book.setStock("In Stock");
		} else {
			book.setStock("Out of Stock");
		}

		bookRepository.save(book);
		redirectAttributes.addFlashAttribute("toastMessage", "Book updated successfully!");
		return "redirect:/admin/books";
	}

	@PostMapping("/delete/{id}")
	public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		bookRepository.deleteById(id);
		redirectAttributes.addFlashAttribute("toastMessage", "Book deleted successfully!");
		return "redirect:/admin/books";
	}
}
