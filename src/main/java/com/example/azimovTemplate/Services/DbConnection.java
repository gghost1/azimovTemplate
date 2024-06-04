package com.example.azimovTemplate.Services;

import com.example.azimovTemplate.Models.Tables.NewsModel;
import com.example.azimovTemplate.Models.Tables.Steck;
import com.example.azimovTemplate.Models.Tables.User.CompanyProfileModel;
import com.example.azimovTemplate.Models.Tables.User.UserModel;
import com.example.azimovTemplate.Models.Tables.User.UsersProfile;
import com.example.azimovTemplate.Models.Tables.VacancyModel;
import com.example.azimovTemplate.Models.Tables.VacancyTestModel;
import com.example.azimovTemplate.Services.Reprositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class DbConnection {

    private UserModelReprository userReprository;
    private UsersInformationModelReprository userProfileReprository;
    private CompanyInformationModelReprository companyProfileReprository;
    private SteckModelReprository steckReprository;
    private VacancyTestModelReprository vacancyTestReprository;
    private VacancyModelReprository vacancyReprository;
    private NewsModelReprository newsReprository;


    public NewsModel addNews(NewsModel news) {
        return newsReprository.save(news);
    }
    public VacancyModel addVacancy(VacancyModel vacancy) {
        return vacancyReprository.save(vacancy);
    }
    public VacancyTestModel addVacancyTest(VacancyTestModel vacancyTest) {
        return vacancyTestReprository.save(vacancyTest);
    }
    public UserModel addUser(UserModel user) {
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
        return findUserByName(user.getName());
    }
    public void updateUser(UserModel newUser) {
        UserModel prevUser = userReprository.findByName(newUser.getName()).orElseThrow();
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
        CompanyProfileModel prevCompany = (CompanyProfileModel) companyProfileReprository.findById(profile.getId()).orElse(null);
        companyProfileReprository.delete(prevCompany);
        companyProfileReprository.save(profile);
    }

    public void updateUsersProfile(UsersProfile profile) {
        UsersProfile prevUser = userProfileReprository.findById(profile.getId()).orElse(null);
        userProfileReprository.delete(prevUser);
        userProfileReprository.save(profile);
    }

    public UserModel findUserByName(String name) {
        return userReprository.findByName(name).orElseThrow();
    }
    public CompanyProfileModel findCompanyProfileById(Long id) {
        return companyProfileReprository.findById(id).orElseThrow();
    }
    public UsersProfile findUserProfileById(Long id) {
        return userProfileReprository.findUsersProfileById(id).orElseThrow();
    }
    public Steck findSteckByName(String name) {
        return (Steck) steckReprository.getByName(name).orElseThrow();
    }

}
