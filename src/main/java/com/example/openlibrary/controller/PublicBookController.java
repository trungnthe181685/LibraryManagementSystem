package com.example.openlibrary.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.openlibrary.model.*;
import com.example.openlibrary.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.openlibrary.service.AuthorService;
import com.example.openlibrary.service.BookService;
import com.example.openlibrary.service.CategoryService;
import com.example.openlibrary.service.UserService;

@Controller
public class PublicBookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookCategoryRepository bookCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping("/books/{id}")
    public String showBookDetail(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            return "error"; // or redirect to 404 page
        }

        model.addAttribute("book", book);

        // Fetch similar books
        List<Book> similarBooks = bookService.findSimilarBooks(book);
        // Limit to 4
        if (similarBooks.size() > 3) {
            similarBooks = similarBooks.subList(0, 3);
        }


        List<Book> sameAuthorBooks = bookService.findBooksBySameAuthor(book.getAuthor(), book.getBookID());

        // Limit to 4
        if (sameAuthorBooks.size() > 4) {
            sameAuthorBooks = sameAuthorBooks.subList(0, 4);
        }

        model.addAttribute("sameAuthorBooks", sameAuthorBooks);
        model.addAttribute("similarBooks", similarBooks);

        return "bookdetail"; // Thymeleaf page
    }


    @GetMapping("/search")
    public String searchBooks(@RequestParam("query") String query, Model model) {
        List<Book> results = bookService.searchByKeyword(query); // You’ll create this method
        model.addAttribute("query", query);
        model.addAttribute("results", results);
        return "search"; // Your Thymeleaf HTML
    }

    

    @GetMapping("/books")
    public String getBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) List<Long> categoryIds,
            @RequestParam(required = false) String sortBy,
            Model model) {

        Sort sort = Sort.unsorted();

        if ("latest".equals(sortBy)) {
            sort = Sort.by(Sort.Direction.DESC, "publishedDate");
        } else if ("oldest".equals(sortBy)) {
            sort = Sort.by(Sort.Direction.ASC, "publishedDate");
        } else if ("popularity".equals(sortBy)) {
            sort = Sort.by(Sort.Direction.DESC, "reservations.size"); // May vary based on entity structure
        }

        Long catSize = (categoryIds != null) ? (long) categoryIds.size() : null;

        List<Book> books = bookRepository.searchBooksAllFilters(
        		title, authorId, categoryIds, catSize, sortBy);

        model.addAttribute("books", books);
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("categories", bookCategoryRepository.findAll());
        model.addAttribute("selectedbookName", title);
        model.addAttribute("selectedAuthorId", authorId);
        model.addAttribute("selectedCategoryIds", categoryIds);
        model.addAttribute("selectedSort", sortBy);

        return "book-list";
    }





    @PostMapping("/books/{bookId}/reserve")
    public String reserveBook(@PathVariable Long bookId, @RequestParam Long userId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) return "redirect:/books";

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return "redirect:/books";

        if (book.getAvailableCopies() > 0) {
            return "redirect:/books?error=available"; // or show message on UI
        }

        boolean alreadyReserved = reservationRepository.existsByBookAndUser(book, user);
        if (!alreadyReserved) {
            Reservation reservation = new Reservation();
            reservation.setBook(book);
            reservation.setUser(user);
            reservation.setReservedAt(LocalDateTime.now());
            reservation.setCreatedDate(LocalDate.now());
            reservation.setStatus(Reservation.Status.RESERVED); // or CANCELLED, RETURNED
            reservationRepository.save(reservation);
        }

        return "redirect:/books/" + bookId;
    }

}
