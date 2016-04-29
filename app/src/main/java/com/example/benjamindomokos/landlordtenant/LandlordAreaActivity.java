package com.example.benjamindomokos.landlordtenant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LandlordAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_area);

        final Button messages = (Button) findViewById(R.id.buttonMessages);
        final Button addUser = (Button) findViewById(R.id.buttonAddUser);
        final Button maintainance = (Button) findViewById(R.id.buttonMaintainance);
        final Button complaints = (Button) findViewById(R.id.buttonComplaints);
        final Button reminder = (Button) findViewById(R.id.buttonReminder);

        maintainance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LandlordAreaActivity.this, DisplayComplexMessages.class);
                Bundle extras = getIntent().getExtras();
                extras.putInt("messageType",1);
                registerIntent.putExtras(extras);
                LandlordAreaActivity.this.startActivity(registerIntent);
            }
        });

        complaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LandlordAreaActivity.this, DisplayComplexMessages.class);
                Bundle extras = getIntent().getExtras();
                extras.putInt("messageType",2);

                registerIntent.putExtras(extras);
                LandlordAreaActivity.this.startActivity(registerIntent);
            }
        });

        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LandlordAreaActivity.this, ComplexMessageActivity.class);
                Bundle extras = getIntent().getExtras();
                extras.putInt("messageType",3);

                registerIntent.putExtras(extras);
                LandlordAreaActivity.this.startActivity(registerIntent);
            }
        });

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LandlordAreaActivity.this, TenantSearchActivity.class);
                Bundle extras = getIntent().getExtras();
                registerIntent.putExtras(extras);
                LandlordAreaActivity.this.startActivity(registerIntent);
            }
        });


        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LandlordAreaActivity.this, MessagesActivity.class);
                Bundle extras = getIntent().getExtras();
                registerIntent.putExtras(extras);
                LandlordAreaActivity.this.startActivity(registerIntent);
            }
        });
    }
}
