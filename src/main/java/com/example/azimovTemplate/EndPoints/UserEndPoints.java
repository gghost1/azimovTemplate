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

    UserModelReprository userReprository;
    UsersInformationModelReprository usersReprository;
    SteckModelReprository steckReprository;
    VacancyTestModelReprository vacancyTestReprository;
    VacancyModelReprository vacancyReprository;
    DbConnection connection;

    // @GetMapping("/addWorkExperience")
    // public ModelAndView sendAnswerPage() {
    //     return new ModelAndView("redirect:/userExperiencePage.html");
    // }

    @SuppressWarnings("rawtypes")
    @PostMapping("/addDescription")
    public ResponseEntity createDescription(@RequestBody String desription, HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        String name = Arrays.stream(cookies).filter(a -> a.getName().equals("token")).findFirst().orElseThrow().getValue();

        UserModel user = userReprository.findByName(name).orElseThrow();
        if (user.isCompany()) return new ResponseEntity(HttpStatus.FORBIDDEN);

        UsersProfile profile = (UsersProfile) usersReprository.findById(user.getId()).orElseThrow();

        profile.setDescription(desription);
        connection.updateUsersProfile(profile);

        return new ResponseEntity(HttpStatus.OK);
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/addWorkExperience")
    public ResponseEntity addExperience(@RequestBody String experience, HttpServletRequest request) {
        
        Cookie[] cookies = request.getCookies();
        String name = Arrays.stream(cookies).filter(a -> a.getName().equals("token")).findFirst().orElseThrow().getValue();

        UserModel user = userReprository.findByName(name).orElseThrow();
        if (user.isCompany()) return new ResponseEntity(HttpStatus.FORBIDDEN);

        UsersProfile profile = (UsersProfile) usersReprository.findById(user.getId()).orElseThrow();

        profile.setDescription(experience);
        connection.updateUsersProfile(profile);

        return new ResponseEntity(HttpStatus.OK);
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/addStack")
    public ResponseEntity postMethodName(@RequestBody String infoForStack, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String name = Arrays.stream(cookies).filter(a -> a.getName().equals("token")).findFirst().orElseThrow().getValue();
        
        UserModel user = userReprository.findByName(name).orElseThrow();
        if (user.isCompany()) return new ResponseEntity(HttpStatus.FORBIDDEN);

        UsersProfile profile = (UsersProfile) usersReprository.findById(user.getId()).orElseThrow();
        profile.setInfoForStack(infoForStack);
        connection.updateUsersProfile(profile);

        return new ResponseEntity(HttpStatus.OK);
    }
    


}