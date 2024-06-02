package com.example.azimovTemplate.EndPoints;


import com.example.azimovTemplate.Models.Tables.User.UserModel;
import com.example.azimovTemplate.Services.DbConnection;
import com.example.azimovTemplate.Services.Reprositories.UserModelReprository;
import com.example.azimovTemplate.Services.Security.RegistrationService;
import com.example.azimovTemplate.Services.Security.SecurityConfig;
import com.example.azimovTemplate.Services.TemplateEngine;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Arrays;


@RestController
@RequestMapping("/")
@AllArgsConstructor
public class AnonimusEndPoints {

    TemplateEngine engine;
    private DbConnection dbConnection;
    private PasswordEncoder passwordEncoder;
    private RegistrationService registration;
    private UserModelReprository userReprository;
    private SecurityConfig decoder;

    @GetMapping("/")
    public ModelAndView welcomePage() { // will return some page
        return new ModelAndView("welcomePage");
    }
    @GetMapping("/register")
    public ModelAndView registerPage() { // will return some page
        return new ModelAndView("registerPage");
    }
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserModel user, HttpServletResponse response, HttpServletRequest request) {
        String pass = user.getPassword();
        user.setCompany(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        registration.regiter(user, pass);

        response.addCookie(registration.setCookieToken(user.getName()));
        return new ResponseEntity(HttpStatus.OK);
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

        // may be set token as encode id
        registration.autoLogin(user, pass, request);
        response.addCookie(registration.setCookieToken(user.getName()));
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/home")
    public ModelAndView homePage(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String name = Arrays.stream(cookies).filter(a -> a.getName().equals("token")).findFirst().orElseThrow().getValue();
        name = decoder.decodeString(name);
        UserModel user = userReprository.findByName(name).orElseThrow();
        ModelAndView model = new ModelAndView("homePage");
        model.addObject("user", user);
        return model;
    }

}
