package com.babaeti.workoutrecognizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.babaeti.workoutrecognizer.model.GenderType;
import com.google.android.material.button.MaterialButton;

public class FirstSetupActivity extends AppCompatActivity {

    private Button buttonFinishSetup;
    private RadioGroup radioGroupGender;
    private boolean isGenderSelected = false;
    int gender = GenderType.MALE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_setup);
        addViews();
        setupViews();
    }

    private void addViews() {
        buttonFinishSetup = findViewById(R.id.button_finish_setup);
        radioGroupGender = findViewById(R.id.radio_group_gender);
    }

    private void setupViews() {
        buttonFinishSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishSetup();
            }
        });

        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                onGenderChanged(i);
            }
        });
    }

    private void onGenderChanged(int id) {
        isGenderSelected = true;
        if (id == R.id.radio_button_male)
            gender = GenderType.MALE;
        else if (id == R.id.radio_button_female)
            gender = GenderType.FEMALE;
    }

    private void finishSetup() {
        if (!checkRequirementsBeforeFinish())
            return;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started), false);
        if(!previouslyStarted) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putInt(getString(R.string.pref_gender), gender);
            edit.putBoolean(getString(R.string.pref_previously_started), Boolean.TRUE);
            edit.commit();
        }
        finish();
    }

    private boolean checkRequirementsBeforeFinish() {
        return isGenderSelected;
    }
}
