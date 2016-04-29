package com.example.benjamindomokos.landlordtenant;

public class ComplexMessage {
        private String subject, time, message;
        public ComplexMessage(String subject1, String time1, String text1){
            subject = subject1;
            time = time1;
            message = text1;
        }

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

