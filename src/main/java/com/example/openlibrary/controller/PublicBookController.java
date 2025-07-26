package com.example.openlibrary.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.openlibrary.model.Author;
import com.example.openlibrary.model.Book;
import com.example.openlibrary.model.BookCategory;
import com.example.openlibrary.repository.BookRepository;
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
        if (similarBooks.size() > 4) {
            similarBooks = similarBooks.subList(0, 4);
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
    public String listBooks(@RequestParam(required = false) Long authorId,
                            @RequestParam(required = false) Integer year,
                            @RequestParam(required = false) List<Long> categoryIds,
                            Model model) {
        List<Book> books = bookService.getFilteredBooks(authorId, year, categoryIds);
        model.addAttribute("books", books);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("authors", authorService.getAllAuthors());

        model.addAttribute("selectedAuthorId", authorId);
        model.addAttribute("selectedYear", year);
        model.addAttribute("selectedCategoryIds", categoryIds);		
        return "book-list";
    }


    
}
