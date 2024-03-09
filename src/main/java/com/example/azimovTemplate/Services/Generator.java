package com.example.azimovTemplate.Services;

import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class Generator {

    public String generateVerificationCode() {
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int digit = ThreadLocalRandom.current().nextInt(0, 10);
            codeBuilder.append(digit);
        }
        return codeBuilder.toString();
    }


}

