package com.example.openlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.openlibrary.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}