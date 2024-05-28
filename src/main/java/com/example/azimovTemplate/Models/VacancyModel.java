package com.example.azimovTemplate.Models;

import com.example.azimovTemplate.Models.User.CompanyProfileModel;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "vacancies")
public class VacancyModel {
    @Id
    private long id;

    private String name;
    private String description;
    private String amount;

    private String workingSchedule;

    private String[] steck;


//    @OneToMany(fetch = FetchType.LAZY)
//    @MapsId
//    private CompanyProfileModel company;

}
