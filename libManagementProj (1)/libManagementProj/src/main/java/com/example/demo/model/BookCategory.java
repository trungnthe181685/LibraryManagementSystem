package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BookCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookCategoryID;

	private String bookCatName;

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
