package com.example.openlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.openlibrary.model.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
