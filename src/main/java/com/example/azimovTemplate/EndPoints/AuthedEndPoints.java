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
public class AuthedEndPoints {

    private DbConnection connection;
    private TestModelReprository testReprository;
    private PasswordEncoder passwordEncoder;

    @GetMapping("/createTest")
    public ModelAndView createTestPage() {
        return new ModelAndView("redirect:/createTestPage.html");
    }
    @PostMapping("/createTest")
    public String createTest(@RequestBody TestModel test, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String name = Arrays.stream(cookies).filter(a -> a.getName().equals("token")).findFirst().orElseThrow().getValue();
        System.out.println(name);
        // here request to ML
        connection.addTest(test);
        // return json with test or redirect
        return "test";
    }

    @GetMapping("/checkAnswer")
    public ModelAndView sendAnswerPage() {
        return new ModelAndView("redirect:/checkAnswerPage.html");
    }

    @PostMapping("/checkAnswer")
    public String checkAnswer(@RequestBody String answer) {
        TestModel test = testReprository.findById(1).orElseThrow();
        // here request to ML
        connection.removeTest(test.id);
        // return json with test or redirect
        return "answer";
    }


}
