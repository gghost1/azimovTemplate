package com.example.azimovTemplate.Models.Tables.User;

import com.example.azimovTemplate.Models.Entity.User;
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
    private boolean isCompany;
    private String roles;
    private String code;
    private boolean isVerified;
    private int score;

    public UserModel(User user) {
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.name = user.getName();
        this.password = user.getPassword();
        this.isCompany = user.getIsCompany().equals("company");
    }

    public UserModel() {
    }
    // here token should be saved(if user will create
    // test and the answer will be mush later token will be updated)
}
