package com.example.benjamindomokos.landlordtenant;

//This class stores the details about a "complex message", such as a complaint or maintenance request
public class ComplexMessage {
        private String subject, time, message;

    //constructor
        public ComplexMessage(String subject1, String time1, String text1){
            subject = subject1;
            time = time1;
            message = text1;
        }

    //getters
        public String getSubject(){
            return subject;
        }

        public String getTime(){
            return time;
        }

        public String getMessage(){
            return message;
        }
    }

