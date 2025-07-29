package com.example.openlibrary.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.openlibrary.model.BorrowRecord;
import com.example.openlibrary.model.User;
import com.example.openlibrary.repository.BorrowRecordRepository;

@Service
public class ReminderService {

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @Autowired
    private JavaMailSender mailSender;

    public void sendDueDateReminders() {
        LocalDate today = LocalDate.now();
        LocalDate soon = today.plusDays(2); // Reminder for books due in 2 days

        List<BorrowRecord> records = borrowRecordRepository.findAll();

        for (BorrowRecord record : records) {
            LocalDate dueDate = record.getDueDate();
            if (dueDate != null && !dueDate.isBefore(today) && !dueDate.isAfter(soon)) {
                User user = record.getReservation().getUser();
                if (user != null && user.getEmail() != null) {
                    sendEmail(user.getEmail(), "Book Due Reminder",
                            "Dear " + user.getName() + ", your borrowed book is due on " + dueDate + ".");
                }
            }
        }
    }

    private void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
    
    @Scheduled(cron = "0 0 9 * * ?") // Every day at 9 AM
    public void runReminderJob() {
        sendDueDateReminders();
    }
}

