package com.example.openlibrary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.openlibrary.model.BorrowRecord;
import com.example.openlibrary.model.Reservation;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
	List<BorrowRecord> findByReservation(Reservation reservation);
}