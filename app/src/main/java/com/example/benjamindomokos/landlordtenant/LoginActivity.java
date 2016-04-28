package com.example.benjamindomokos.landlordtenant;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button login = (Button) findViewById(R.id.loginButton);
        final EditText username = (EditText) findViewById(R.id.usernameText);
        final EditText pwd = (EditText) findViewById(R.id.passwordText);
        final TextView register = (TextView) findViewById(R.id.registerText);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String uName = username.getText().toString();
                final String password = pwd.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject registerResponse = new JSONObject(response);

                            boolean registerSuccess = registerResponse.getBoolean("success");
                            boolean userIsInGroup = registerResponse.getBoolean("group");
                            int userType = registerResponse.getInt("type");

                            if(registerSuccess & userIsInGroup){
                                if(userType == 0) {
                                    Intent intent = new Intent(LoginActivity.this, LandlordAreaActivity.class);
                                    intent.putExtra("id",registerResponse.getInt("id"));
                                    //intent.putExtra("group",registerResponse.getInt("gr_id"));
                                    LoginActivity.this.startActivity(intent);
                                }else{
                                    Intent intent = new Intent(LoginActivity.this, TenantAreaActivity.class);
                                    intent.putExtra("details",response);
                                    LoginActivity.this.startActivity(intent);
                                }
                            }else if(!userIsInGroup) {
                                if(userType == 0) {
                                    Intent intent = new Intent(LoginActivity.this, CreateGroupActivity.class);
                                    intent.putExtra("id",registerResponse.getInt("id"));
                                    //intent.putExtra("group",registerResponse.getInt("group"));
                                    LoginActivity.this.startActivity(intent);
                                }else{
                                    Intent intent = new Intent(LoginActivity.this, PreTenantAreaActivity.class);
                                    intent.putExtra("details",response);
                                    LoginActivity.this.startActivity(intent);

                                }
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Failed to register").setNegativeButton("Retry",null)
                                        .create().show();
                            }
                        } catch (JSONException ex){
                            ex.printStackTrace();
                        }
                    }
                };
                Map<String, String > parameters = new HashMap<String, String>();
                parameters.put("username",uName);
                parameters.put("password",password);

                CustomStringRequest loginRequest = new CustomStringRequest(parameters, "http://openexport.esy.es/login.php",responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}

