package com.example.azimovTemplate.Services.Reprositories;

import com.example.azimovTemplate.Models.Test.TestModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TestModelReprository extends JpaRepository<TestModel, Long> {
    List<TestModel> findByUsername(String username);

}
