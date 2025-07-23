package com.example.openlibrary.controller;

import com.example.openlibrary.model.Author;
import com.example.openlibrary.repository.AuthorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/authors")
public class AdminAuthorController {

    @Autowired
    private AuthorRepository authorRepo;

    @GetMapping
    public String listAuthors(Model model) {
        model.addAttribute("authors", authorRepo.findAll());
        model.addAttribute("author", new Author()); // for modal form
        return "admin/authors";
    }

    @PostMapping("/add")
    public String addAuthor(@ModelAttribute("author") Author author, RedirectAttributes redirectAttrs) {
        authorRepo.save(author);
        redirectAttrs.addFlashAttribute("message", "Author added successfully!");
        return "redirect:/admin/authors";
    }
    
    @PostMapping("/edit")
    public String editAuthor(@RequestParam Long authorID, @RequestParam String authorName, RedirectAttributes redirectAttrs) {
        Author author = authorRepo.findById(authorID).orElse(null);
        if (author != null) {
            author.setAuthorName(authorName);
            authorRepo.save(author);
            redirectAttrs.addFlashAttribute("message", "Author updated.");
        }
        return "redirect:/admin/authors";
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable("id") Long id, RedirectAttributes redirectAttrs) {
        authorRepo.deleteById(id);
        redirectAttrs.addFlashAttribute("message", "Author deleted.");
        return "redirect:/admin/authors";
    }
}
