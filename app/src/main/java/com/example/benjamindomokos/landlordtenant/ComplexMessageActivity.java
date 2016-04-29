package com.example.benjamindomokos.landlordtenant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ComplexMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complex_message);

        Bundle extras = getIntent().getExtras();

        TextView messageType = (TextView) findViewById(R.id.textType);
        Button send = (Button) findViewById(R.id.buttonSendComplex);

        final EditText subject = (EditText) findViewById(R.id.etSubject);
        final EditText message = (EditText) findViewById(R.id.etMessage);
        final int group = extras.getInt("group");
        final int type = extras.getInt("messageType");


        if (type == 1)
            messageType.setText("Maintenance Requests");
        else if (type == 2)
            messageType.setText("Complaints");
        else if (type == 3)
            messageType.setText("Reminders");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                };

                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("subject", subject.getText().toString());
                parameters.put("message", message.getText().toString());
                parameters.put("group", group + "");
                parameters.put("type", type + "");


                CustomStringRequest loginRequest = new CustomStringRequest(parameters, "http://openexport.esy.es/postcomplex.php", responseListener);
                RequestQueue queue = Volley.newRequestQueue(ComplexMessageActivity.this);
                queue.add(loginRequest);
            }
        });



    }
}
