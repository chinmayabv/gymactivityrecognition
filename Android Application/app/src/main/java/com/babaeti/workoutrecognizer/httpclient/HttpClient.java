package com.babaeti.workoutrecognizer.httpclient;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.babaeti.workoutrecognizer.Constants;

public class HttpClient {
    private static HttpClient instance;
    private RequestQueue requestQueue;

    private final static String API_START_CAMERA = "startcamera/";
    private final static String API_STOP_CAMERA = "stopcamera/";
    private final static String API_GET_RESULT = "getresult/";

    private HttpClient(Context context) {
        setRequestQueue(context);
    }

    public static synchronized HttpClient getInstance(Context context) {
        if (instance == null) {
            instance = new HttpClient(context);
        }
        return instance;
    }

    private void setRequestQueue(Context context) {
        // getApplicationContext() is key, it keeps you from leaking the
        // Activity or BroadcastReceiver if someone passes one in.
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    private RequestQueue getRequestQueue() {
        return requestQueue;
    }


    public void startCamera(final ResponseHandler responseHandler) {
        String url = Constants.getServerAddress() + "/" + API_START_CAMERA;
        Log.d("mytag", "start camera sending request for " + url);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("mytag", "Response is: "+ response);
                        responseHandler.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("mytag", "that didn't work " + error.getMessage());
                responseHandler.onError(error.getMessage());
            }
        });

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
    }

    public void stopCamera(final ResponseHandler responseHandler) {
        String url = Constants.getServerAddress() + "/" + API_STOP_CAMERA;
        Log.d("mytag", "stop camera sending request for " + url);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("mytag", "Response is: "+ response);
                        responseHandler.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("mytag", "that didn't work " + error.getMessage());
                responseHandler.onError(error.getMessage());
            }
        });

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
    }

    public void getResult(final ResponseHandler responseHandler) {
        String url = Constants.getServerAddress() + "/" + API_GET_RESULT;
        Log.d("mytag", "get result sending request for " + url);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("mytag", "Response is: "+ response);
                        responseHandler.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("mytag", "that didn't work " + error.getMessage());
                responseHandler.onError(error.getMessage());
            }
        });

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
    }
}
