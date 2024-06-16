package com.example.azimovTemplate.Models.Tables;

import com.example.azimovTemplate.Models.Tables.Steck;
import com.example.azimovTemplate.Models.Tables.User.CompanyProfileModel;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "resumes")
public class CVModelDatabase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private int age;
    private String description;
    private String education;
    private String location;
    private String country;
    private boolean move;
    private boolean businessTrip;
    private String sheduel;
    private String prevJob;
    private String skills;


}
