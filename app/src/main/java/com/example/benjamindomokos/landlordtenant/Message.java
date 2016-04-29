package com.example.benjamindomokos.landlordtenant;

//this class holds a single message including sender, time and the message itself
public class Message {
    private String sender, time, message;

    //constructor
    public Message(String sender1, String time1, String text1){
        sender = sender1;
        time = time1;
        message = text1;
    }

    //getters
    public String getSender(){
        return sender;
    }

    public String getTime(){
        return time;
    }

    public String getMessage(){
        return message;
    }
}
