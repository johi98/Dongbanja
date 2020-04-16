package com.example.dongbanja.profile;

public class ProfileInfoActivity {
    private String name;
    private String age;
    private String phonenumber;
    private String birthday;
    private String job;
    private String freetext;

    public ProfileInfoActivity(String age, String phonenumber, String birthday, String job, String freetext)
    {
        this.age = age;
        this.phonenumber = phonenumber;
        this.birthday = birthday;
        this.job = job;
        this.freetext = freetext;
    }

    public String getAge()
    {
        return this.age;
    }
    public void setAge(String age)
    {
        this.age = age;
    }

}
