package com.example.dongbanja;

public class ChatDTO {


    private String message;

    public ChatDTO() {}
    public ChatDTO( String message) {

        this.message = message;
    }



    public void setMessage(String message) {
        this.message = message;
    }



    public String getMessage() {
        return message;
    }
}
