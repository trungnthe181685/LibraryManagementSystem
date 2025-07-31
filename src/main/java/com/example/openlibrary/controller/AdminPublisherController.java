package com.example.openlibrary.controller;

import com.example.openlibrary.model.Book;
import com.example.openlibrary.model.Publisher;
import com.example.openlibrary.model.Reservation;
import com.example.openlibrary.repository.BookRepository;
import com.example.openlibrary.repository.PublisherRepository;
import com.example.openlibrary.repository.ReservationRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/publishers")
public class AdminPublisherController {

    @Autowired
    private PublisherRepository publisherRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private ReservationRepository reservationRepository;

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

    @Transactional
    @PostMapping("/delete-publisher/{id}")
    public String deletePublisher(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Publisher> optionalPublisher = publisherRepository.findById(id);

        if (optionalPublisher.isPresent()) {
            Publisher publisher = optionalPublisher.get();

            if (publisher.getBooks() != null) {
                for (Book book : publisher.getBooks()) {

                	if (book.getReservations() != null) {
                        for (Reservation reservation : book.getReservations()) {
                            reservationRepository.delete(reservation);
                        }
                    }
                	
                    book.setPublisher(null);
                    bookRepository.save(book);
                }
            }

            publisherRepository.delete(publisher);
            redirectAttributes.addFlashAttribute("message", "Publisher deleted!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Publisher not found!");
        }

        return "redirect:/admin/publishers";
    }

}
