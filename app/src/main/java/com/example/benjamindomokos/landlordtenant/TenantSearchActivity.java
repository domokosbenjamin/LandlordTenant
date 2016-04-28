package com.example.benjamindomokos.landlordtenant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TenantSearchActivity extends AppCompatActivity {
    List<User> userList = new ArrayList<User>();
    ListView userListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_search);

        Button searchButton = (Button) findViewById(R.id.searchButton);
        final TextView textView   = (TextView) findViewById(R.id.usernameText);
        userListView = (ListView) findViewById(R.id.listView);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userList = new ArrayList<User>();
                userListView.setAdapter(null);



                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray array = new JSONArray();
                        try {
                            JSONObject json = new JSONObject(response);
                            array = json.getJSONArray("value");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject user = array.getJSONObject(i);
                                String firstname = user.getString("firstname");
                                String lastname = user.getString("lastname");
                                String username = user.getString("username");

                                userList.add(new User(firstname, lastname, username));
                                Log.d("added",firstname+lastname+username);

                            }
                            UserListAdapter adapter = new UserListAdapter();
                            userListView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Map<String ,String > parameters = new HashMap<String, String>();
                String nameEntered = textView.getText().toString();
                Log.d("test", nameEntered);
                parameters.put("u", nameEntered);
                CustomStringRequest request = new CustomStringRequest(parameters,"http://openexport.esy.es/json.php",responseListener);
                RequestQueue queue = Volley.newRequestQueue(TenantSearchActivity.this);
                queue.add(request);
            }
        });
    }

    public class UserListAdapter extends ArrayAdapter<User> {
        public UserListAdapter(){
            super (TenantSearchActivity.this, R.layout.user_list_item, userList);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){
            if (view == null)
               view = getLayoutInflater().inflate(R.layout.user_list_item, parent, false);
            User user = userList.get(position);
            TextView name = (TextView) view.findViewById(R.id.nameText);
            name.setText(user.getFirstname()+" "+user.getLastname());

            TextView username = (TextView) view.findViewById(R.id.usernameText);
            username.setText(user.getUsername());

            return view;
        }
    }
}
