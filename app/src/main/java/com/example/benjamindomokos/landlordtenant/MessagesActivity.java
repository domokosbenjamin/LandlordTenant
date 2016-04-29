package com.example.benjamindomokos.landlordtenant;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;
import java.util.Map;

public class MessagesActivity extends AppCompatActivity {
    ListView messageListView;
    List <Message> messageList = new ArrayList<Message>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        Button searchButton = (Button) findViewById(R.id.buttonRefresh);
        Button sendButton = (Button) findViewById(R.id.buttonSend);
        final TextView textView   = (TextView) findViewById(R.id.textMessage);
        messageListView = (ListView) findViewById(R.id.listView2);

        //final Bundle extras = getIntent().getExtras();
        Bundle extras = getIntent().getExtras();
        final int group = extras.getInt("group");
        final String name = extras.getString("name");

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = name+group+"";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                };

                Map<String, String > parameters = new HashMap<String, String>();
                parameters.put("sender",name);
                parameters.put("group",group+"");
                parameters.put("message",textView.getText().toString());


                CustomStringRequest loginRequest = new CustomStringRequest(parameters, "http://openexport.esy.es/postmessage.php",responseListener);
                RequestQueue queue = Volley.newRequestQueue(MessagesActivity.this);
                queue.add(loginRequest);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageList = new ArrayList<Message>();
                messageListView.setAdapter(null);


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray array = new JSONArray();
                        try {
                            JSONObject json = new JSONObject(response);
                            array = json.getJSONArray("value");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject user = array.getJSONObject(i);
                                String sender = user.getString("sender");
                                String time = user.getString("time");
                                String message = user.getString("message");

                                messageList.add(new Message(sender, time, message));
                               // Log.d("added", firstname + lastname + username);

                            }
                            MessageListAdapter adapter = new MessageListAdapter();
                            messageListView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Map<String ,String > parameters = new HashMap<String, String>();

                parameters.put("group", group+"");
                CustomStringRequest request = new CustomStringRequest(parameters, "http://openexport.esy.es/getmessages.php", responseListener);
                RequestQueue queue = Volley.newRequestQueue(MessagesActivity.this);
                queue.add(request);
            }
        });
    }

    public class MessageListAdapter extends ArrayAdapter<Message> {
        public MessageListAdapter(){
            super (MessagesActivity.this, R.layout.message_layout, messageList);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.message_layout, parent, false);
            Message user = messageList.get(position);
            TextView name = (TextView) view.findViewById(R.id.textMessage);
            name.setText(user.getMessage());

            TextView sender = (TextView) view.findViewById(R.id.textSender);
            sender.setText(user.getSender());


            TextView time = (TextView) view.findViewById(R.id.textTime);
            time.setText(user.getTime());

            //view.setBackgroundColor(0xFFDED2BE);



            return view;
        }
    }
}

