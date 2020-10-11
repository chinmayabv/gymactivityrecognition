package com.babaeti.workoutrecognizer.ui.home;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.babaeti.workoutrecognizer.R;
import com.babaeti.workoutrecognizer.SessionManager;
import com.babaeti.workoutrecognizer.httpclient.HttpClient;
import com.babaeti.workoutrecognizer.httpclient.ResponseHandler;
import com.babaeti.workoutrecognizer.model.ExerciseType;
import com.babaeti.workoutrecognizer.model.RepDetail;
import com.babaeti.workoutrecognizer.model.Session;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private HomeViewModel homeViewModel;
    private ExtendedFloatingActionButton startFab;
    private boolean isRecording = false;
    private ColorStateList startFabDefaultBackgroundTintList;
    private Chronometer chronometer;
    private TextView processingReportTextView;
    private TimerTask timerTask;
    private Timer timer;
    private Handler handler = new Handler();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        startFab = root.findViewById(R.id.fab_start);
        startFabDefaultBackgroundTintList = startFab.getBackgroundTintList();
        startFab.setOnClickListener(this);
        chronometer = root.findViewById(R.id.chronometer);
        processingReportTextView = root.findViewById(R.id.textview_processing_report);
        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_start:
                if (!isRecording)
//                    onNewSessionStarted();
                    startNewSession();
                else
//                    onSessionStopped();
                    stopSession();
                break;
        }
    }

    private void startNewSession() {
        HttpClient.getInstance(getContext()).startCamera(new ResponseHandler() {
            @Override
            public void onResponse(String response) {
                onNewSessionStarted();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), "error in starting camera", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void stopSession() {
        HttpClient.getInstance(getContext()).stopCamera(new ResponseHandler() {
            @Override
            public void onResponse(String response) {
                onSessionStopped();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), "error in stopping camera", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onNewSessionStarted() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        isRecording = true;
        processingReportTextView.setVisibility(View.GONE);
        startFab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
        startFab.setIconResource(R.drawable.ic_stop_black_24dp);
        startFab.setText("STOP");
    }

    private void onSessionStopped() {
        chronometer.stop();
        isRecording = false;
        processingReportTextView.setVisibility(View.VISIBLE);
        startFab.setBackgroundTintList(startFabDefaultBackgroundTintList);
        startFab.setIconResource(R.drawable.ic_fitness_center_black_24dp);
        startFab.setText("START");
        initializeTimer();
    }

    private void initializeTimer() {
        timerTask = new TimerTask() {
            public void run() {
                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        requestForResult();
                    }
                });
            }
        };

        timer = new Timer();
        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 5000, 10000); //
    }

    public void stopTimerTask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void requestForResult() {
        HttpClient.getInstance(getContext()).getResult(new ResponseHandler() {
            @Override
            public void onResponse(String response) {
                onResultReceived(response);
            }

            @Override
            public void onError(String error) {
            }
        });
    }

    private void onResultReceived(String response) {
        ArrayList<Session> newSessions = parseJson(response);
        if (newSessions.size() > 0) {
//            Toast.makeText(getContext(), "workout report is ready", Toast.LENGTH_SHORT).show();
            SessionManager.addNewSessions(newSessions);
        }
    }

    private ArrayList<Session> parseJson(String response) {
        ArrayList<Session> sessionList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("sessionList");
            for (int i = 0; i < jsonArray.length(); i++) {
                Session session = new Session();
                JSONObject jo = jsonArray.getJSONObject(i);
                String startTimestampStr = jo.getString("startTimestamp");
                Date startDate = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS").parse(startTimestampStr);
                session.setStartDateTime(startDate);
                String stopTimestampStr = jo.getString("stopTimestamp");
                Date stopDate = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS").parse(stopTimestampStr);
                session.setEndDateTime(stopDate);
                // todo: parse timestamps
                JSONArray repArray = jo.getJSONArray("repList");
                ArrayList<RepDetail> repList = new ArrayList<>();
                for (int j = 0 ; j < repArray.length() ; j++) {
                    RepDetail repDetail = new RepDetail();
                    JSONObject repObj = repArray.getJSONObject(j);

                    JSONArray acc_v = repObj.getJSONArray("acc_v");
                    ArrayList<Double> accList = new ArrayList<>();
                    for (int k = 0 ; k < acc_v.length() ; k++) {
                        accList.add(acc_v.getDouble(k));
                    }
                    repDetail.setJointAccuracyList(accList);

                    JSONArray acc_t = repObj.getJSONArray("acc_t");
                    ArrayList<String> accDescList = new ArrayList<>();
                    for (int k = 0 ; k < acc_t.length() ; k++) {
                        accDescList.add(acc_t.getString(k));
                    }
                    repDetail.setJointAccuracyDescriptionList(accDescList);

                    int a1 = repObj.getInt("a1");
                    int a2 = repObj.getInt("a2");
                    int a3 = repObj.getInt("a3");
                    int a4 = repObj.getInt("a4");
                    int a5 = repObj.getInt("a5");
                    if (a1 == 1)
                        repDetail.setType(ExerciseType.PUSHUP);
                    else if (a2 == 1)
                        repDetail.setType(ExerciseType.SQUAT);
                    else if (a3 == 1)
                        repDetail.setType(ExerciseType.PLANK);
                    else if (a4 == 1)
                        repDetail.setType(ExerciseType.LUNGE);
                    else if (a5 == 1)
                        repDetail.setType(ExerciseType.SITUP);
                    else
                        repDetail.setType(ExerciseType.UNKNOWN);

                    repList.add(repDetail);
                }
                session.setRepDetails(repList);
                sessionList.add(session);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sessionList;
    }
}