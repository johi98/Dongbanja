package com.example.dongbanja;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class UserProfile {
    public String name, gender, education, phonenumber, length, job, money, family, drunksmoke, living, salary, religion;



    public UserProfile(String s, String gender, String education, String phonenumber, String length, String job, String money, String family, String drunksmoke, String living, String salary, String name) {
        this.name = name;
        this.gender = this.gender;
        this.education = this.education;
        this.phonenumber = this.phonenumber;
        this.length = this.length;
        this.job = this.job;
        this.money = this.money;
        this.family = this.family;
        this.drunksmoke = this.drunksmoke;
        this.living = this.living;
        this.salary = this.salary;
        this.religion = religion;
    }

    public String getName() {
        return name;
    }
    public String getGender() {
        return gender;
    }
    public String getEducation() {
        return education;
    }
    public String getPhonenumber() {
        return phonenumber;
    }
    public String getLength() {
        return length;
    }
    public String getJob() {
        return job;
    }
    public String getMoney() {
        return money;
    }
    public String getFamily() {
        return family;
    }
    public String getDrunksmoke() {
        return drunksmoke;
    }
    public String getLiving() {
        return living;
    }
    public String getSalary() {
        return salary;
    }
    public String getReligion() {
        return religion;
    }
}