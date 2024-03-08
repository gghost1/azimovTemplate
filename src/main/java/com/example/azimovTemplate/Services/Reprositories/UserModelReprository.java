package com.example.azimovTemplate.Services.Reprositories;

import com.example.azimovTemplate.Models.User.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserModelReprository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByName(String name);
    List<UserModel> findByPhone (String phone);
    List<UserModel> findByEmail (String email);
}
