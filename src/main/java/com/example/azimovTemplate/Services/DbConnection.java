package com.example.azimovTemplate.Services;

import com.example.azimovTemplate.Models.Test.TestModel;
import com.example.azimovTemplate.Models.User.UserModel;
import com.example.azimovTemplate.Services.Reprositories.TestModelReprository;
import com.example.azimovTemplate.Services.Reprositories.UserModelReprository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class DbConnection {
    private UserModelReprository userReprository;
    private TestModelReprository testReprository;

    public void addUser(UserModel user) {
        userReprository.save(user);
    }
    public void updateUser(UserModel prevUser, UserModel newUser) {
        userReprository.delete(prevUser);
        userReprository.save(newUser);
    }
    public void addTest(TestModel test) {
        testReprository.save(test);
    }
    public void removeTest(long id) {
        testReprository.deleteAllById(id);
    }

}
