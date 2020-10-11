package com.babaeti.workoutrecognizer.ui.notifications;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.babaeti.workoutrecognizer.Constants;
import com.babaeti.workoutrecognizer.R;
import com.babaeti.workoutrecognizer.model.GenderType;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private EditText ipEditText;
    private RadioGroup radioGroupGender;
    private SharedPreferences prefs;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        ipEditText = root.findViewById(R.id.edittext_ip);
        ipEditText.setText(Constants.SERVER_IP);
        ipEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Constants.SERVER_IP = editable.toString();
            }
        });

        radioGroupGender = root.findViewById(R.id.radio_group_gender);
        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                onGenderChanged(i);
            }
        });

        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        int gender = prefs.getInt(getString(R.string.pref_gender), 0);
        if(gender != 0) {
            if (gender == GenderType.MALE)
                radioGroupGender.check(R.id.radio_button_male);
            else if (gender == GenderType.FEMALE)
                radioGroupGender.check(R.id.radio_button_female);
        }
        return root;
    }

    private void onGenderChanged(int id) {
        int gender = 0;
        if (id == R.id.radio_button_male)
            gender = GenderType.MALE;
        else if (id == R.id.radio_button_female)
            gender = GenderType.FEMALE;

        if (gender != 0) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putInt(getString(R.string.pref_gender), gender);
            edit.commit();
        }
    }
}