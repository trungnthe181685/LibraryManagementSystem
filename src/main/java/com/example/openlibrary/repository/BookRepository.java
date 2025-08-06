package com.example.openlibrary.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.openlibrary.model.Author;
import com.example.openlibrary.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	List<Book> findByBookNameContainingIgnoreCase(String bookName);

	
	@Query("""
            SELECT b FROM Book b
            LEFT JOIN b.categories c
            LEFT JOIN b.author a
            LEFT JOIN b.reservations r
            WHERE (:title IS NULL OR LOWER(b.bookName) LIKE LOWER(CONCAT('%', :title, '%')))
              AND (:authorId IS NULL OR a.authorID = :authorId)
              AND (:categoryIds IS NULL OR 
                  (SELECT COUNT(DISTINCT bc.bookCategoryID) FROM b.categories bc WHERE bc.bookCategoryID IN :categoryIds) = :catSize)
            GROUP BY b
            ORDER BY 
                CASE WHEN :sortBy = 'latest' THEN b.publishedDate END DESC,
                CASE WHEN :sortBy = 'oldest' THEN b.publishedDate END ASC,
                CASE WHEN :sortBy = 'popularity' THEN COUNT(r) END DESC
        """)
    Page<Book> searchBooksAllFilters(
            @Param("title") String title,
            @Param("authorId") Long authorId,
            @Param("categoryIds") List<Long> categoryIds,
            @Param("catSize") Long catSize,
            @Param("sortBy") String sortBy,
            Pageable pageable);


	@Query(value = "SELECT * FROM book ORDER BY RAND() LIMIT 6", nativeQuery = true)
	List<Book> findRandomBooks();

	List<Book> findByAuthor(Author author);
	
	@Query("SELECT DISTINCT b FROM Book b " +
	        "JOIN b.categories c " +
	        "LEFT JOIN b.author a " +
	        "WHERE (:title IS NULL OR LOWER(b.bookName) LIKE LOWER(CONCAT('%', :title, '%'))) " +
	        "AND (:authorId IS NULL OR a.authorID = :authorId) " +
	        "AND (:categoryIds IS NULL OR c.bookCategoryID IN :categoryIds) " +
	        "ORDER BY SIZE(b.reservations) DESC")
	List<Book> searchBooksByPopularity(
	        @Param("title") String title,
	        @Param("authorId") Long authorId,
	        @Param("categoryIds") List<Long> categoryIds);

	
	// Finds books that share at least one category ID, and not the current book itself
	List<Book> findDistinctByCategories_BookCategoryIDInAndBookIDNot(List<Long> categoryIds, Long excludeBookId);

	List<Book> findByAuthorAndBookIDNot(Author author, Long excludeBookId);

	@Query("SELECT b FROM Book b LEFT JOIN b.reservations r GROUP BY b ORDER BY COUNT(r) DESC LIMIT 6")
	List<Book> findPopularBooks();
	
	@Query("""
            SELECT b FROM Book b
            LEFT JOIN b.author a
            WHERE (:title IS NULL OR LOWER(b.bookName) LIKE LOWER(CONCAT('%', :title, '%')))
              AND (:authorId IS NULL OR a.authorID = :authorId)
            """)
    Page<Book> searchBooksByTitleAndAuthor(
            @Param("title") String title,
            @Param("authorId") Long authorId,
            Pageable pageable);

}