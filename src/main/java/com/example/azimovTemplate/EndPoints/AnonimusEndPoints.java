package com.example.azimovTemplate.EndPoints;


import com.example.azimovTemplate.Models.Entity.User;
import com.example.azimovTemplate.Models.Tables.NewsModel;
import com.example.azimovTemplate.Models.Tables.User.CompanyProfileModel;
import com.example.azimovTemplate.Models.Tables.User.UserModel;
import com.example.azimovTemplate.Models.Tables.User.UsersProfile;
import com.example.azimovTemplate.Services.DbConnection;
import com.example.azimovTemplate.Services.Reprositories.NewsModelReprository;
import com.example.azimovTemplate.Services.Security.Utils;
import com.example.azimovTemplate.Services.Security.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/")
@AllArgsConstructor
public class AnonimusEndPoints {

    private DbConnection dbConnection;
    private RegistrationService registration;
    private Utils utils;

    @GetMapping("/")
    public ModelAndView welcomePage() { // will return some page
        return new ModelAndView("welcomePage");
    }
    @GetMapping("/register")
    public ModelAndView registerPage() { // will return some page
        return new ModelAndView("registerPage");
    }
    // @GetMapping("/registerPage")
    // public ModelAndView registerPageSecond() { // will return some page
    //     return new ModelAndView("registerPage");
    // }
    @GetMapping("toVacancies")
    public ModelAndView vacanciesPage() { // will return some page
        return new ModelAndView("vacancies");
    }
    @GetMapping("toNews")
    public ModelAndView newsPage() { // will return some page
        return new ModelAndView("news");
    }
    @PostMapping("/register")
    public ModelAndView register(@RequestBody User userEntity, HttpServletResponse response, HttpServletRequest request) {

        UserModel user = new UserModel(userEntity);
        String pass = user.getPassword();
        user.setPassword(utils.encode(user.getPassword()));
        registration.register(user, pass);

        response.addCookie(registration.setCookieToken(user.getName()));
        return new ModelAndView("verificationPage");
    }

    @GetMapping("/verification")
    private ModelAndView verificationPage() {
        return new ModelAndView("verificationPage");
    }

    @GetMapping("/resendCode")
    public ModelAndView resendCode(HttpServletRequest request) throws Exception {
        registration.resendCode(request);

        return new ModelAndView("verificationPage");
    }


    @GetMapping("/register/{code}")
    public ModelAndView verification(@PathVariable String code, HttpServletResponse response, HttpServletRequest request) {
        registration.verificate(code, request);

        return new ModelAndView("loginPage");
    }

    @GetMapping("/auth")
    public ModelAndView loginPage() { // will return some page
        return new ModelAndView("loginPage");
    }
    @PostMapping("/authen")
    public ResponseEntity auth(@RequestBody UserModel user, HttpServletResponse response, HttpServletRequest request) throws IOException {
        String pass = user.getPassword();

        registration.autoLogin(user, pass, request);
        response.addCookie(registration.setCookieToken(user.getName()));
        return new ResponseEntity(HttpStatus.OK);
    }

    private NewsModelReprository news;
    @GetMapping("/news")
    public ModelAndView anonimousNewsPage() {
        ModelAndView model = new ModelAndView("newsPage");
        List<NewsModel> newsModels = news.getAllByOrderByScoreDescDateDesc();
        List<String> a = new ArrayList<>();
        model.addObject("newsColl",newsModels);

        return model;
    }

    @GetMapping("/home")
    public ModelAndView homePage(HttpServletRequest request) {
        String name = utils.getUserName(request);
        UserModel user = dbConnection.findUserByName(name);
        ModelAndView model = new ModelAndView("personal");
        if (user.isCompany()) {
            CompanyProfileModel company = dbConnection.findCompanyProfileById(user.getId());
            model.addObject("info", company);
        } else {
            UsersProfile profile = dbConnection.findUserProfileById(user.getId());
            model.addObject("info", profile);
        }

        model.addObject("user", user);
        return model;
    }

}
