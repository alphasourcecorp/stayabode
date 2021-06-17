package stayabode.foodyHive.utils;

import android.content.Context;
import android.util.Log;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import com.android.volley.toolbox.StringRequest;
import stayabode.foodyHive.activities.consumers.ConsumerCheckOutActivity;

import java.util.HashMap;
import java.util.Map;

public class CustomVolleyRequest extends StringRequest {
    Context context;
    public CustomVolleyRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener, Context context) {
        super(method, url, listener, errorListener);
        this.context = context;
    }

    public CustomVolleyRequest(int get, String url, Response.Listener<String> booleanListener, Response.ErrorListener errorListener, ConsumerCheckOutActivity consumerCheckOutActivity) {
        super(get, url, booleanListener, errorListener);
        this.context = consumerCheckOutActivity;
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();

        headers.put("Authorization","Bearer "+ SaveSharedPreference.getAzureToken(context));
        Log.v("accessToken",SaveSharedPreference.getAzureToken(context));
        return headers;
    }

}