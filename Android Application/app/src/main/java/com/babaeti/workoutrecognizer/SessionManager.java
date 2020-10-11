package com.babaeti.workoutrecognizer;

import android.util.Log;

import com.babaeti.workoutrecognizer.model.RepDetail;
import com.babaeti.workoutrecognizer.model.Session;

import java.util.ArrayList;

public class SessionManager {
    private static ArrayList<Session> sessionList = new ArrayList<>();

    public static void addNewSessions(ArrayList<Session> newSessions) {
        sessionList.addAll(0, newSessions);
    }

    public static ArrayList<Session> getSessionList() {
        return sessionList;
    }

    public static ArrayList<Float> getPieValues(Session session) {
        ArrayList<Float> result = new ArrayList<>();
        int totalReps = session.getRepDetails().size();
        int type1Count = 0, type2Count = 0, type3Count = 0, type4Count = 0, type5Count = 0;
        for (RepDetail repDetail : session.getRepDetails()) {
            switch (repDetail.getType()) {
                case 1:
                    type1Count++;
                    break;
                case 2:
                    type2Count++;
                    break;
                case 3:
                    type3Count++;
                    break;
                case 4:
                    type4Count++;
                    break;
                case 5:
                    type5Count++;
                    break;
                default:
                    break;
            }
        }

        result.add(((float)type1Count/totalReps)*100);
        result.add(((float)type2Count/totalReps)*100);
        result.add(((float)type3Count/totalReps)*100);
        result.add(((float)type4Count/totalReps)*100);
        result.add(((float)type5Count/totalReps)*100);
        return result;
    }
}
