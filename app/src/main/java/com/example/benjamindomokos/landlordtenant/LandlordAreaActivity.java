package com.example.benjamindomokos.landlordtenant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//this activity is the main landlord area where the user can send messages, view messages and add tenants to the group
public class LandlordAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_area);

        //getting the buttons from the screen
        final Button messages = (Button) findViewById(R.id.buttonMessages);
        final Button addUser = (Button) findViewById(R.id.buttonAddUser);
        final Button maintainance = (Button) findViewById(R.id.buttonMaintainance);
        final Button complaints = (Button) findViewById(R.id.buttonComplaints);
        final Button reminder = (Button) findViewById(R.id.buttonReminder);

        //if the user clicked "Maintenance requests"
        maintainance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //starting new activity
                Intent intent = new Intent(LandlordAreaActivity.this, DisplayComplexMessages.class);
                //passing data to new activity
                Bundle extras = getIntent().getExtras();
                //the type of the message
                extras.putInt("messageType",1);
                intent.putExtras(extras);
                LandlordAreaActivity.this.startActivity(intent);
            }
        });

        //if the user clicked "Complaints
        complaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start new activity to display complaints
                Intent intent = new Intent(LandlordAreaActivity.this, DisplayComplexMessages.class);
                Bundle extras = getIntent().getExtras();
                extras.putInt("messageType",2);

                intent.putExtras(extras);
                LandlordAreaActivity.this.startActivity(intent);
            }
        });

        //when the usr clicked reminders, start new activity to display reminedrs
        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandlordAreaActivity.this, ComplexMessageActivity.class);
                Bundle extras = getIntent().getExtras();
                extras.putInt("messageType",3);

                intent.putExtras(extras);
                LandlordAreaActivity.this.startActivity(intent);
            }
        });

        //when the user clicked Add user, start new activity to search and add new user to group
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandlordAreaActivity.this, TenantSearchActivity.class);
                Bundle extras = getIntent().getExtras();
                intent.putExtras(extras);
                LandlordAreaActivity.this.startActivity(intent);
            }
        });

        //if the user clicked Messages, start new activity to send and display messages
        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandlordAreaActivity.this, MessagesActivity.class);
                Bundle extras = getIntent().getExtras();
                intent.putExtras(extras);
                LandlordAreaActivity.this.startActivity(intent);
            }
        });
    }
}
