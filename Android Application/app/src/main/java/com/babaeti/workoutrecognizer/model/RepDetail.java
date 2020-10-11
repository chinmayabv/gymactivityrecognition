package com.babaeti.workoutrecognizer.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RepDetail {
    private int id;
//    private int exerciseSetId;
    private int sessionId;
    private int type;
    private int repNumber;

    private ArrayList<Double> jointAccuracyList;
    private ArrayList<String> jointAccuracyDescriptionList;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public int getExerciseSetId() {
//        return exerciseSetId;
//    }
//
//    public void setExerciseSetId(int exerciseSetId) {
//        this.exerciseSetId = exerciseSetId;
//    }

    public int getRepNumber() {
        return repNumber;
    }

    public void setRepNumber(int repNumber) {
        this.repNumber = repNumber;
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

    public ArrayList<Double> getJointAccuracyList() {
        return jointAccuracyList;
    }

    public void setJointAccuracyList(ArrayList<Double> jointAccuracyList) {
        this.jointAccuracyList = jointAccuracyList;
    }

    public ArrayList<String> getJointAccuracyDescriptionList() {
        return jointAccuracyDescriptionList;
    }

    public void setJointAccuracyDescriptionList(ArrayList<String> jointAccuracyDescriptionList) {
        this.jointAccuracyDescriptionList = jointAccuracyDescriptionList;
    }
}
