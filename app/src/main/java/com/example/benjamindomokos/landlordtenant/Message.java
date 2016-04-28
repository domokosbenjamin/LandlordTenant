package com.example.benjamindomokos.landlordtenant;

public class Message {
    private String sender, text, message;
    public Message(String sender1, String time1, String text1){
        sender = sender1;
        text = text1;
        message = time1;
    }

    public String getSender(){
        return sender;
    }

    public String getText(){
        return text;
    }

    public String getMessage(){
        return message;
    }
}
