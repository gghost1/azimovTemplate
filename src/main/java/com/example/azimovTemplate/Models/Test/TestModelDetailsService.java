package com.example.azimovTemplate.Models.Test;


import com.example.azimovTemplate.Services.Reprositories.TestModelReprository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestModelDetailsService {
    @Autowired
    private TestModelReprository reprository;

    public void addTest(TestModel testModel) {
        reprository.save(testModel);
    }
    @Cacheable(value = "tests", key = "#username")
    public List<TestModel> loadTestsByUsername(String username) {
        List<TestModel> tests = reprository.findByUsername(username);
        return tests;
    }
}
