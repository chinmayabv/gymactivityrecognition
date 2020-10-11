package com.babaeti.workoutrecognizer.ui.report;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.babaeti.workoutrecognizer.DetailActivity;
import com.babaeti.workoutrecognizer.R;
import com.babaeti.workoutrecognizer.SessionManager;
import com.babaeti.workoutrecognizer.adapter.SessionAdapter;

public class ReportFragment extends Fragment implements View.OnClickListener{

    private ReportViewModel reportViewModel;
    private CardView lastSessionCard, dailyCard, monthlyCard;
    private CardView plankCard, pushupCard, lungeCard, crunchCard, squatCard;
    private RecyclerView sessionsRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reportViewModel =
                ViewModelProviders.of(this).get(ReportViewModel.class);
        View root = inflater.inflate(R.layout.fragment_report, container, false);
        addViews(root);
//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        reportViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    private void addViews(View root) {
        sessionsRecyclerView = root.findViewById(R.id.recyclerview_sessions);
        setupSessionsRecyclerView();
//        lastSessionCard = root.findViewById(R.id.card_last_session);
//        dailyCard = root.findViewById(R.id.card_report_daily);
//        monthlyCard = root.findViewById(R.id.card_report_monthly);
//        plankCard = root.findViewById(R.id.card_report_plank);
//        pushupCard = root.findViewById(R.id.card_report_push_up);
//        lungeCard = root.findViewById(R.id.card_report_lunge);
//        crunchCard = root.findViewById(R.id.card_report_crunch);
//        squatCard = root.findViewById(R.id.card_report_squat);
//
//        lastSessionCard.setOnClickListener(this);
//        dailyCard.setOnClickListener(this);
//        monthlyCard.setOnClickListener(this);
//        plankCard.setOnClickListener(this);
//        pushupCard.setOnClickListener(this);
//        lungeCard.setOnClickListener(this);
//        crunchCard.setOnClickListener(this);
//        squatCard.setOnClickListener(this);
    }

    private void setupSessionsRecyclerView() {
        sessionsRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        sessionsRecyclerView.setLayoutManager(layoutManager);
        SessionAdapter sessionAdapter = new SessionAdapter(SessionManager.getSessionList());
        sessionsRecyclerView.setAdapter(sessionAdapter);
    }

    @Override
    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.card_last_session:
//                Intent intent = new Intent(getActivity(), DetailActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.card_report_daily:
//                break;
//            case R.id.card_report_monthly:
//                break;
//            case R.id.card_report_plank:
//                break;
//            case R.id.card_report_push_up:
//                break;
//            case R.id.card_report_lunge:
//                break;
//            case R.id.card_report_crunch:
//                break;
//            case R.id.card_report_squat:
//                break;
//        }
    }
}