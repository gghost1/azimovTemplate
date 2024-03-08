package com.example.azimovTemplate.Services.Reprositories;

import com.example.azimovTemplate.Models.User.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserModelReprository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByName(String name);
    Optional<UserModel> findByPhone(String phone);
}
