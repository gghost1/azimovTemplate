package com.example.azimovTemplate.Services.Reprositories;

import com.example.azimovTemplate.Models.Tables.NewsModel;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface NewsModelReprository extends JpaRepository<NewsModel, Long> {

    List<NewsModel> getAllByOrderByScoreDescDateDesc();

}
