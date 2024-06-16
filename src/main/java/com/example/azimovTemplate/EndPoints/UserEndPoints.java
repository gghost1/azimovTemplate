package com.example.azimovTemplate.EndPoints;

import com.example.azimovTemplate.Models.Entity.CVModel;
import com.example.azimovTemplate.Models.Entity.VacancyEntity;
import com.example.azimovTemplate.Models.Tables.CVModelDatabase;
import com.example.azimovTemplate.Models.Tables.Steck;
import com.example.azimovTemplate.Models.Tables.User.CompanyProfileModel;
import com.example.azimovTemplate.Models.Tables.User.UserModel;
import com.example.azimovTemplate.Models.Tables.User.UsersProfile;
import com.example.azimovTemplate.Models.Tables.VacancyModel;
import com.example.azimovTemplate.Models.Tables.VacancyTestModel;
import com.example.azimovTemplate.Services.DbConnection;
import com.example.azimovTemplate.Services.Reprositories.CompanyInformationModelReprository;
import com.example.azimovTemplate.Services.Reprositories.SteckModelReprository;
import com.example.azimovTemplate.Services.Reprositories.UserModelReprository;
import com.example.azimovTemplate.Services.Reprositories.UsersInformationModelReprository;
import com.example.azimovTemplate.Services.Reprositories.VacancyModelReprository;
import com.example.azimovTemplate.Services.Reprositories.VacancyTestModelReprository;

import com.example.azimovTemplate.Services.Security.SecurityConfig;
import com.example.azimovTemplate.Services.Security.Utils;
import com.example.azimovTemplate.Services.TemplateEngine;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64Encoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.Header;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;



@RestController
@RequestMapping("/home/")
@AllArgsConstructor

public class UserEndPoints {

    private SteckModelReprository steckReprository;
    private DbConnection dbConnection;
    private Utils utils;
    private final RestTemplate restTemplate;

    // @GetMapping("/addWorkExperience")
    // public ModelAndView sendAnswerPage() {
    //     return new ModelAndView("redirect:/userExperiencePage.html");
    // }

    @PostMapping("/addDescription")
    public ResponseEntity createDescription(@RequestBody String desription, HttpServletRequest request) {

        String name = utils.getUserName(request);
        UserModel user = dbConnection.findUserByName(name);

        if (user.isCompany()) return new ResponseEntity(HttpStatus.FORBIDDEN);

        UsersProfile profile = dbConnection.findUserProfileById(user.getId());
        desription = desription.replaceFirst("\"","");
        desription = desription.substring(0,desription.length()-2);
        profile.setDescription(desription);
        dbConnection.updateUsersProfile(profile);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/addWorkExperience")
    public ResponseEntity addExperience(@RequestBody String experience, HttpServletRequest request) {
        
        String name = utils.getUserName(request);
        UserModel user = dbConnection.findUserByName(name);

        if (user.isCompany()) return new ResponseEntity(HttpStatus.FORBIDDEN);

        UsersProfile profile = dbConnection.findUserProfileById(user.getId());

        profile.setDescription(experience);
        dbConnection.updateUsersProfile(profile);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/createCV")
    public ResponseEntity createVacancy(@RequestBody CVModel cv, HttpServletRequest request) throws IOException {

        String name = utils.getUserName(request);
        UserModel user = dbConnection.findUserByName(name);
        UsersProfile profile = dbConnection.findUserProfileById(user.getId());
        CVModelDatabase resumeModel = new CVModelDatabase();
        cv.setName(name);
        profile.setId(profile.getId());
        resumeModel.setAge(cv.getAge());
        resumeModel.setName(cv.getName());
        resumeModel.setEducation(cv.getEducation());
        resumeModel.setDescription(cv.getDescription());
        resumeModel.setLocation(cv.getLocation());
        resumeModel.setCountry(cv.getCountry());
        resumeModel.setMove(cv.isMove());
        resumeModel.setBusinessTrip(cv.isBusinessTrip());
        resumeModel.setSheduel(cv.getSheduel());
        resumeModel.setPrevJob(cv.getPrevJob());
        resumeModel.setSkills(cv.getSkills());
        
        dbConnection.addResume(resumeModel);
        

        if (user.isCompany()) return new ResponseEntity(HttpStatus.FORBIDDEN);

        String url = "http://localhost:8080/cv";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<CVModel> requestTo = new HttpEntity<>(cv, headers);

        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestTo,
                byte[].class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            byte[] fileBytes = responseEntity.getBody();
            profile = dbConnection.findUserProfileById(user.getId());
            profile.setCv(fileBytes);
            dbConnection.updateUsersProfile(profile);
            // here fileBytes should be uploaded to bd


            // pdf file save local
            String outputFile = "example.pdf";
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                fos.write(fileBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


            // send cv model to ml and get file;

        // generateTestOrCreateOwnTest


        return new ResponseEntity(HttpStatus.OK);
    }
}