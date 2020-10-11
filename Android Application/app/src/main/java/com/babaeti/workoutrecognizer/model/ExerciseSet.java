package com.babaeti.workoutrecognizer.model;

import java.util.ArrayList;

public class ExerciseSet {
    private int id;
    private int sessionId;
    private int type;
    private boolean isTimeBased;
    private int duration;

    private ArrayList<RepDetail> repDetails;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isTimeBased() {
        return isTimeBased;
    }

    public void setTimeBased(boolean timeBased) {
        isTimeBased = timeBased;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
