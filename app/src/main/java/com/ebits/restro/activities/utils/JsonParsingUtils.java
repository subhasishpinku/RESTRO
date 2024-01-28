package com.ebits.restro.activities.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ebits.restro.activities.application.RestroApplication;
import com.ebits.restro.activities.utils.constant.Consts;
import com.ebits.restro.activities.utils.interfaces.ServiceCallBack;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mr. Satyaki Mukherjee on 10-07-2016.
 */
public class JsonParsingUtils {

    private static JsonParsingUtils instance=null;

    private JsonParsingUtils(){}

    public static JsonParsingUtils getInstance(){
        if(instance==null){
            instance    =   new JsonParsingUtils();
        }
        return instance;
    }

    public static void stringRequest(final Map<String,String> sendParams, final ServiceCallBack scb){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Consts.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response",response);
                        scb.onServiceResponse(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Response",error.toString());
                        String errorName="Socket Exception";
                        // Handle your error types accordingly.For Timeout & No connection error, you can show 'retry' button.
                        // For AuthFailure, you can re login with user credentials.
                        // For ClientError, 400 & 401, Errors happening on client side when sending api request.
                        // In this case you can check how client is forming the api and debug accordingly.
                        // For ServerError 5xx, you can do retry or handle accordingly.
                        if( error instanceof NetworkError) {
                            errorName="Network Exception";
                        } else if( error instanceof ClientError) {
                            errorName="Client Exception";
                        } else if( error instanceof ServerError) {
                            errorName="Server Exception";
                        } else if( error instanceof AuthFailureError) {
                            errorName="Authentication Failure Exception";
                        } else if( error instanceof ParseError) {
                            errorName="Parse Exception";
                        } else if( error instanceof NoConnectionError) {
                            errorName="No Connection Exception";
                        } else if( error instanceof TimeoutError) {
                            errorName="Timeout Exception";
                        }

                        scb.onServiceResponse(errorName);
                    }
                }){
            @Override
            protected Map<String,String> getParams(){

                System.out.println("Params- "+ new JSONObject(sendParams));
                return sendParams;
            }

        };

        //Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions. Volley does retry for you if you have specified the policy.
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        RestroApplication.getInstance().addToRequestQueue(stringRequest);
    }
}
