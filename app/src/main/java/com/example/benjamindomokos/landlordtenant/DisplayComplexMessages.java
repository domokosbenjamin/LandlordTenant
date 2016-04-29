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

public class DisplayComplexMessages extends AppCompatActivity {

    ListView messageListView;
    List<ComplexMessage> messageList = new ArrayList<ComplexMessage>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_complex_messages);

        Bundle extras = getIntent().getExtras();

        TextView messageType = (TextView) findViewById(R.id.textComplexType);
        if (extras.getInt("messageType") == 1)
            messageType.setText("Maintenance Requests");
        else if (extras.getInt("messageType") == 2)
            messageType.setText("Complaints");
        else if (extras.getInt("messageType") == 3)
            messageType.setText("Reminders");



    }

    public class ComplexMessageListAdapter extends ArrayAdapter<ComplexMessage> {
        public ComplexMessageListAdapter(){
             super (DisplayComplexMessages.this, R.layout.complex_message_layout, messageList);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        if (view == null)
            view = getLayoutInflater().inflate(R.layout.complex_message_layout, parent, false);
        ComplexMessage user = messageList.get(position);
        TextView name = (TextView) view.findViewById(R.id.textMessage);
        name.setText(user.getMessage());

        TextView subject = (TextView) view.findViewById(R.id.textSubject);
        subject.setText(user.getSubject());


        TextView time = (TextView) view.findViewById(R.id.textTime);
        time.setText(user.getTime());
        return view;
    }
}
}



