package com.example.azimovTemplate.EndPoints;

import com.example.azimovTemplate.Models.Test.TestModel;
import com.example.azimovTemplate.Models.Test.TestModelDetailsService;
import com.example.azimovTemplate.Services.Reprositories.TestModelReprository;
import com.example.azimovTemplate.Services.Security.SecurityConfig;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/home/")
@AllArgsConstructor
public class AuthedEndPoints {

    private TestModelDetailsService testModelDetailsService;
    @Autowired
    private SecurityConfig decoder;

    @GetMapping("/createTest")
    public ModelAndView createTestPage() {
        return new ModelAndView("redirect:/createTestPage.html");
    }
    @PostMapping("/createTest")
    public String createTest(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        TestModel testModel;

            testModel = new TestModel();
            try {
                testModel.setBytes(file.getBytes());
//                BufferedOutputStream stream =
//                        new BufferedOutputStream(new FileOutputStream(new File("-uploaded")));
//                stream.write(file.getBytes());
//                stream.close();
            } catch (Exception e) {

            }

        Cookie[] cookies = request.getCookies();
        String name = Arrays.stream(cookies).filter(a -> a.getName().equals("token")).findFirst().orElseThrow().getValue();
        name = decoder.decodeString(name);
        testModel.setUsername(name);
        // here request to ML
        testModelDetailsService.addTest(testModel);
        // return json with test or redirect
        return "test";
    }

    @GetMapping("/checkAnswer")
    public ModelAndView sendAnswerPage() {
        return new ModelAndView("redirect:/checkAnswerPage.html");
    }

    @PostMapping("/checkAnswer")
    public String checkAnswer(@RequestBody String answer) {
        List<TestModel> test = testModelDetailsService.loadTestsByUsername(answer);
        // here request to ML

        // return json with test or redirect
        return "answer";
    }


}
