package com.example.azimovTemplate.EndPoints;


import com.example.azimovTemplate.Models.User.UserModel;
import com.example.azimovTemplate.Services.DbConnection;
import com.example.azimovTemplate.Services.Security.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;


@RestController
@RequestMapping("/")
@AllArgsConstructor
public class AnonimusEndPoints {

    private DbConnection dbConnection;
    private PasswordEncoder passwordEncoder;
    private RegistrationService registration;

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
        System.out.println("goin");
        registration.verificate(code, request);
        return new ModelAndView("redirect:/auth");
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
