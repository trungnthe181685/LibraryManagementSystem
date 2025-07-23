package com.example.openlibrary.service;

import com.example.openlibrary.model.BookCategory;
import com.example.openlibrary.repository.BookCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {
	@Autowired
    private BookCategoryRepository categoryRepository;

    public List<BookCategory> getAllCategories() {
        return categoryRepository.findAll();
    }
    
    public BookCategory getById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }
}
