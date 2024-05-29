package com.example.azimovTemplate.Models.Tables.User;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_information")
public class UsersProfile {
    @Id
    private long id;

    private int score;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private UserModel user;

}
