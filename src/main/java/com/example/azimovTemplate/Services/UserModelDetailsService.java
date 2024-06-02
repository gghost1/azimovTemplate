package com.example.azimovTemplate.Services;

import com.example.azimovTemplate.Models.Tables.User.CompanyProfileModel;
import com.example.azimovTemplate.Models.Tables.User.UserModel;
import com.example.azimovTemplate.Models.Tables.User.UserModelDetails;
import com.example.azimovTemplate.Models.Tables.User.UsersProfile;
import com.example.azimovTemplate.Services.Reprositories.CompanyInformationModelReprository;
import com.example.azimovTemplate.Services.Reprositories.UserModelReprository;
import com.example.azimovTemplate.Services.Reprositories.UsersInformationModelReprository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserModelDetailsService implements UserDetailsService {

    @Autowired
    private UserModelReprository reprository;

    @Override
    public UserModelDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> user = reprository.findByName(username);
        return user.map(UserModelDetails::new).orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }
}
