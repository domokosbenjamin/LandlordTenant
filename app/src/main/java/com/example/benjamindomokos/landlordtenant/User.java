package com.example.benjamindomokos.landlordtenant;

public class User {
    private String firstname, lastname, username;
    public User(String firstname1, String lastname1, String username1){
        firstname = firstname1;
        lastname = lastname1;
        username = username1;
    }

    public String getFirstname(){
        return firstname;
    }

    public String getLastname(){
        return lastname;
    }

    public String getUsername(){
        return username;
    }
}
