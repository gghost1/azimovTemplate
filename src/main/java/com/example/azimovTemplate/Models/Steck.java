package com.example.azimovTemplate.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "steck")
public class Steck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;


}
