package com.firebasedongbanja.dongbanja;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;
//데이터 전송
@IgnoreExtraProperties
public class FirebasePost {
    public String name, gender, education, phonenumber, length, job, money, family, drunksmoke, living, salary, religion;

    

    public FirebasePost(String name) {
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


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("gender", gender);
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