package com.example.benjamindomokos.landlordtenant;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//this activity allows the user to enther his details and register
public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //getting the views from the screen
        final Button register = (Button) findViewById(R.id.registerButton);
        final EditText firstName = (EditText) findViewById(R.id.fnText);
        final EditText lastName = (EditText) findViewById(R.id.lnText);
        final EditText username = (EditText) findViewById(R.id.usernameText);
        final EditText pwd = (EditText) findViewById(R.id.pwdText);
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        //if the user clicks register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the strings entered in the textfields
                final String fName = firstName.getText().toString();
                final String lName = lastName.getText().toString();
                final String uName = username.getText().toString();
                final String password = pwd.getText().toString();

                //getting the index of the radiobutton selected, meaning whether the user is a tenant or a landlord
                int radioButtonID = radioGroup.getCheckedRadioButtonId();
                View radioButton = radioGroup.findViewById(radioButtonID);
                int index = radioGroup.indexOfChild(radioButton);

                //on response from register php script
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the respose to a json object
                            JSONObject registerResponse = new JSONObject(response);

                            //this var stores the outcome of the registration, if true, it was successful
                            boolean registerSuccess = registerResponse.getBoolean("success");

                            //if the registration was successful, get back to login screen
                            if(registerSuccess){
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);

                            //if the registration was unsuccessful, display alert dialog
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Failed to register").setNegativeButton("Retry",null)
                                        .create().show();
                            }
                        //catching exception
                        } catch (JSONException ex){
                            ex.printStackTrace();
                        }
                    }
                };

                //hash map for posting the information to the php script
                Map<String, String > parameters = new HashMap<String, String>();
                parameters.put("firstname",fName);
                parameters.put("lastname", lName);
                parameters.put("username",uName);
                parameters.put("password",password);
                parameters.put("type",index+"");

                //creating request
                CustomStringRequest registerRequest = new CustomStringRequest(parameters, "http://openexport.esy.es/register.php", responseListener);
                //creating request queue
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                //adding request to queue
                queue.add(registerRequest);
            }
        });
    }
}
