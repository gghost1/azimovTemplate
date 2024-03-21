package com.example.azimovTemplate.Services;

import com.example.azimovTemplate.Models.User.UserModel;
import com.example.azimovTemplate.Models.User.UserModelDetails;
import com.example.azimovTemplate.Services.Reprositories.UserModelReprository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserModelDetailsService implements UserDetailsService {

    @Autowired
    private UserModelReprository reprository;

    @Override
    @Cacheable(value = "users", key = "#username")
    public UserModelDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> user = reprository.findByName(username);
        System.out.println("load");
        return user.map(UserModelDetails::new).orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }
    @CachePut(value = "users", key = "#user.name")
    public void updateUser(UserModel user) throws Exception {
        Optional<UserModel> userOld = reprository.findByName(user.getName());
        if (userOld.isEmpty()) throw new Exception();
        reprository.delete(userOld.orElseThrow());
        reprository.save(user);
    }
}
