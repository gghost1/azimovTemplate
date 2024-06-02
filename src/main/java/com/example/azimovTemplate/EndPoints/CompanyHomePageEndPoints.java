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
    private UserModelReprository userReprository;
    private CompanyInformationModelReprository companyReprository;
    private SteckModelReprository steckReprository;
    private VacancyTestModelReprository vacancyTestReprository;
    private VacancyModelReprository vacancyReprository;
    private DbConnection connection;
    private TemplateEngine engine;
    private SecurityConfig decoder;


    @SuppressWarnings("rawtypes")
    @PostMapping("/createDescription")
    public ResponseEntity createDescription(@RequestBody String desription, HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        String name = Arrays.stream(cookies).filter(a -> a.getName().equals("token")).findFirst().orElseThrow().getValue();
        name = decoder.decodeString(name);
        UserModel user = userReprository.findByName(name).orElseThrow();
        if (!user.isCompany()) return new ResponseEntity(HttpStatus.FORBIDDEN);

        CompanyProfileModel profile = (CompanyProfileModel) companyReprository.findById(user.getId()).orElseThrow();

        profile.setDescription(desription);
        connection.updateCompanyProfile(profile);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/createVacancy")
    public ResponseEntity createVacancy(@RequestBody VacancyEntity vacancy, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String name = Arrays.stream(cookies).filter(a -> a.getName().equals("token")).findFirst().orElseThrow().getValue();
        name = decoder.decodeString(name);
        UserModel user = userReprository.findByName(name).orElseThrow();
        if (!user.isCompany()) return new ResponseEntity(HttpStatus.FORBIDDEN);
        CompanyProfileModel profile = (CompanyProfileModel) companyReprository.findById(user.getId()).orElseThrow();

        VacancyModel vacancyModel = new VacancyModel();
        vacancyModel.setCompany(profile);
        vacancyModel.setName(vacancy.getName());
        vacancyModel.setAmount(vacancy.getAmount());
        vacancyModel.setExperience(vacancy.getExperience());
        vacancyModel.setDescription(vacancy.getDescription());
        vacancyModel.setGeneratedTest(!vacancy.getIsGeneratedTest().isEmpty());
        vacancyModel.setSteckVerefied(!vacancy.getIsSteckVerefied().isEmpty());

        List<Steck> stecks = new ArrayList<>();
        vacancy.getSteck().stream().forEach(a-> stecks.add((Steck) steckReprository.getByName(a).orElseThrow()));
        vacancyModel.setSteck(stecks);

        // generateTestOrCreateOwnTest
        VacancyTestModel testModel = new VacancyTestModel();
        vacancyTestReprository.save(testModel);
        vacancyModel.setTestId(testModel.getId());

        vacancyReprository.save(vacancyModel);

        return new ResponseEntity(HttpStatus.OK);
    }
}
