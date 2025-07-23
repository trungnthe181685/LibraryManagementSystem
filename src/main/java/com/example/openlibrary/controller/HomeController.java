package com.example.openlibrary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.openlibrary.model.Book;
import com.example.openlibrary.model.BookCategory;
import com.example.openlibrary.repository.BookCategoryRepository;
import com.example.openlibrary.repository.BookRepository;

@Controller
public class HomeController {

    @Autowired
    private BookCategoryRepository categoryRepo;

    @Autowired
    private BookRepository bookRepo;

    @GetMapping("/home")
    public String homePage(Model model) {
        List<BookCategory> randomCategories = categoryRepo.findRandomCategories();
        List<Book> randomBooks = bookRepo.findRandomBooks();

        model.addAttribute("categories", randomCategories);
        model.addAttribute("books", randomBooks);

        return "home"; // home.html
    }
}
