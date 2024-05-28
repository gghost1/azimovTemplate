package com.example.azimovTemplate.Services;

import com.example.azimovTemplate.Models.User.UserModel;
import com.example.azimovTemplate.Models.User.UserModelDetails;
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

    @Override
    public UserModelDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> user = reprository.findByName(username);
        return user.map(UserModelDetails::new).orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }
    public void updateUser(UserModel user) throws Exception {
        Optional<UserModel> userOld = reprository.findByName(user.getName());
        if (userOld.isEmpty()) throw new Exception();
        UsersProfile profile = profileRepository.findUsersProfileById(user.getId()).orElseThrow();
        profileRepository.delete(profile);
        reprository.delete(userOld.orElseThrow());
        reprository.save(user);
        profileRepository.save(profile);
    }
}
