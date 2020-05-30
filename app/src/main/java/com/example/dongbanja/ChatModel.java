package com.example.dongbanja;

public class ChatModel {

    public String uid1; // 현재 사용자(로그인한)
    public String uid2;// 상대 uid

    public ChatModel() {}

    public ChatModel(String uid1,String uid2){

        this.uid1 = uid1;
        this.uid1 = uid2;
    }
    public void setuid1(String message) {
            this.uid1 = uid1;
    }
    public void setuid2(String message) {
        this.uid1 = uid2;
    }




    public String getuid1() {
        return uid1;
    }


    public String getuid2() {
        return uid2;
    }

}
