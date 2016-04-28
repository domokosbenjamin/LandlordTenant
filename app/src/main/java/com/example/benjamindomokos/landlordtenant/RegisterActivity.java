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

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final Button register = (Button) findViewById(R.id.registerButton);
        final EditText firstName = (EditText) findViewById(R.id.fnText);
        final EditText lastName = (EditText) findViewById(R.id.lnText);
        final EditText username = (EditText) findViewById(R.id.usernameText);
        final EditText pwd = (EditText) findViewById(R.id.pwdText);

        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                String tmp = radioButton.getText().toString();
                //Toast.makeText(getApplicationContext(), tmp, Toast.LENGTH_LONG).show();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fName = firstName.getText().toString();
                final String lName = lastName.getText().toString();
                final String uName = username.getText().toString();
                final String password = pwd.getText().toString();
                int radioButtonID = radioGroup.getCheckedRadioButtonId();
                View radioButton = radioGroup.findViewById(radioButtonID);
                int index = radioGroup.indexOfChild(radioButton);
                //Toast.makeText(getApplicationContext(), index+"", Toast.LENGTH_LONG).show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject registerResponse = new JSONObject(response);

                            boolean registerSuccess = registerResponse.getBoolean("success");

                            if(registerSuccess == true){
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Failed to register").setNegativeButton("Retry",null)
                                        .create().show();
                            }
                        } catch (JSONException ex){
                            ex.printStackTrace();
                        }
                    }
                };

                Map<String, String > parameters = new HashMap<String, String>();
                parameters.put("firstname",fName);
                parameters.put("lastname", lName);
                parameters.put("username",uName);
                parameters.put("password",password);
                parameters.put("type",index+"");

                CustomStringRequest registerRequest = new CustomStringRequest(parameters, "http://openexport.esy.es/register.php", responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
