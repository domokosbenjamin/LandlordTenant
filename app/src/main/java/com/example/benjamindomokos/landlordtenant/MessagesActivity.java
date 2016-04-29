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

//this activity enables users to post messages to eachoder in a group
public class MessagesActivity extends AppCompatActivity {

    ListView messageListView;
    List <Message> messageList = new ArrayList<Message>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        //getting the views from the screen
        Button refreshButton = (Button) findViewById(R.id.buttonRefresh);
        Button sendButton = (Button) findViewById(R.id.buttonSend);
        final TextView messageTextView   = (TextView) findViewById(R.id.textMessage);
        messageListView = (ListView) findViewById(R.id.listView2);

        //getting the data passed from previous activity
        Bundle extras = getIntent().getExtras();
        //the group of the user
        final int group = extras.getInt("group");
        //name of user
        final String name = extras.getString("name");

        //when the user clicks send button
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //listening for response from php script
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                };

                //hash map to post variables to script
                Map<String, String > parameters = new HashMap<String, String>();
                parameters.put("sender",name);
                parameters.put("group",group+"");
                parameters.put("message", messageTextView.getText().toString());

                //creating new request with the variables passed
                CustomStringRequest loginRequest = new CustomStringRequest(parameters, "http://openexport.esy.es/postmessage.php",responseListener);
                //creating new request queue
                RequestQueue queue = Volley.newRequestQueue(MessagesActivity.this);
                //adding request to queue
                queue.add(loginRequest);
            }
        });

        //if the user clicks refresh button
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this is so that every time the user clicks refresh the listview is cleared
                messageList = new ArrayList<Message>();
                messageListView.setAdapter(null);

                //on responsefrom php script
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray array = new JSONArray();
                        try {
                            //converting response into a json object
                            JSONObject json = new JSONObject(response);
                            //getting the list of messages
                            array = json.getJSONArray("value");
                            //looping through the messages
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject user = array.getJSONObject(i);
                                String sender = user.getString("sender");
                                String time = user.getString("time");
                                String message = user.getString("message");
                                //instantiating message class and adding to message list
                                messageList.add(new Message(sender, time, message));
                                // Log.d("added", firstname + lastname + username);
                            }

                            //creating new adapter with the updated message list
                            MessageListAdapter adapter = new MessageListAdapter();
                            //setting adapter as the adapter of the listview
                            messageListView.setAdapter(adapter);

                        //catching exception
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                //hash map to post the group of the user to the php script
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("group", group + "");

                //creating request, request queue and adding request to queue
                CustomStringRequest request = new CustomStringRequest(parameters, "http://openexport.esy.es/getmessages.php", responseListener);
                RequestQueue queue = Volley.newRequestQueue(MessagesActivity.this);
                queue.add(request);
            }
        });
    }

    //custom adapter for the listview
    public class MessageListAdapter extends ArrayAdapter<Message> {
        public MessageListAdapter(){
            //calling constructor of super with the layout of the message in the list view, and the message list
            super (MessagesActivity.this, R.layout.message_layout, messageList);
        }

        //getting a view on a position
        @Override
        public View getView(int position, View view, ViewGroup parent){
            //if view does not exist, inflate it
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.message_layout, parent, false);

            //setting the text on the view with the information from the message
            Message user = messageList.get(position);

            TextView name = (TextView) view.findViewById(R.id.textMessage);
            name.setText(user.getMessage());

            TextView sender = (TextView) view.findViewById(R.id.textSender);
            sender.setText(user.getSender());


            TextView time = (TextView) view.findViewById(R.id.textTime);
            time.setText(user.getTime());

            return view;
        }
    }
}

