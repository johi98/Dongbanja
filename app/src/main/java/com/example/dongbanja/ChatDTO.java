package com.example.dongbanja;

public class ChatDTO {


    private String message;
    private String uid;
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public ChatDTO() {}
    public ChatDTO( String message,String uid) {

        this.message = message;
        this.uid = uid;
    }



    public void setMessage(String message) {
        this.message = message;
    }



    public String getMessage() {
        return message;
    }
}
