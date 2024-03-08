package com.example.azimovTemplate.Services.Security;

import com.example.azimovTemplate.Models.User.UserModel;
import com.example.azimovTemplate.Services.Reprositories.UserModelReprository;
import com.example.azimovTemplate.Services.UserModelDetailsService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;


@Service
public class RegistrationService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserModelDetailsService userModelDetailsService;
    @Autowired
    private UserModelReprository reprository;



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
