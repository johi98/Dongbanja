package com.example.dongbanja;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class UserProfile {
    public String name, education, phonenumber, length, job, money, family, drunksmoke, living, salary, religion;



    public UserProfile(){}

    public UserProfile(String name, String education, String phonenumber, String length, String job, String money, String family, String drunksmoke, String living, String salary, String religion) {
        this.name = name;
        this.education = education;
        this.phonenumber = phonenumber;
        this.length = length;
        this.job = job;
        this.money = money;
        this.family = family;
        this.drunksmoke = drunksmoke;
        this.living = living;
        this.salary = salary;
        this.religion = religion;
    }

    public String getName() {return name; }
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