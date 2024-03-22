package com.example.azimovTemplate.Models.User;


import com.example.azimovTemplate.Services.Reprositories.UserModelReprository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
        return user.map(UserModelDetails::new).orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }

    public UserModelDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        Optional<UserModel> user = reprository.findByEmail(email);
        return user.map(UserModelDetails::new).orElseThrow(() -> new UsernameNotFoundException(email + " not found"));
    }
    public UserModelDetails loadUserByPhone(String phone) throws UsernameNotFoundException {
        Optional<UserModel> user = reprository.findByPhone(phone);
        return user.map(UserModelDetails::new).orElseThrow(() -> new UsernameNotFoundException(phone + " not found"));
    }

    public void addUser(UserModel user) {
        reprository.save(user);
    }
    public void updateUser(UserModel user) throws Exception {
        Optional<UserModel> userOld = reprository.findByName(user.getName());
        if (userOld.isEmpty()) throw new Exception();
        reprository.delete(userOld.orElseThrow());
        reprository.save(user);
    }
}
