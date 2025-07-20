package com.example.openlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.openlibrary.model.BookCategory;

public interface BookCategoryRepository extends JpaRepository<BookCategory, Long> {
}