package com.example.azimovTemplate.Services;

import com.example.azimovTemplate.Models.Tables.User.CompanyProfileModel;
import com.example.azimovTemplate.Models.Tables.User.UserModel;
import com.example.azimovTemplate.Models.Tables.User.UsersProfile;
import com.example.azimovTemplate.Services.Reprositories.CompanyInformationModelReprository;
import com.example.azimovTemplate.Services.Reprositories.UserModelReprository;
import com.example.azimovTemplate.Services.Reprositories.UsersInformationModelReprository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class DbConnection {

    private UserModelReprository userReprository;
    private UsersInformationModelReprository userProfileReprository;
    private CompanyInformationModelReprository companyProfileReprository;

    public void addUser(UserModel user) {
        userReprository.save(user);
        if (!user.isCompany()) {
            UsersProfile profile = new UsersProfile();
            profile.setUser(user);
            userProfileReprository.save(profile);
        } else {
            CompanyProfileModel profile = new CompanyProfileModel();
            profile.setUser(user);
            companyProfileReprository.save(profile);
        }

    }
    public void updateUser(UserModel prevUser, UserModel newUser) {
        if (newUser.isCompany()) {
            CompanyProfileModel profile = (CompanyProfileModel) companyProfileReprository.findById(prevUser.getId()).orElse(null);
            companyProfileReprository.delete(profile);
            userReprository.delete(prevUser);
            userReprository.save(newUser);
            profile.setUser(userReprository.findByName(newUser.getName()).orElseThrow());
            companyProfileReprository.save(profile);
        } else {
            UsersProfile profile = userProfileReprository.findUsersProfileById(prevUser.getId()).orElse(null);
            userProfileReprository.delete(profile);
            userReprository.delete(prevUser);
            userReprository.save(newUser);
            profile.setUser(userReprository.findByName(newUser.getName()).orElseThrow());
            userProfileReprository.save(profile);
        }
    }



    public void updateCompanyProfile(CompanyProfileModel profile) {
        @SuppressWarnings("unchecked")
        CompanyProfileModel prevCompany = (CompanyProfileModel) companyProfileReprository.findById(profile.getId()).orElse(null);
        companyProfileReprository.delete(prevCompany);
        companyProfileReprository.save(profile);
    }

    public void updateUsersProfile(UsersProfile profile) {
        UsersProfile prevUser = (UsersProfile) userProfileReprository.findById(profile.getId()).orElse(null);
        userProfileReprository.delete(prevUser);
        userProfileReprository.save(profile);
    }
}
