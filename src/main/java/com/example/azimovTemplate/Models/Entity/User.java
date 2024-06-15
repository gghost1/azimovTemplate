package com.example.azimovTemplate.Models.Entity;

import lombok.Data;

@Data
public class User {
    private String email;
    private String phone;
    private String name;
    private String password;
    private String isCompany;
}
