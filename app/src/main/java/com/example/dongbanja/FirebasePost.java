package com.example.dongbanja;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;
//데이터 전송
@IgnoreExtraProperties
public class FirebasePost {
 //   public String id;
    public String name;
 //   public Long age;
 //   public String gender;

    public FirebasePost(){
        // Default constructor required for calls to DataSnapshot.getValue(FirebasePost.class)
    }

    public FirebasePost( String name) {

        this.name = name;

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
   //     result.put("id", id);
        result.put("name", name);
    //    result.put("age", age);
   //     result.put("gender", gender);
        return result;
    }
}