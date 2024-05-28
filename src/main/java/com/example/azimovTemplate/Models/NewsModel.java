package com.example.azimovTemplate.Models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "news")
public class NewsModel {
    @Id
    private long id;

    private String text;

    private Date date;


}
