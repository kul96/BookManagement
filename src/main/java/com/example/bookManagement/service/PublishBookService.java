package com.example.bookManagement.service;

import com.example.bookManagement.entity.PublishBook;
import com.example.bookManagement.repository.PublishBookRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublishBookService {
    private final PublishBookRepo publishBookRepo;

    @Autowired
    public PublishBookService(PublishBookRepo publishBookRepo) {
        this.publishBookRepo = publishBookRepo;
    }

    public PublishBook addPublishBook(PublishBook publishBook) {
        return publishBookRepo.save(publishBook);
    }

    public PublishBook getPublishBookById(Long id) {
        return publishBookRepo.findById(id)
                              .orElseThrow(() -> new EntityNotFoundException("not found by id: " + id));
    }

}
