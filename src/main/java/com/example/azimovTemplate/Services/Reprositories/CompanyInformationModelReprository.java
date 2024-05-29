package com.example.azimovTemplate.Services.Reprositories;

import com.example.azimovTemplate.Models.User.CompanyProfileModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyInformationModelReprository extends JpaRepository<CompanyProfileModel, Long> {

}
