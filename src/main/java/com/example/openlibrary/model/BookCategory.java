package com.example.openlibrary.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class BookCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookCategoryID;

	private String bookCatName;

	@ManyToMany(mappedBy = "categories")
    private List<Book> books;
	
	public Long getBookCategoryID() {
		return bookCategoryID;
	}

	public void setBookCategoryID(Long bookCategoryID) {
		this.bookCategoryID = bookCategoryID;
	}

	public String getBookCatName() {
		return bookCatName;
	}

	public void setBookCatName(String bookCatName) {
		this.bookCatName = bookCatName;
	}
}
