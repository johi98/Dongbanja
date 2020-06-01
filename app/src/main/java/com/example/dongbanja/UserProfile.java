package com.example.dongbanja;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;
@IgnoreExtraProperties
public class UserProfile {
    public String name, education, phonenumber, length, job, money, family, drunksmoke, living, salary, religion;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public UserProfile() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("education", education);
        result.put("phonenumber", phonenumber);
        result.put("length", length);
        result.put("job", job);
        result.put("money", money);
        result.put("family", family);
        result.put("drunksmoke", drunksmoke);
        result.put("living", living);
        result.put("salary", salary);
        result.put("religion", religion);



        return result;
    }
}