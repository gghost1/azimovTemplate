package com.example.azimovTemplate.Models.Tables;

import com.example.azimovTemplate.Models.Tables.Steck;
import com.example.azimovTemplate.Models.Tables.User.CompanyProfileModel;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "vacancies")
public class VacancyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    private String amount;
    private String experience;
    private String workingSchedule;

    @OneToMany(mappedBy  = "name", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Steck> steck;

    private boolean isGeneratedTest;

    private long testId;

    private boolean isSteckVerefied;

    @Column(nullable = false)
    private long companyId;

}
