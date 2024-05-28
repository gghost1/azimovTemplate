package com.example.azimovTemplate.Models.User;

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

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private UserModel user;

}
