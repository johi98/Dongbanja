package com.firebasedongbanja.dongbanja;

public class UserInfoModel {
    public String gender;
    public String uid;

    public UserInfoModel() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserInfoModel(String gender, String uid) {
        this.gender = gender;
        this.uid = uid;
    }
}
