package com.example.azimovTemplate.Models.User;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phone;
    @Column(unique = true)
    private String name;
    private String password;
    private String roles;
    private String code;
    private boolean isVerified;
    private boolean isCompany = false;

    // here token should be saved(if user will create
    // test and the answer will be mush later token will be updated)
}
