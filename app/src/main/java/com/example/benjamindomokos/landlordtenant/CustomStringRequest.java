package com.example.benjamindomokos.landlordtenant;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
//this class extends the Stringrequest class by enabling passing parameters
public class CustomStringRequest extends StringRequest {
    //hashmap to store parameters
    private Map<String, String> params;
    //constructor
    public CustomStringRequest(Map parameters, String registerUrl, Response.Listener<String> listener){
        //calling constructor of super class
        super(Method.POST,registerUrl,listener,null);
        //storing parameters to be passed
        params = parameters;


    }

    @Override
    //used by volley library to get the parameters of a request
    public Map<String, String> getParams(){
        return params;
    }


}

