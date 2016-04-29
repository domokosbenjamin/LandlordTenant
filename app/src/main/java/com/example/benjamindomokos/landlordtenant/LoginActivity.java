package com.example.benjamindomokos.landlordtenant;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
//this activity enables the user to log in or to go to the registration page
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getting the views from the device screen
        final Button login = (Button) findViewById(R.id.loginButton);
        final EditText username = (EditText) findViewById(R.id.usernameText);
        final EditText pwd = (EditText) findViewById(R.id.passwordText);
        final TextView register = (TextView) findViewById(R.id.registerText);

        //if the user clicked the register here text, start new activity to enable user to register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        //if the user clicked the login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting the text from the textfields on the screen
                final String uName = username.getText().toString();
                final String password = pwd.getText().toString();

                //response listener for php response
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the response to a json object
                            JSONObject loginResponse = new JSONObject(response);

                            //getting the data returned from the php script
                            boolean registerSuccess = loginResponse.getBoolean("success");
                            boolean userIsInGroup = loginResponse.getBoolean("group");
                            int userType = loginResponse.getInt("type");

                            //if the user is registered and is part of a group
                            if(registerSuccess & userIsInGroup){
                                //if the user is a tenant
                                if (userType == 1){
                                    //start tenant area activity and pass data to the activity
                                    Intent intent = new Intent(LoginActivity.this, TenantAreaActivity.class);
                                    Bundle extras = new Bundle();
                                    extras.putInt("id", loginResponse.getInt("id"));
                                    extras.putString("name", loginResponse.getString("name"));
                                    extras.putInt("group", loginResponse.getInt("gr_id"));

                                    intent.putExtras(extras);
                                    LoginActivity.this.startActivity(intent);
                                }
                                //else if the user is a landlord
                                else if(userType == 0) {
                                    //start landlord area activity and pass data about the user to the activity
                                    Intent intent = new Intent(LoginActivity.this, LandlordAreaActivity.class);
                                    Bundle extras = new Bundle();
                                    extras.putInt("id", loginResponse.getInt("id"));
                                    extras.putString("name", loginResponse.getString("name"));
                                    extras.putInt("group", loginResponse.getInt("gr_id"));

                                    intent.putExtras(extras);
                                    LoginActivity.this.startActivity(intent);
                                }

                                //else if the user is not in a group
                            }else if(!userIsInGroup) {
                                //if the user is a landloard
                                if(userType == 1) {

                                    //if the user is a tenant, start activity to display a message to the user
                                    Intent intent = new Intent(LoginActivity.this, PreTenantAreaActivity.class);
                                    LoginActivity.this.startActivity(intent);

                                }
                                //if login unsuccesfull, display alert dialog
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Failed to login").setNegativeButton("Retry",null)
                                        .create().show();
                            }
                            //catching exceptions
                        } catch (JSONException ex){
                            ex.printStackTrace();
                        }
                    }
                };
                //hashmap to pass the data to php script
                Map<String, String > parameters = new HashMap<String, String>();
                //put username and password entered
                parameters.put("username",uName);
                parameters.put("password",password);

                //create new request
                CustomStringRequest loginRequest = new CustomStringRequest(parameters, "http://openexport.esy.es/login.php",responseListener);
                //create request queue and add request to the queue
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}

