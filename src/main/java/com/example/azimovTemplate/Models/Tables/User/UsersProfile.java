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

    @Column(unique = true)
    private String description;

    private byte[] cv;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private UserModel user;

}
