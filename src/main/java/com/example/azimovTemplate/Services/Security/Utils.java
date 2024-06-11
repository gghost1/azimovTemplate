package com.example.azimovTemplate.Services.Security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.hibernate.annotations.Bag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Base64;

@Configuration
public class Utils {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String encodeString(String string) {
        return Base64.getEncoder().encodeToString(string.getBytes());
    }
    public String decodeString(String string) {
        return new String(Base64.getDecoder().decode(string));
    }
    public String encode(String string) {
        return passwordEncoder.encode(string);
    }
    public String getUserName(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String name = Arrays.stream(cookies).filter(a -> a.getName().equals("token")).findFirst().orElseThrow().getValue();
        return decodeString(name);
    }
}
