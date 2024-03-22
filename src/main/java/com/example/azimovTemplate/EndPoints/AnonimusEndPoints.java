package com.example.azimovTemplate.EndPoints;


import com.example.azimovTemplate.Models.User.UserModel;
import com.example.azimovTemplate.Models.User.UserModelDetails;
import com.example.azimovTemplate.Models.User.UserModelDetailsService;
import com.example.azimovTemplate.Services.Security.RegistrationService;
import com.example.azimovTemplate.Services.Security.SecurityConfig;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Arrays;


@RestController
@RequestMapping("/")
@AllArgsConstructor
public class AnonimusEndPoints {

    private PasswordEncoder passwordEncoder;
    private RegistrationService registration;
    @Autowired
    private SecurityConfig security;

    @GetMapping("/")
    public ModelAndView welcomePage() { // will return some page
        return new ModelAndView("redirect:/welcomePage.html");
    }
    @GetMapping("/register")
    public ModelAndView registerPage() { // will return some page
        return new ModelAndView("redirect:/registerPage.html");
    }
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserModel user, HttpServletResponse response, HttpServletRequest request) {
        String pass = user.getPassword();

        // check something(may be sent sms and redirect to some page)
        /*
         * set role to this user notFullAuth
         * set code and when user will enter correct code,
         * user will be updated without code(then this field may be used for new verefications
         * user.setCode("1234");
         */

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // may be set token as encode id

        registration.regiter(user, pass);

        response.addCookie(registration.setCookieToken(user.getName()));
        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping("/register/{code}")
    public ModelAndView verification(@PathVariable String code, HttpServletResponse response, HttpServletRequest request) {
        registration.verificate(code, request);
        return new ModelAndView("redirect:/auth");
    }
    @PostMapping("/resendCode")
    public ModelAndView resendCode(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String name = security.decodeString(Arrays.stream(cookies).filter(a -> a.getName().equals("token")).findFirst().orElseThrow().getValue());
        registration.sendVerificationCode(name);
        return new ModelAndView("redirect:/verificationPage.html");
    }

    @GetMapping("/auth")
    public ModelAndView loginPage() { // will return some page
        return new ModelAndView("redirect:/loginPage.html");
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
    public ModelAndView homePage() {
        return new ModelAndView("redirect:/homePage.html");
    }

}
