package com.example.azimovTemplate.Services;

import com.example.azimovTemplate.Models.User.UserModel;
import com.example.azimovTemplate.Models.User.UserModelDetails;
import com.example.azimovTemplate.Services.Reprositories.UserModelReprository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserModelDetailsService implements UserDetailsService {

    @Autowired
    private UserModelReprository reprository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> user = reprository.findByName(username);
        return user.map(UserModelDetails::new).orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }
}
