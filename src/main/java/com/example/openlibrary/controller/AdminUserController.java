package com.example.openlibrary.controller;

import com.example.openlibrary.model.User;
import com.example.openlibrary.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private UserRepository userRepo;

    // List users
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "admin/users";
    }

    // Show add form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/add-user";
    }

    // Add user
    @PostMapping("/add")
    public String addUser(@ModelAttribute User user, RedirectAttributes redirectAttrs) {
        userRepo.save(user);
        redirectAttrs.addFlashAttribute("message", "User added successfully.");
        return "redirect:/admin/users";
    }

    // Show edit form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userRepo.findById(id).orElse(null);
        if (user == null) return "redirect:/admin/users";
        model.addAttribute("user", user);
        return "admin/edit-user";
    }

    // Update user
    @PostMapping("/edit")
    public String updateUser(@ModelAttribute User user, RedirectAttributes redirectAttrs) {
        userRepo.save(user);
        redirectAttrs.addFlashAttribute("message", "User updated successfully.");
        return "redirect:/admin/users";
    }

    // Delete user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        userRepo.deleteById(id);
        redirectAttrs.addFlashAttribute("message", "User deleted.");
        return "redirect:/admin/users";
    }
}
