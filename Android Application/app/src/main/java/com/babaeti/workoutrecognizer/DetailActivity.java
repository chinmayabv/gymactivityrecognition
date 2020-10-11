package com.babaeti.workoutrecognizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.babaeti.workoutrecognizer.ui.report.DetailFragment;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int sessionIndex = getIntent().getIntExtra("index", -1);
        if (sessionIndex == -1) {
            finish();
        }

        FragmentTransaction ts = getSupportFragmentManager().beginTransaction();
        ts.add(R.id.container, DetailFragment.newInstance(sessionIndex));
        ts.commit();
    }
}
