package com.example.benjamindomokos.landlordtenant;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//in this activity, a landlord can create a group by entering the address and the name of the group
public class CreateGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        // getting the buttons and textfields from screen
        Button doneButton = (Button) findViewById(R.id.buttonDone);
        final TextView address   = (TextView) findViewById(R.id.etAddress);
        final TextView name   = (TextView) findViewById(R.id.etName);

        //getting data passed from previous activity using intents
        final Bundle extras = getIntent().getExtras();

        //when the user clicks the done button
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creatin hashmap to post data to php script
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("address", address.getText().toString());
                parameters.put("name", name.getText().toString());
                parameters.put("id",extras.getInt("id")+"");

                //getting the response from php script as a string
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to a json object
                            JSONObject registerResponse = new JSONObject(response);
                            //getting the outcome of the insert operation
                            boolean insertSuccess = registerResponse.getBoolean("success");
                            //if the operation was successful
                            if(insertSuccess) {
                                //starting new the landlord area activity
                                Intent intent = new Intent(CreateGroupActivity.this, LandlordAreaActivity.class);
                                CreateGroupActivity.this.startActivity(intent);
                            }else{
                                //if it was not successful, show alert dialog with message
                                AlertDialog.Builder builder = new AlertDialog.Builder(CreateGroupActivity.this);
                                builder.setMessage("Error while creating group.").setNegativeButton("Retry",null)
                                        .create().show();
                            }


                        //catching exceptions
                        } catch (JSONException ex){
                            ex.printStackTrace();
                        }
                    }
                };
                //new request
                CustomStringRequest request = new CustomStringRequest(parameters,"http://openexport.esy.es/creategroup.php",responseListener);
                //new queue for the requests
                RequestQueue queue = Volley.newRequestQueue(CreateGroupActivity.this);
                //adding request
                queue.add(request);
            }
        });
    }
}
