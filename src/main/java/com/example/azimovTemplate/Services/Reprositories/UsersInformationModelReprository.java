package com.example.azimovTemplate.Services.Reprositories;

import com.example.azimovTemplate.Models.User.UsersProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersInformationModelReprository extends JpaRepository<UsersProfile, Long> {

}
