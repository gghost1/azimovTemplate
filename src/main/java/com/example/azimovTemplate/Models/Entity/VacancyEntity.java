package com.example.azimovTemplate.Models.Entity;

import lombok.Data;

import java.util.List;

@Data
public class VacancyEntity {
    private String name;
    private String description;
    private String amount;
    private String experience;
    private String workingSchedule;

    private List<String> steck;

    private String isGeneratedTest;

    private String isSteckVerefied;
}
