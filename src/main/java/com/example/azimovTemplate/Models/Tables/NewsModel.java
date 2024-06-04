package com.example.azimovTemplate.Models.Tables;


import com.example.azimovTemplate.Models.Tables.User.CompanyProfileModel;
import com.example.azimovTemplate.Models.Tables.User.UserModel;
import jakarta.persistence.*;
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
    private int score;

    @Column(nullable = false)
    private long userId;

}
