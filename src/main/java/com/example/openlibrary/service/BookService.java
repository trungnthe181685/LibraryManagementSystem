package com.example.openlibrary.service;

import com.example.openlibrary.model.Book;
import com.example.openlibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }
    
    public List<Book> searchByKeyword(String keyword) {
        return bookRepository.findByBookNameContainingIgnoreCase(keyword);
    }
    
    public List<Book> getFilteredBooks(Long authorId, Integer year, List<Long> categoryIds) {
        return bookRepository.findBooksByFilters(authorId, year, categoryIds);
    }

    	


}
