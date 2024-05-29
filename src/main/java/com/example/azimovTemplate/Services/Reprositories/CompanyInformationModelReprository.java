package com.example.azimovTemplate.Services.Reprositories;

import com.example.azimovTemplate.Models.Tables.User.CompanyProfileModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyInformationModelReprository extends JpaRepository<CompanyProfileModel, Long> {
    public Optional findById (long id);

}
