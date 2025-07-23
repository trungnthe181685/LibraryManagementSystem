package com.example.openlibrary.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Publisher {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long publisherID;

	private String name;

	private String address;

	@OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
	private List<Book> books;

	public Long getPublisherID() {
		return publisherID;
	}

	public void setPublisherID(Long publisherID) {
		this.publisherID = publisherID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
}
