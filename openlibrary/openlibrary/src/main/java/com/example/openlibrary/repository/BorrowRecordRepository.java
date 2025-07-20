package com.example.openlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.openlibrary.model.BorrowRecord;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
}