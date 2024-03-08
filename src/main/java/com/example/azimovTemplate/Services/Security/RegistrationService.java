package com.example.azimovTemplate.Services.Security;

import com.example.azimovTemplate.Models.User.UserModel;
import com.example.azimovTemplate.Services.DbConnection;
import com.example.azimovTemplate.Services.Reprositories.UserModelReprository;
import com.example.azimovTemplate.Services.UserModelDetailsService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;


@Service
@AllArgsConstructor
public class RegistrationService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserModelDetailsService userModelDetailsService;
    @Autowired
    private UserModelReprository reprository;
    private DbConnection dbConnection;

    public void regiter(UserModel user, String password, HttpServletRequest request) {

        if (!reprository.findByEmail(user.getEmail()).isEmpty()) throw new IllegalArgumentException("Email is already used");

        if (!reprository.findByPhone(user.getPhone()).isEmpty()) throw new IllegalArgumentException("Phone is already used");

        if (reprository.findByName(user.getName()).isPresent()) throw new IllegalArgumentException("Username already exists");

        if (password.length() < 8) throw new IllegalArgumentException("Password should have the length more than 8");

        if (!isValidPassword(password)) throw new IllegalArgumentException("Password should contains letters, digits and symbols(*/-+?;:%())");

        dbConnection.addUser(user);

        autoLogin(user, password, request);

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


        UserDetails userDetails = userModelDetailsService.loadUserByUsername(user.getName());

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        }
    }
    public Cookie setCookieToken(String token) {
        Cookie cookie = new Cookie("token", token);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(86400);
        return cookie;
    }
}
