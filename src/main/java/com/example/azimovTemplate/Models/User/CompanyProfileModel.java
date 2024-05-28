package com.example.azimovTemplate.Models.User;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "company_information")
public class CompanyProfileModel {
    @Id
    private long id;

    private int score;

//    @OneToOne(fetch = FetchType.LAZY)
//    @MapsId
//    private UserModel user;

    @Column(unique = true)
    private String Inn;
    private String description;

}