package com.babaeti.workoutrecognizer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.babaeti.workoutrecognizer.DetailActivity;
import com.babaeti.workoutrecognizer.R;
import com.babaeti.workoutrecognizer.SessionManager;
import com.babaeti.workoutrecognizer.model.Session;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.SessionViewHolder> {
    private ArrayList<Session> mSessionList;

    public SessionAdapter(ArrayList<Session> sessionList) {
        mSessionList = sessionList;
    }

    @NonNull
    @Override
    public SessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_session_item, parent, false);
        SessionViewHolder vh = new SessionViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull SessionViewHolder holder, int position) {
        holder.setSession(mSessionList.get(position));
    }

    @Override
    public int getItemCount() {
        return mSessionList.size();
    }

    public static class SessionViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private TextView timestampTextView;
        private PieChart pieChart;
        private PieDataSet pieDataSet;
        private ArrayList<PieEntry> entries;
        private ArrayList<Integer> colors;

        public SessionViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetailActivity.class);
                    intent.putExtra("index", getAdapterPosition());
                    view.getContext().startActivity(intent);
                }
            });
            context = view.getContext();
            timestampTextView = view.findViewById(R.id.textview_timestamp);
            pieChart = view.findViewById(R.id.chart);
            pieChart.setTouchEnabled(false);
            entries = new ArrayList<>();
            pieDataSet = new PieDataSet(entries, "label");
            colors = new ArrayList<>();
            colors.add(context.getColor(R.color.red));
            colors.add(context.getColor(R.color.green));
            colors.add(context.getColor(R.color.blue));
            colors.add(context.getColor(R.color.yellow));
            colors.add(context.getColor(R.color.purple));
            pieDataSet.setColors(colors);
            PieData pieData = new PieData(pieDataSet);
            pieChart.setData(pieData);
            pieChart.getDescription().setEnabled(false);
            pieChart.getLegend().setEnabled(false);
        }

        public void setSession(Session session) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate = dateFormat.format(session.getStartDateTime());
            timestampTextView.setText(strDate);
            updateChart(session);
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
            pieChart.invalidate();
        }
    }
}
