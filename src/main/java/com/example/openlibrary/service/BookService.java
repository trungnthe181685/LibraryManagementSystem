package com.example.openlibrary.service;

import com.example.openlibrary.model.Author;
import com.example.openlibrary.model.Book;
import com.example.openlibrary.model.BookCategory;
import com.example.openlibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

	public List<Book> findSimilarBooks(Book book) {
		List<BookCategory> categories = book.getCategories();
		if (categories == null || categories.isEmpty()) {
			return new ArrayList<>();
		}

		// Get all category IDs of this book
		List<Long> categoryIds = categories.stream().map(BookCategory::getBookCategoryID).collect(Collectors.toList());

		// Find books that share at least one of these categories (exclude the original
		// book)
		return bookRepository.findDistinctByCategories_BookCategoryIDInAndBookIDNot(categoryIds, book.getBookID());
	}

	public List<Book> findBooksBySameAuthor(Author author, Long excludeBookId) {
		if (author == null)
			return Collections.emptyList();
		return bookRepository.findByAuthorAndBookIDNot(author, excludeBookId);
	}

}
