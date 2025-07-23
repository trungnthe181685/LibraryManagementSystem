package com.example.openlibrary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.openlibrary.model.Book;
import com.example.openlibrary.model.User;
import com.example.openlibrary.repository.BookRepository;
import com.example.openlibrary.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	public void saveUser (User user) {
		userRepository.save(user);
	}
	
	// Search books by name (partial, case-insensitive) - postman
	public List<Book> searchByName(String keyword) {
		return bookRepository.findByBookNameContainingIgnoreCase(keyword);
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
}