package com.example.azimovTemplate.EndPoints;

import com.example.azimovTemplate.Models.Entity.VacancyEntity;
import com.example.azimovTemplate.Models.Tables.Steck;
import com.example.azimovTemplate.Models.Tables.User.CompanyProfileModel;
import com.example.azimovTemplate.Models.Tables.User.UserModel;
import com.example.azimovTemplate.Models.Tables.VacancyModel;
import com.example.azimovTemplate.Models.Tables.VacancyTestModel;
import com.example.azimovTemplate.Services.DbConnection;
import com.example.azimovTemplate.Services.Reprositories.*;
import com.example.azimovTemplate.Services.Security.SecurityConfig;
import com.example.azimovTemplate.Services.Security.Utils;
import com.example.azimovTemplate.Services.TemplateEngine;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/home/")
@AllArgsConstructor
public class CompanyHomePageEndPoints {

    private DbConnection dbConnection;
    private Utils utils;


    @SuppressWarnings("rawtypes")
    @PostMapping("/createDescription")
    public ResponseEntity createDescription(@RequestBody String desription, HttpServletRequest request) {

        String name = utils.getUserName(request);
        UserModel user = dbConnection.findUserByName(name);

        if (!user.isCompany()) return new ResponseEntity(HttpStatus.FORBIDDEN);

        CompanyProfileModel profile = dbConnection.findCompanyProfileById(user.getId());

        profile.setDescription(desription);
        dbConnection.updateCompanyProfile(profile);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/createVacancy")
    public ResponseEntity createVacancy(@RequestBody VacancyEntity vacancy, HttpServletRequest request) {

        String name = utils.getUserName(request);
        UserModel user = dbConnection.findUserByName(name);

        if (!user.isCompany()) return new ResponseEntity(HttpStatus.FORBIDDEN);

        CompanyProfileModel profile = dbConnection.findCompanyProfileById(user.getId());

        VacancyModel vacancyModel = new VacancyModel();
        vacancyModel.setCompany(profile);
        vacancyModel.setName(vacancy.getName());
        vacancyModel.setAmount(vacancy.getAmount());
        vacancyModel.setExperience(vacancy.getExperience());
        vacancyModel.setDescription(vacancy.getDescription());
        vacancyModel.setGeneratedTest(!vacancy.getIsGeneratedTest().isEmpty());
        vacancyModel.setSteckVerefied(!vacancy.getIsSteckVerefied().isEmpty());

        List<Steck> stecks = new ArrayList<>();
        vacancy.getSteck().stream().forEach(a-> stecks.add(dbConnection.findSteckByName(a)));
        vacancyModel.setSteck(stecks);

        // generateTestOrCreateOwnTest
        VacancyTestModel testModel = new VacancyTestModel();
        dbConnection.addVacancyTest(testModel);
        vacancyModel.setTestId(testModel.getId());

        dbConnection.addVacancy(vacancyModel);

        return new ResponseEntity(HttpStatus.OK);
    }
}
