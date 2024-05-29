package com.example.azimovTemplate.Services.Reprositories;

import com.example.azimovTemplate.Models.Tables.Steck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SteckModelReprository extends JpaRepository<Steck, Long> {
    public Optional getByName (String name);
}
