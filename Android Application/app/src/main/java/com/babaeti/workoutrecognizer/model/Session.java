package com.babaeti.workoutrecognizer.model;

import java.util.ArrayList;
import java.util.Date;

public class Session {
    private int id;
    private Date startDateTime;
    private Date endDateTime;

//    private ArrayList<ExerciseSet> exerciseSets;
    private ArrayList<RepDetail> repDetails;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public ArrayList<RepDetail> getRepDetails() {
        return repDetails;
    }

    public void setRepDetails(ArrayList<RepDetail> repDetails) {
        this.repDetails = repDetails;
    }
}
