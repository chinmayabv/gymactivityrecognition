package com.babaeti.workoutrecognizer.ui.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.babaeti.workoutrecognizer.R;
import com.babaeti.workoutrecognizer.SessionManager;
import com.babaeti.workoutrecognizer.model.RepDetail;
import com.babaeti.workoutrecognizer.model.Session;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class DetailFragment extends Fragment {

    private PieChart pieChart;
    private PieDataSet pieDataSet;
    private ArrayList<PieEntry> entries;
    private ArrayList<Integer> colors;
    private int sessionIndex;
    private TextView accuracyTextView;

    public static DetailFragment newInstance(int sessionIndex) {
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.sessionIndex = sessionIndex;
        return detailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        pieChart = view.findViewById(R.id.chart);
        accuracyTextView = view.findViewById(R.id.textview_accuracy);
        Session session = SessionManager.getSessionList().get(sessionIndex);
        StringBuilder builder = new StringBuilder();
        for (RepDetail repDetail : session.getRepDetails()) {
            switch (repDetail.getType()){
                case 1:
                    builder.append("PushUp");
                    break;
                case 2:
                    builder.append("Squat");
                    break;
                case 3:
                    builder.append("Plank");
                    break;
                case 4:
                    builder.append("Lunge");
                    break;
                case 5:
                    builder.append("SitUp");
                    break;
            }
            builder.append('\n');
            for (String s : repDetail.getJointAccuracyDescriptionList()) {
                builder.append("\t\t");
                builder.append(s);
                builder.append('\n');
            }
            builder.append("\n\n");
        }

        accuracyTextView.setText(builder.toString());

        entries = new ArrayList<>();
        updateChart(session);
        pieDataSet = new PieDataSet(entries, "label");

        colors = new ArrayList<>();
        colors.add(getContext().getColor(R.color.red));
        colors.add(getContext().getColor(R.color.green));
        colors.add(getContext().getColor(R.color.blue));
        colors.add(getContext().getColor(R.color.yellow));
        colors.add(getContext().getColor(R.color.purple));


        pieDataSet.setColors(colors);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.invalidate();
    }

    public void updateChart(Session session) {
        entries.clear();
        ArrayList<Float> values = SessionManager.getPieValues(session);
        if (values.get(0) != 0)
            entries.add(new PieEntry(values.get(0), "PushUp"));
        if (values.get(1) != 0)
            entries.add(new PieEntry(values.get(1), "Squat"));
        if (values.get(2) != 0)
            entries.add(new PieEntry(values.get(2), "Plank"));
        if (values.get(3) != 0)
            entries.add(new PieEntry(values.get(3), "Lunge"));
        if (values.get(4) != 0)
            entries.add(new PieEntry(values.get(4), "SitUp"));
    }
}
