package com.example.dongbanja;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;
//데이터 전송
@IgnoreExtraProperties
public class FirebasePost {
    public String name;

    

    public FirebasePost(String name) {
        this.name = name;
    }



    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        return result;
    }
}