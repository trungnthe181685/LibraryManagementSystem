package com.example.openlibrary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.openlibrary.model.Reservation;
import com.example.openlibrary.repository.AuthorRepository;
import com.example.openlibrary.repository.BookRepository;
import com.example.openlibrary.repository.ReservationRepository;
import com.example.openlibrary.repository.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private BookRepository bookRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ReservationRepository reservationRepository;
    @Autowired private AuthorRepository authorRepository;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("bookCount", bookRepository.count());
        model.addAttribute("userCount", userRepository.count());
        model.addAttribute("reservationCount", reservationRepository.count());
        model.addAttribute("authorCount", authorRepository.count());

        List<Reservation> recentReservations = reservationRepository
            .findTop5ByOrderByCreatedDateDesc();
        model.addAttribute("recentReservations", recentReservations);

        return "admin/dashboard";
    }
    
    @GetMapping
    public String adminDashboard() {
        return "admin/admin";
    }
}

