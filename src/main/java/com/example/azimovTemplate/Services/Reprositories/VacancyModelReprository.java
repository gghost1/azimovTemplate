package com.example.azimovTemplate.Services.Reprositories;

import com.example.azimovTemplate.Models.Tables.VacancyModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacancyModelReprository extends JpaRepository<VacancyModel, Long> {
    public List findByCompany_Id(Long id);
}
