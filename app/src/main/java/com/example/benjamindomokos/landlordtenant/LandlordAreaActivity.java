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


        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LandlordAreaActivity.this, MessagesActivity.class);
                LandlordAreaActivity.this.startActivity(registerIntent);
            }
        });
    }
}
