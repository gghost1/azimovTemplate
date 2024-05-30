package com.example.azimovTemplate.EndPoints;

import com.example.azimovTemplate.Models.Test.TestModel;
import com.example.azimovTemplate.Services.DbConnection;
import com.example.azimovTemplate.Services.Reprositories.TestModelReprository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64Encoder;
import org.springframework.http.HttpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.Header;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@RestController
@RequestMapping("/home/")
@AllArgsConstructor
public class UserEndPoints {

    private DbConnection connection;
    private TestModelReprository testReprository;
    private PasswordEncoder passwordEncoder;


    @GetMapping("/addWorkExperience")
    public ModelAndView sendAnswerPage() {
        return new ModelAndView("redirect:/userExperiencePage.html");
    }

    @PostMapping("/addWorkExperience")
    public String addExperience(@RequestBody String exp) {
        return exp;
    }


}