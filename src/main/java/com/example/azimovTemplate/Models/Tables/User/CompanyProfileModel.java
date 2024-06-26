package com.example.azimovTemplate.Models.Tables.User;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "company_information")
public class CompanyProfileModel {
    @Id
    private long id;

    @Column(unique = true)
    private String Inn;
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private UserModel user;



}
