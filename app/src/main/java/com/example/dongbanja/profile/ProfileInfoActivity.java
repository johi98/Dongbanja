package com.example.dongbanja.profile;

public class ProfileInfoActivity {
    private String name;
    private String age;
    private String phonenumber;
    private String birthday;
    private String job;
    private String freetext;

    public ProfileInfoActivity(String name, String age, String phonenumber, String birthday, String job, String freetext)
    {
        this.name = name;
        this.age = age;
        this.phonenumber = phonenumber;
        this.birthday = birthday;
        this.job = job;
        this.freetext = freetext;
    }
    public String getName()
    {
        return this.age;
    }
    public void setName(String name)
    {
        this.age = name;
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
