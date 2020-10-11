package com.babaeti.workoutrecognizer;

public abstract class Constants {
    public static String SERVER_IP = "192.168.43.75";
    public static String SERVER_PORT = "6986";

    public static String getServerAddress() {
        return "http://" + SERVER_IP + ":" + SERVER_PORT;
    }
}
