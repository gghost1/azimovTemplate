package com.example.azimovTemplate.EndPoints;

import com.example.azimovTemplate.Models.Entity.VacancyEntity;
import com.example.azimovTemplate.Models.Tables.Steck;
import com.example.azimovTemplate.Models.Tables.User.CompanyProfileModel;
import com.example.azimovTemplate.Models.Tables.User.UserModel;
import com.example.azimovTemplate.Models.Tables.VacancyModel;
import com.example.azimovTemplate.Models.Tables.VacancyTestModel;
import com.example.azimovTemplate.Services.DbConnection;
import com.example.azimovTemplate.Services.Reprositories.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/home/")
@AllArgsConstructor
public class CompanyHomePageEndPoints {
    UserModelReprository userReprository;
    CompanyInformationModelReprository companyReprository;
    SteckModelReprository steckReprository;
    VacancyTestModelReprository vacancyTestReprository;
    VacancyModelReprository vacancyReprository;
    DbConnection connection;

    @SuppressWarnings("rawtypes")
    @PostMapping("/createDescription")
    public ResponseEntity createDescription(@RequestBody String desription, HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        String name = Arrays.stream(cookies).filter(a -> a.getName().equals("token")).findFirst().orElseThrow().getValue();

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
//        String name = Arrays.stream(cookies).filter(a -> a.getName().equals("token")).findFirst().orElseThrow().getValue();
        String name = "dima";
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
