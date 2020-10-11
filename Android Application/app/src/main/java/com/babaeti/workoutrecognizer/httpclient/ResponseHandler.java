package com.babaeti.workoutrecognizer.httpclient;

public interface ResponseHandler {
    void onResponse(String response);
    void onError(String error);
}
