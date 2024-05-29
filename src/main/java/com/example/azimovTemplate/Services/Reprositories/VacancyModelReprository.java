package com.example.azimovTemplate.Services.Reprositories;

import com.example.azimovTemplate.Models.VacancyModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VacancyModelReprository extends JpaRepository<VacancyModel, Long> {
    public List findByCompany_Id(Long id);
}
