package com.example.azimovTemplate.Models.User;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.Role;
import org.springframework.context.support.BeanDefinitionDsl;

import java.util.List;

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
    private String name;
    private String surname;
    private String password;
    private String roles;
    private String code;
    // here token should be saved(if user will create
    // test and the answer will be mush later token will be updated)
}
