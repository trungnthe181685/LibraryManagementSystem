package com.example.openlibrary.controller;

import com.example.openlibrary.model.BookCategory;
import com.example.openlibrary.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class PublicCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String showAllCategories(Model model) {
        List<BookCategory> categories = categoryService.getAllCategories();
        System.out.println("Categories loaded: " + categories.size()); // ✅ Debug log
        model.addAttribute("categories", categories);
        return "category";
    }
}
