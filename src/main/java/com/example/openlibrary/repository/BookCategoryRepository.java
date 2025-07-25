package com.example.openlibrary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.openlibrary.model.BookCategory;

public interface BookCategoryRepository extends JpaRepository<BookCategory, Long> {
	@Query(value = "SELECT * FROM book_category ORDER BY RAND() LIMIT 6", nativeQuery = true)
	List<BookCategory> findRandomCategories();
}