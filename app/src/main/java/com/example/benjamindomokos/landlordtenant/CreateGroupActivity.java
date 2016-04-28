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

public class CreateGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        Button doneButton = (Button) findViewById(R.id.buttonDone);
        final TextView address   = (TextView) findViewById(R.id.etAddress);
        final TextView name   = (TextView) findViewById(R.id.etName);

        final Bundle extras = getIntent().getExtras();


        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("address", address.getText().toString());
                parameters.put("name", name.getText().toString());
                parameters.put("id",extras.getInt("id")+"");


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject registerResponse = new JSONObject(response);

                            boolean insertSuccess = registerResponse.getBoolean("success");

                            if(insertSuccess) {

                                Intent intent = new Intent(CreateGroupActivity.this, TenantSearchActivity.class);
                                CreateGroupActivity.this.startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(CreateGroupActivity.this);
                                builder.setMessage("Error while creating group.").setNegativeButton("Retry",null)
                                        .create().show();
                            }



                        } catch (JSONException ex){
                            ex.printStackTrace();
                        }
                    }
                };
                CustomStringRequest request = new CustomStringRequest(parameters,"http://openexport.esy.es/creategroup.php",responseListener);

                RequestQueue queue = Volley.newRequestQueue(CreateGroupActivity.this);
                queue.add(request);
            }
        });
    }
}
