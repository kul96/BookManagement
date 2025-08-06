package com.example.bookManagement.repository;

import com.example.bookManagement.entity.People;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PeopleRepo extends JpaRepository<People, Long> {
    @Override
    <S extends People> List<S> saveAll(Iterable<S> entities);
}
