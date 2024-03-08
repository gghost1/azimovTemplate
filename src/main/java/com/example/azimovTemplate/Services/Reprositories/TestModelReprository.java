package com.example.azimovTemplate.Services.Reprositories;

import com.example.azimovTemplate.Models.Test.TestModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestModelReprository extends JpaRepository<TestModel, Long> {
    Optional<TestModel> findById (long id);

    void deleteAllById(long id);
}
