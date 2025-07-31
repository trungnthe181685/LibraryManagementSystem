package com.example.openlibrary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.openlibrary.model.BorrowRecord;
import com.example.openlibrary.model.Notification;
import com.example.openlibrary.model.Notification.NotificationType;
import com.example.openlibrary.model.User;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    List<Notification> findByUser(User user);
    
    boolean existsByUserAndBorrowRecordAndType(User user, BorrowRecord borrowRecord, NotificationType type);

    List<Notification> findByUserOrderByCreatedAtDesc(User user);
    
    List<Notification> findTop5ByUserOrderByCreatedAtDesc(User user);
    
    List<Notification> findByUserAndIsReadFalse(User user);


}
