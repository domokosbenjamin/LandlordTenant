package com.example.benjamindomokos.landlordtenant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
//This activity displays messages on the screen with the subject, time and the message
public class DisplayComplexMessages extends AppCompatActivity {
    //creating listview and list of messages
    ListView messageListView;
    List<ComplexMessage> messageList = new ArrayList<ComplexMessage>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_complex_messages);

        //getting data from previous activity
        Bundle extras = getIntent().getExtras();

        //getting the view objects from the screen
        TextView messageType = (TextView) findViewById(R.id.textComplexType);
        messageListView = (ListView) findViewById(R.id.listView4);
        //setting the title of the screen depending on the type of message
        if (extras.getInt("messageType") == 1)
            messageType.setText("Maintenance Requests");
        else if (extras.getInt("messageType") == 2)
            messageType.setText("Complaints");
        else if (extras.getInt("messageType") == 3)
            messageType.setText("Reminders");

        //listener for php script response
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray array = new JSONArray();
                try {
                    //converting response to a json object
                    JSONObject json = new JSONObject(response);
                    //getting the array stored on the "value" field
                    array = json.getJSONArray("value");
                    //looping through the array
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject user = array.getJSONObject(i);
                        String subject = user.getString("subject");
                        String time = user.getString("time");
                        String message = user.getString("message");
                        //creating a new message object with the data from the array
                        messageList.add(new ComplexMessage(subject,time,message));
                    }
                    //creating ney adatper with the updated arraylist
                    ComplexMessageListAdapter adapter = new ComplexMessageListAdapter();
                    //setting the adapter as the adapter of the listview
                    messageListView.setAdapter(adapter);

                //catching esceptions
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        //new hashmap to post parameters to php script
        Map<String ,String > parameters = new HashMap<String, String>();

        parameters.put("group", extras.getInt("group")+"");
        parameters.put("type", extras.getInt("messageType")+"");

        //creating new string request, request queue and adding request to the queue
        CustomStringRequest request = new CustomStringRequest(parameters, "http://openexport.esy.es/getcomplex.php", responseListener);
        RequestQueue queue = Volley.newRequestQueue(DisplayComplexMessages.this);
        queue.add(request);

    }

    //list adapter for the complex message list
    public class ComplexMessageListAdapter extends ArrayAdapter<ComplexMessage> {
        public ComplexMessageListAdapter(){
            //calling constructor of super class with the layout file and list of the complex message
             super (DisplayComplexMessages.this, R.layout.complex_message_layout, messageList);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        //if the view does not exist, inflate it
        if (view == null)
            view = getLayoutInflater().inflate(R.layout.complex_message_layout, parent, false);
        //getting the object on the specific position from the message list
        ComplexMessage message = messageList.get(position);
        //setting the text of the text views to the data stored in the message object
        TextView messageText = (TextView) view.findViewById(R.id.textMessage);
        messageText.setText(message.getMessage());

        TextView subject = (TextView) view.findViewById(R.id.textSubject);
        subject.setText(message.getSubject());


        TextView time = (TextView) view.findViewById(R.id.textTime);
        time.setText(message.getTime());

        return view;
    }
}
}



