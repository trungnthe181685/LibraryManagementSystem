package com.example.openlibrary.repository;

import com.example.openlibrary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
		User findByEmailAndPassword(String email, String password);
		
		User findByEmail(String email);
}