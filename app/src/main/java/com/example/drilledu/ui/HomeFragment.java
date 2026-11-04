package com.example.drilledu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.drilledu.MainActivity;
import com.example.drilledu.R;
import com.example.drilledu.activities.ChecklistActivity;
import com.example.drilledu.activities.EmergencyContactsActivity;
import com.example.drilledu.activities.GuidelinesActivity;
import com.example.drilledu.activities.ProgressActivity;
import com.example.drilledu.activities.QuizActivity;
import com.example.drilledu.database.DrillEduDbHelper;
import com.example.drilledu.models.ChecklistItem;
import com.example.drilledu.models.DrillItem;
import com.example.drilledu.utils.PreferencesManager;

import java.util.List;

public class HomeFragment extends Fragment {

    private TextView tvGreeting;
    private TextView tvProgressPercent;
    private TextView tvProgressSummary;
    private ProgressBar pbOverallProgress;
    private DrillEduDbHelper dbHelper;
    private PreferencesManager prefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        dbHelper = DrillEduDbHelper.getInstance(requireContext());
        prefs = new PreferencesManager(requireContext());

        tvGreeting = view.findViewById(R.id.tv_greeting);
        tvProgressPercent = view.findViewById(R.id.tv_progress_percent);
        tvProgressSummary = view.findViewById(R.id.tv_progress_summary);
        pbOverallProgress = view.findViewById(R.id.pb_overall_progress);

        setupClickListeners(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateDashboardData();
    }

    private void setupClickListeners(View view) {
        view.findViewById(R.id.card_guidelines).setOnClickListener(v ->
                startActivity(new Intent(requireContext(), GuidelinesActivity.class)));

        view.findViewById(R.id.card_drills).setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToTab(R.id.nav_drills);
            }
        });

        view.findViewById(R.id.card_checklist).setOnClickListener(v ->
                startActivity(new Intent(requireContext(), ChecklistActivity.class)));

        view.findViewById(R.id.card_practice).setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), QuizActivity.class);
            intent.putExtra("CATEGORY", "ALL");
            startActivity(intent);
        });

        view.findViewById(R.id.btn_contacts).setOnClickListener(v ->
                startActivity(new Intent(requireContext(), EmergencyContactsActivity.class)));

        view.findViewById(R.id.btn_progress_details).setOnClickListener(v ->
                startActivity(new Intent(requireContext(), ProgressActivity.class)));
    }

    private void updateDashboardData() {
        String name = prefs.getStudentName();
        tvGreeting.setText("Hello, " + name + "! 👋");

        List<DrillItem> drills = dbHelper.getAllDrills();
        int completedDrills = 0;
        for (DrillItem d : drills) {
            if (d.isCompleted()) completedDrills++;
        }

        List<ChecklistItem> checklistItems = dbHelper.getChecklistByCategory("ALL");
        int completedChecklist = 0;
        for (ChecklistItem c : checklistItems) {
            if (c.isCompleted()) completedChecklist++;
        }

        int checklistPercent = checklistItems.size() > 0 ? (completedChecklist * 100 / checklistItems.size()) : 0;
        int drillPercent = drills.size() > 0 ? (completedDrills * 100 / drills.size()) : 0;
        int overallScore = (checklistPercent + drillPercent) / 2;

        pbOverallProgress.setProgress(overallScore);
        tvProgressPercent.setText(overallScore + "%");

        tvProgressSummary.setText(completedDrills + " of " + drills.size() + " drills completed • Checklist: " + checklistPercent + "%");
    }
}