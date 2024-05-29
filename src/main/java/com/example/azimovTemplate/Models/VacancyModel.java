package com.example.azimovTemplate.Models;

import com.example.azimovTemplate.Models.User.CompanyProfileModel;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
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

    private String workingSchedule;

    @OneToMany(mappedBy = "name", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Steck> steck;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_information_id")
    private CompanyProfileModel company;

}
