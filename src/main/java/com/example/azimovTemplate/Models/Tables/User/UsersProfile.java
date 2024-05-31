package com.example.azimovTemplate.Models.Tables.User;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

@Data
@Entity
@Table(name = "user_information")
public class UsersProfile {
    @Id
    private long id;

    private int score;

    @Column(unique = true)
    private String description;
    private String experience;
    private String infoForStack;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private UserModel user;

}
