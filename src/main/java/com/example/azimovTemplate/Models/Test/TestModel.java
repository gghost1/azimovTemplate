package com.example.azimovTemplate.Models.Test;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tests")
public class TestModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String username;
    private byte[] bytes;
    private String type;
    private int count;
    private String book;
    private String complexity;
    private String topics;
}