package com.example.openlibrary.controller;

import com.example.openlibrary.model.Book;
import com.example.openlibrary.model.BorrowRecord;
import com.example.openlibrary.model.Reservation;
import com.example.openlibrary.repository.BookRepository;
import com.example.openlibrary.repository.BorrowRecordRepository;
import com.example.openlibrary.repository.ReservationRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/reservations")
public class AdminReservationController {

    @Autowired
    private ReservationRepository reservationRepo;
    
    @Autowired
    private BorrowRecordRepository borrowRecordRepo;

    @Autowired
    private BookRepository bookRepo;

    @GetMapping
    public String listReservations(Model model) {
        List<Reservation> reservations = reservationRepo.findAll();

        // Create a map to hold borrow records for each reservation
        Map<Long, List<BorrowRecord>> recordsMap = new HashMap<>();
        for (Reservation reservation : reservations) {
            List<BorrowRecord> records = borrowRecordRepo.findByReservation(reservation);
            recordsMap.put(reservation.getReservationID(), records);
        }

        model.addAttribute("reservations", reservations);
        model.addAttribute("recordsMap", recordsMap);
        return "admin/reservations";
    }


    @GetMapping("/update-status/{id}")
    public String updateReservationStatus(
            @PathVariable Long id,
            @RequestParam("status") Reservation.Status newStatus,
            RedirectAttributes redirectAttrs) {

        Reservation reservation = reservationRepo.findById(id).orElse(null);

        if (reservation != null && reservation.getStatus() != newStatus) {
            reservation.setStatus(newStatus);
            reservationRepo.save(reservation);

                Book book = reservation.getBook();
                int active = reservationRepo.countActiveReservationsByBook(book);
                book.setAvailableCopies(Math.max(book.getTotalCopies() - active, 0));
                book.setStock(book.getAvailableCopies() > 0 ? "In Stock" : "Out of Stock");
                bookRepo.save(book);


            redirectAttrs.addFlashAttribute("message", "Reservation status updated to " + newStatus.name());
        } else {
            redirectAttrs.addFlashAttribute("message", "No change made or reservation not found.");
        }

        return "redirect:/admin/reservations";
    }
    
    @GetMapping("/add-auto/{reservationID}")
    public String autoAddBorrowRecord(@PathVariable Long reservationID, RedirectAttributes redirectAttrs) {
        Reservation reservation = reservationRepo.findById(reservationID).orElse(null);
        if (reservation == null) {
            redirectAttrs.addFlashAttribute("message", "Reservation not found.");
            return "redirect:/admin/reservations";
        }

        BorrowRecord record = new BorrowRecord();
        LocalDate now = LocalDate.now();

        record.setBorrowDate(now);
        record.setReturnDate(null);
        record.setDueDate(now.plusWeeks(2));
        record.setStatus("BORROWING");
        record.setReservation(reservation);

        borrowRecordRepo.save(record);
        redirectAttrs.addFlashAttribute("message", "Borrow record added.");

        return "redirect:/admin/reservations";
    }

    @PostMapping("/update-record")
    public String updateBorrowRecord(@ModelAttribute BorrowRecord record, RedirectAttributes redirectAttrs) {
        BorrowRecord existing = borrowRecordRepo.findById(record.getBorrowID()).orElse(null);
        if (existing != null) {
            existing.setReturnDate(record.getReturnDate());
            existing.setStatus(record.getStatus());
            borrowRecordRepo.save(existing);
            redirectAttrs.addFlashAttribute("message", "Borrow record updated.");
        }
        return "redirect:/admin/reservations";
    }

}

