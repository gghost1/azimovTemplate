package com.example.azimovTemplate.Services.Reprositories;

import com.example.azimovTemplate.Models.User.UserModel;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserModelReprository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByName(String username);
    Optional<UserModel> findByPhone (String phone);
    Optional<UserModel> findByEmail (String email);
}
