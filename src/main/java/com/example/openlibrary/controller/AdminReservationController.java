package com.example.openlibrary.controller;

import com.example.openlibrary.model.Book;
import com.example.openlibrary.model.Reservation;
import com.example.openlibrary.repository.BookRepository;
import com.example.openlibrary.repository.ReservationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/reservations")
public class AdminReservationController {

    @Autowired
    private ReservationRepository reservationRepo;

    @Autowired
    private BookRepository bookRepo;

    @GetMapping
    public String listReservations(Model model) {
        model.addAttribute("reservations", reservationRepo.findAll());
        return "admin/reservations";
    }

    @GetMapping("/return/{id}")
    public String markAsReturned(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        Reservation reservation = reservationRepo.findById(id).orElse(null);

        if (reservation != null && reservation.getStatus() == Reservation.Status.RESERVED) {
            reservation.setStatus(Reservation.Status.RETURNED);
            reservationRepo.save(reservation);

            Book book = reservation.getBook();
            int active = reservationRepo.countActiveReservationsByBook(book);
            book.setAvailableCopies(Math.max(book.getTotalCopies() - active, 0));
            book.setStock(book.getAvailableCopies() > 0 ? "In Stock" : "Out of Stock");
            bookRepo.save(book);

            redirectAttrs.addFlashAttribute("message", "Reservation marked as returned.");
        }
        return "redirect:/admin/reservations";
    }
}

