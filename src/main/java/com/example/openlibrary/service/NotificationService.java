package com.example.openlibrary.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.openlibrary.model.BorrowRecord;
import com.example.openlibrary.model.Notification;
import com.example.openlibrary.model.Notification.NotificationType;
import com.example.openlibrary.model.User;
import com.example.openlibrary.repository.BorrowRecordRepository;
import com.example.openlibrary.repository.NotificationRepository;


@Service
public class NotificationService {

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Scheduled(cron = "0 0 9 * * ?")
    public void generateReservationNotifications() {
        LocalDate today = LocalDate.now();

        List<BorrowRecord> borrowRecords = borrowRecordRepository.findAll();

        for (BorrowRecord borrowRecord : borrowRecords) {
            LocalDate dueDate = borrowRecord.getDueDate(); // You must have getDueDate()
            User user = borrowRecord.getReservation().getUser();

            if (dueDate != null) {
                long daysUntilDue = ChronoUnit.DAYS.between(today, dueDate);

                if (dueDate.isBefore(today)) {
                    createNotification(user, "Your reserved book is overdue!", borrowRecord, NotificationType.OVERDUE);
                } else if (daysUntilDue <= 2) {
                    createNotification(user, "Reminder: Your reserved book is due in 2 days.", borrowRecord, NotificationType.DUE_SOON);
                }

            }
        }
    }

    private void createNotification(User user, String message, BorrowRecord borrowRecord, NotificationType type) {
        boolean exists = notificationRepository.existsByUserAndBorrowRecordAndType(user, borrowRecord, type);
        if (exists) return; // Already notified

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message + " (Book: " + borrowRecord.getReservation().getBook().getBookName() + ")");
        notification.setCreatedAt(LocalDateTime.now());
        notification.setType(type);
        notification.setBorrowRecord(borrowRecord);
        notification.setRead(false);
        notificationRepository.save(notification);
    }

}
