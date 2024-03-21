package com.example.azimovTemplate.Services.Errors;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionEndPoints implements ErrorController {

    @RequestMapping("/error")
    public String hendleError(HttpServletResponse response) {
        if (response.getStatus() == HttpStatus.NOT_FOUND.value()) return "404";

        return "ERROR";
    }

}
