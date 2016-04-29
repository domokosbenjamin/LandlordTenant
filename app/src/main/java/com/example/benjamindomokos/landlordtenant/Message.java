package com.example.benjamindomokos.landlordtenant;

public class Message {
    private String sender, time, message;
    public Message(String sender1, String time1, String text1){
        sender = sender1;
        time = time1;
        message = text1;
    }

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
