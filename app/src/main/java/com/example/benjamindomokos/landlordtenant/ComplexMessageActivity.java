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

//in this activity, the user can enter a complex message and send it by posting it to the database
public class ComplexMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complex_message);

        //getting data passed by previous activity
        Bundle extras = getIntent().getExtras();

        //the large text on the top
        TextView messageType = (TextView) findViewById(R.id.textType);
        //send button
        Button send = (Button) findViewById(R.id.buttonSendComplex);

        //getting the data required to pass to php script
        final EditText subject = (EditText) findViewById(R.id.etSubject);
        final EditText message = (EditText) findViewById(R.id.etMessage);
        final int group = extras.getInt("group");
        final int type = extras.getInt("messageType");

        //changing the title of the page according to the type of the message
        if (type == 1)
            messageType.setText("Maintenance Requests");
        else if (type == 2)
            messageType.setText("Complaints");
        else if (type == 3)
            messageType.setText("Reminders");

        //when the user clicks the send button
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //response listener to get the data returned by php script
                //CURRENTLY NO RESPONSE FROM SCRIPT
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                };

                //creating a hash map to post data to php script
                Map<String, String> parameters = new HashMap<String, String>();
                //putting the data in the hashmap
                parameters.put("subject", subject.getText().toString());
                parameters.put("message", message.getText().toString());
                parameters.put("group", group + "");
                parameters.put("type", type + "");

                //new custom string request
                CustomStringRequest sendRequest = new CustomStringRequest(parameters, "http://openexport.esy.es/postcomplex.php", responseListener);
                //new request queue to hold the requests
                RequestQueue queue = Volley.newRequestQueue(ComplexMessageActivity.this);
                //adding the request to the request queue
                queue.add(sendRequest);
            }
        });



    }
}
