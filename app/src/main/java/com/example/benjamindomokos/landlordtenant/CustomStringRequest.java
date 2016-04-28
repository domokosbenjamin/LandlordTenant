package com.example.benjamindomokos.landlordtenant;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CustomStringRequest extends StringRequest {

    private Map<String, String> params;

    public CustomStringRequest(Map parameters, String registerUrl, Response.Listener<String> listener){
        super(Method.POST,registerUrl,listener,null);
        params = parameters;


    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }


}

