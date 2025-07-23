package com.example.openlibrary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.openlibrary.model.Author;
import com.example.openlibrary.repository.AuthorRepository;

@Service
public class AuthorService {
	@Autowired
    private AuthorRepository authorRepository;
	
	public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
    
}
