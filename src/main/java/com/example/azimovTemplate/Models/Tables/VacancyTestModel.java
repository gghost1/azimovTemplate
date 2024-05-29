package com.example.azimovTemplate.Models.Tables;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "vacancy_test")
public class VacancyTestModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
