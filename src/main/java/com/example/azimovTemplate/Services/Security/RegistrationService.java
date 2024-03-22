package com.example.azimovTemplate.Services.Security;

import com.example.azimovTemplate.Models.User.UserModel;
import com.example.azimovTemplate.Models.User.UserModelDetails;
import com.example.azimovTemplate.Models.User.UserModelDetailsService;
import com.example.azimovTemplate.Services.*;
import com.example.azimovTemplate.Services.Reprositories.UserModelReprository;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.nullability.NeverNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.regex.Pattern;


@Service
@AllArgsConstructor
public class RegistrationService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserModelDetailsService userModelDetailsService;
    @Autowired
    private UserModelReprository userReprository;

    private Utils utils;
    private SenderEmails mailSender;
    private SecurityConfig security;


    public void regiter(UserModel user, String password) {

        if (userReprository.findByEmail(user.getEmail()).isPresent()) throw new IllegalArgumentException("Email is already used");

        if (userReprository.findByPhone(user.getPhone()).isPresent()) throw new IllegalArgumentException("Phone is already used");

        if (userReprository.findByName(user.getName()).isPresent()) throw new IllegalArgumentException("Username already exists");

        if (password.length() < 8) throw new IllegalArgumentException("Password should have the length more than 8");

        if (!isValidPassword(password)) throw new IllegalArgumentException("Password should contains letters, digits and symbols(*/-+?;:%())");


        sendVerificationCode(user);
    }

    public void sendVerificationCode(String username) {
        UserModelDetails userModelDetails = userModelDetailsService.loadUserByUsername(username);
        UserModel user = userModelDetails.getUser();
        user.setCode(utils.generateVerificationCode());
        try {
            mailSender.sendMessage(user.getEmail(),
                    "Verification code",
                    mailSender.generateVerificationText(user.getCode(), user.getName()));
        } catch (MessagingException e) {

        }
        user.setVerified(false);
        userModelDetailsService.addUser(user);
    }
    public void sendVerificationCode(UserModel user) {
        user.setCode(utils.generateVerificationCode());
        try {
            mailSender.sendMessage(user.getEmail(),
                    "Verification code",
                    mailSender.generateVerificationText(user.getCode(), user.getName()));
        } catch (MessagingException e) {

        }
        user.setVerified(false);
        userModelDetailsService.addUser(user);
    }

    public void verificate(String string, HttpServletRequest request) {
        String name, code;
        if (string.contains("_")) {
            String buffer[] = string.split("_", 2);
            code = buffer[0];
            name = security.decodeString(buffer[1]);
        } else {
            code = string;
            Cookie[] cookies = request.getCookies();
            name = security.decodeString(Arrays.stream(cookies).filter(a -> a.getName().equals("token")).findFirst().orElseThrow().getValue());
        }
        UserModelDetails userDetails = userModelDetailsService.loadUserByUsername(name);
        if (userDetails.isVerified()) {
            throw new IllegalArgumentException("You are already verified");
        }
        if (userDetails.getCode().equals(code)) {
            UserModel user = userDetails.getUser();
            user.setVerified(true);
            user.setCode("");
            try {
                userModelDetailsService.updateUser(user);
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            throw new BadCredentialsException("Incorrect code");
        }
    }

    private static boolean isValidPassword(String str) {
        // Check for at least one symbol
        Pattern symbolPattern = Pattern.compile("[*!?;:%()/+-]");
        // Check for at least one English letter
        Pattern letterPattern = Pattern.compile("[a-zA-Z]");
        // Check for at least one digit
        Pattern digitPattern = Pattern.compile("\\d");

        return symbolPattern.matcher(str).find() &&
                letterPattern.matcher(str).find() &&
                digitPattern.matcher(str).find();
    }

    public void autoLogin(UserModel user, String password, HttpServletRequest request) {

        UserModelDetails userDetails = userModelDetailsService.loadUserByUsername(user.getName());

        if (!userDetails.isVerified()){
            throw new IllegalArgumentException("You are not verified");
        }



        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        } else {
            throw new BadCredentialsException("Username or password are incorrect");
        }
    }

    public Cookie setCookieToken(String token) {
        token = security.encodeString(token);
        Cookie cookie = new Cookie("token", token);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(86400);
        return cookie;
    }

}
