package com.example.benjamindomokos.landlordtenant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

//this area allows the user to chose from several operations as a tenant
public class TenantAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_area);

        //getting the views from the screen
        final Button messages = (Button) findViewById(R.id.buttonMessages);
        final Button maintainance = (Button) findViewById(R.id.buttonMaintainance);
        final Button complaints = (Button) findViewById(R.id.buttonComplaints);
        final Button reminder = (Button) findViewById(R.id.buttonReminder);

        //if the user clicked maintenance, passing the data that was passed from the previous activity and also passing the type of message
        maintainance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(TenantAreaActivity.this, ComplexMessageActivity.class);
                Bundle extras = getIntent().getExtras();
                extras.putInt("messageType",1);
                registerIntent.putExtras(extras);
                TenantAreaActivity.this.startActivity(registerIntent);
            }
        });

        //if the user clicked complaints, passing the data that was passed from the previous activity and also passing the type of message
        complaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(TenantAreaActivity.this, ComplexMessageActivity.class);
                Bundle extras = getIntent().getExtras();
                extras.putInt("messageType",2);

                registerIntent.putExtras(extras);
                TenantAreaActivity.this.startActivity(registerIntent);
            }
        });

        //if the user clicked reminders, passing the data that was passed from the previous activity and also passing the type of message
        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(TenantAreaActivity.this, DisplayComplexMessages.class);
                Bundle extras = getIntent().getExtras();
                extras.putInt("messageType",3);

                registerIntent.putExtras(extras);
                TenantAreaActivity.this.startActivity(registerIntent);
            }
        });

        //if the user clicked send message, go to messages activity
        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(TenantAreaActivity.this, MessagesActivity.class);
                Bundle extras = getIntent().getExtras();
                registerIntent.putExtras(extras);
                TenantAreaActivity.this.startActivity(registerIntent);
            }
        });
    }
}
