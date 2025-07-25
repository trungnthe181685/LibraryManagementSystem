package com.example.openlibrary.controller;

import com.example.openlibrary.model.Book;
import com.example.openlibrary.model.BookCategory;
import com.example.openlibrary.repository.BookCategoryRepository;
import com.example.openlibrary.repository.BookRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    @Autowired
    private BookCategoryRepository categoryRepo;
    
    @Autowired
    private BookRepository bookRepository;
    
    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("category", new BookCategory());
        return "admin/categories";
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute("category") BookCategory category,
                              RedirectAttributes redirectAttrs) {
        categoryRepo.save(category);
        redirectAttrs.addFlashAttribute("message", "Category added successfully!");
        return "redirect:/admin/categories";
    }

    @PostMapping("/edit")
    public String editCategory(@RequestParam Long bookCategoryID,
                               @RequestParam String bookCatName,
                               RedirectAttributes redirectAttrs) {
        BookCategory existing = categoryRepo.findById(bookCategoryID).orElse(null);
        if (existing != null) {
            existing.setBookCatName(bookCatName);
            categoryRepo.save(existing);
            redirectAttrs.addFlashAttribute("message", "Category updated.");
        }
        return "redirect:/admin/categories";
    }

    @Transactional
    @PostMapping("/delete-category/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<BookCategory> optionalCategory = categoryRepo.findById(id);
        if (optionalCategory.isPresent()) {
            BookCategory category = optionalCategory.get();

            // Remove the category from all associated books
            for (Book book : category.getBooks()) {
                book.getCategories().remove(category);
                bookRepository.save(book); // Update the book
            }

            categoryRepo.delete(category);
            redirectAttributes.addFlashAttribute("message", "Category deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Category not found!");
        }

        return "redirect:/admin/categories";
    }

}
