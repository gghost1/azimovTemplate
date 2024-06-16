package com.example.azimovTemplate.Services.Reprositories;

import com.example.azimovTemplate.Models.Tables.CVModelDatabase;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CVModelRepository extends JpaRepository<CVModelDatabase, Long> {
    public Optional findById(Long id);
    List<CVModelDatabase> findAll();
}
