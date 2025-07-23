package com.example.openlibrary.controller;

import com.example.openlibrary.model.BookCategory;
import com.example.openlibrary.repository.BookCategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    @Autowired
    private BookCategoryRepository categoryRepo;
    
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

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id, RedirectAttributes redirectAttrs) {
        categoryRepo.deleteById(id);
        redirectAttrs.addFlashAttribute("message", "Category deleted.");
        return "redirect:/admin/categories";
    }
}
