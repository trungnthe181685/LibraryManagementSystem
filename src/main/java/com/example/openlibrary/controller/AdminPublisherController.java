package com.example.openlibrary.controller;

import com.example.openlibrary.model.Publisher;
import com.example.openlibrary.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/publishers")
public class AdminPublisherController {

    @Autowired
    private PublisherRepository publisherRepository;

    @GetMapping
    public String listPublishers(Model model) {
        model.addAttribute("publishers", publisherRepository.findAll());
        model.addAttribute("pub", new Publisher()); // for add form
        return "admin/publishers";
    }

    @PostMapping("/add")
    public String addPublisher(@ModelAttribute("publisher") Publisher publisher, RedirectAttributes redirectAttributes) {
        publisherRepository.save(publisher);
        redirectAttributes.addFlashAttribute("message", "Publisher added successfully!");
        return "redirect:/admin/publishers";
    }

    @PostMapping("/edit")
    public String editPublisher(@RequestParam Long publisherID, @RequestParam String name, @RequestParam String address, RedirectAttributes redirectAttributes) {
        Publisher existing = publisherRepository.findById(publisherID).orElse(null);
        if (existing != null) {
            existing.setName(name);
            existing.setAddress(address);
            publisherRepository.save(existing);
            redirectAttributes.addFlashAttribute("message", "Publisher updated successfully!");
        }
        return "redirect:/admin/publishers";
    }

    @GetMapping("/delete/{id}")
    public String deletePublisher(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        publisherRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Publisher deleted successfully!");
        return "redirect:/admin/publishers";
    }
}
