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
    @Autowired
    private UsersInformationModelReprository profileRepository;
    @Autowired
    private CompanyInformationModelReprository companyReprository;

    @Override
    public UserModelDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> user = reprository.findByName(username);
        return user.map(UserModelDetails::new).orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }
    public void updateUser(UserModel user) throws Exception {
        Optional<UserModel> userOld = reprository.findByName(user.getName());
        if (userOld.isEmpty()) throw new Exception();
        if (user.isCompany()) {
            CompanyProfileModel profile = (CompanyProfileModel) companyReprository.findById(user.getId()).orElseThrow();
            companyReprository.delete(profile);
            reprository.delete(userOld.orElseThrow());
            reprository.save(user);
            profile.setUser(reprository.findByName(user.getName()).orElseThrow());
            companyReprository.save(profile);
        } else {
            UsersProfile profile = profileRepository.findUsersProfileById(user.getId()).orElseThrow();
            profileRepository.delete(profile);
            reprository.delete(userOld.orElseThrow());
            reprository.save(user);
            profile.setUser(reprository.findByName(user.getName()).orElseThrow());
            profileRepository.save(profile);
        }
    }
}
