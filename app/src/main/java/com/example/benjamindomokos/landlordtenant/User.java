package com.example.benjamindomokos.landlordtenant;

//this class holds certain the information about a user of the application
public class User {
    private String firstname, lastname, username;

    //constructor
    public User(String firstname1, String lastname1, String username1){
        firstname = firstname1;
        lastname = lastname1;
        username = username1;
    }

    //getters
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
