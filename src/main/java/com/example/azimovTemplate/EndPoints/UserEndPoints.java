package com.example.azimovTemplate.EndPoints;

import com.example.azimovTemplate.Models.Tables.User.CompanyProfileModel;
import com.example.azimovTemplate.Models.Tables.User.UserModel;
import com.example.azimovTemplate.Models.Tables.User.UsersProfile;
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
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.Header;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/home/")
@AllArgsConstructor
public class UserEndPoints {

    private SteckModelReprository steckReprository;
    private DbConnection dbConnection;
    private Utils utils;

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

    @PostMapping("/addStack")
    public ResponseEntity postMethodName(@RequestBody String infoForStack, HttpServletRequest request) {

        String name = utils.getUserName(request);
        UserModel user = dbConnection.findUserByName(name);

        if (user.isCompany()) return new ResponseEntity(HttpStatus.FORBIDDEN);

        UsersProfile profile = dbConnection.findUserProfileById(user.getId());
        profile.setInfoForStack(infoForStack);
        dbConnection.updateUsersProfile(profile);

        return new ResponseEntity(HttpStatus.OK);
    }
}