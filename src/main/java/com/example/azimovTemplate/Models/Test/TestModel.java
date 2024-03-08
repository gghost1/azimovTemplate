package com.example.azimovTemplate.Models.Test;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tests")
public class TestModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    private String type;
    private int count;
    private String book;
    private String complexity;
    private String topics;
}