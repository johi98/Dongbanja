package com.example.dongbanja;

public class User {
    public String gender;
    public String uid;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String gender, String uid) {
        this.gender = gender;
        this.uid = uid;
    }
}
