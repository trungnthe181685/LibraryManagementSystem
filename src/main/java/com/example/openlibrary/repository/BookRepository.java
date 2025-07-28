package com.example.openlibrary.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.openlibrary.model.Author;
import com.example.openlibrary.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	List<Book> findByBookNameContainingIgnoreCase(String bookName);

	
	@Query("SELECT DISTINCT b FROM Book b " +
		       "JOIN b.categories c " +
		       "LEFT JOIN b.author a " +
		       "WHERE (:authorId IS NULL OR a.authorID = :authorId) " +
		       "AND (:year IS NULL OR FUNCTION('YEAR', b.publishedDate) = :year) " +
		       "AND (:categoryIds IS NULL OR c.bookCategoryID IN :categoryIds)")
		List<Book> findBooksByFilters(@Param("authorId") Long authorId,
		                              @Param("year") Integer year,
		                              @Param("categoryIds") List<Long> categoryIds);
	
	@Query("SELECT DISTINCT b FROM Book b " +
			"JOIN b.categories c " +
			"LEFT JOIN b.author a " +
			"WHERE (:title IS NULL OR LOWER(b.bookName) LIKE LOWER(CONCAT('%', :title, '%'))) " +
			"AND (:authorId IS NULL OR a.authorID = :authorId) " +
			"AND (:categoryIds IS NULL OR c.bookCategoryID IN :categoryIds)")
	List<Book> searchBooks(
			@Param("title") String title,
			@Param("authorId") Long authorId,
			@Param("categoryIds") List<Long> categoryIds, Sort sort);

	@Query(value = "SELECT * FROM book ORDER BY RAND() LIMIT 6", nativeQuery = true)
	List<Book> findRandomBooks();

	List<Book> findByAuthor(Author author);

	// Finds books that share at least one category ID, and not the current book itself
	List<Book> findDistinctByCategories_BookCategoryIDInAndBookIDNot(List<Long> categoryIds, Long excludeBookId);

	List<Book> findByAuthorAndBookIDNot(Author author, Long excludeBookId);

	@Query(value = "SELECT * FROM book ORDER BY borrow_count DESC LIMIT 6", nativeQuery = true)
	List<Book> findPopularBooks();
}