package com.example.openlibrary.service;

import com.example.openlibrary.model.Publisher;
import com.example.openlibrary.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    public Publisher getPublisherById(Long id) {
        return publisherRepository.findById(id).orElse(null);
    }

    public void savePublisher(Publisher publisher) {
        publisherRepository.save(publisher);
    }
}
