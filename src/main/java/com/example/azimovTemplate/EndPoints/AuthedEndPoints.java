package com.example.azimovTemplate.EndPoints;

import com.example.azimovTemplate.Services.DbConnection;
import com.example.azimovTemplate.Services.TemplateEngine;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/home/")
@AllArgsConstructor
public class AuthedEndPoints {

    TemplateEngine engine;
    private DbConnection connection;
    private PasswordEncoder passwordEncoder;

    @GetMapping("/createTest")
    public ModelAndView createTestPage() {
        return new ModelAndView("redirect:/createTestPage.html");
    }

}
