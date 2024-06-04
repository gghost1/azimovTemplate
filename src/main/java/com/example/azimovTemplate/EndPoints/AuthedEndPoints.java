package com.example.azimovTemplate.EndPoints;

import com.example.azimovTemplate.Models.Tables.NewsModel;
import com.example.azimovTemplate.Models.Tables.User.UserModel;
import com.example.azimovTemplate.Services.DbConnection;
import com.example.azimovTemplate.Services.Security.Utils;
import com.example.azimovTemplate.Services.TemplateEngine;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@RestController
@RequestMapping("/home/")
@AllArgsConstructor
public class AuthedEndPoints {

    private DbConnection dbConnection;
    private Utils utils;

    @GetMapping("/createNews")
    public ModelAndView createTestPage() {

        return new ModelAndView();
    }

    @PostMapping("/createNews")
    public ModelAndView createNews(@RequestBody String text, HttpServletRequest request) {
        String name = utils.getUserName(request);
        UserModel user = dbConnection.findUserByName(name);

        NewsModel news = new NewsModel();
        news.setDate(new Date(System.currentTimeMillis()));
        news.setUserId(user.getId());
        news.setText(text);
        news.setScore(user.getScore());

        dbConnection.addNews(news);

        ModelAndView model = new ModelAndView("homePage");
        model.addObject("user", user);
        return model;
    }



}
