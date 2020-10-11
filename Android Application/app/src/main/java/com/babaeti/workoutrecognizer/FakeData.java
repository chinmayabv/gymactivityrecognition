package com.babaeti.workoutrecognizer;

import com.babaeti.workoutrecognizer.model.ExerciseSet;
import com.babaeti.workoutrecognizer.model.ExerciseType;
import com.babaeti.workoutrecognizer.model.RepDetail;
import com.babaeti.workoutrecognizer.model.Session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public abstract class FakeData {
    public static ArrayList<Session> sessions;
    public static ArrayList<ExerciseSet> exerciseSets;
    public static ArrayList<RepDetail> repDetails;
    private static Random random = new Random();

    public static void init() {
        initSessions();
        initExerciseSets();
        initRepDetails();
    }

    private static void initSessions() {
        sessions = new ArrayList<>();
        for (int i = 0 ; i < 10 ; i++) {
            Session newSession = new Session();
            newSession.setId(i);
            int hour = random.nextInt(20);
            int minute = random.nextInt(60);
            int second = random.nextInt(60);
            newSession.setStartDateTime(new GregorianCalendar(2019, 10, 10 + i, hour, minute, second).getTime());
            minute = random.nextInt(60);
            second = random.nextInt(60);
            newSession.setEndDateTime(new GregorianCalendar(2019, 10, 10 + i, hour + 1, minute, second).getTime());
            sessions.add(newSession);
        }
    }

    private static void initExerciseSets() {
        exerciseSets = new ArrayList<>();
        int id = 0;
        for (int i = 0 ; i < sessions.size() ; i++) {
            // number of exercise types
            for (int j = 0 ; j < 5 ; j++) {
                // 3 set for each type
                for (int k = 0 ; k < 3 ; k++) {
                    ExerciseSet newExerciseSet = new ExerciseSet();
                    newExerciseSet.setId(id);
                    newExerciseSet.setSessionId(i);
                    newExerciseSet.setType(j+1);
                    if (j+1 == ExerciseType.PLANK)
                        newExerciseSet.setTimeBased(true);
                    else
                        newExerciseSet.setTimeBased(false);
                    // random duration between 10 to 40 seconds
                    newExerciseSet.setDuration(random.nextInt(30) + 10);
                    exerciseSets.add(newExerciseSet);
                    id++;
                }
            }
        }
    }

    private static void initRepDetails() {
//        repDetails = new ArrayList<>();
//        for (ExerciseSet exerciseSet : exerciseSets) {
//            if (exerciseSet.getType() != ExerciseType.PLANK) {
//                for (int j = 0 ; j < 10 ; j++) {
//                    RepDetail newRepDetail = new RepDetail();
//                    newRepDetail.setId(repDetails.size());
//                    newRepDetail.setExerciseSetId(exerciseSet.getId());
//                    newRepDetail.setRepNumber(j+1);
//                    repDetails.add(newRepDetail);
//                }
//            } else {
//                RepDetail newRepDetail = new RepDetail();
//                newRepDetail.setId(repDetails.size());
//                newRepDetail.setExerciseSetId(exerciseSet.getId());
//                newRepDetail.setRepNumber(1);
//                repDetails.add(newRepDetail);
//            }
//        }
    }
}
