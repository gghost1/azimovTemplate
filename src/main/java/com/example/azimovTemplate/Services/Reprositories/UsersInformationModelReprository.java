package com.example.azimovTemplate.Services.Reprositories;

import com.example.azimovTemplate.Models.User.UsersProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersInformationModelReprository extends JpaRepository<UsersProfile, Long> {
    Optional<UsersProfile> findUsersProfileById (Long id);
}
