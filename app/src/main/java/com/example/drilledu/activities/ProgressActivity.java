package com.example.drilledu.activities;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drilledu.R;
import com.example.drilledu.database.DrillEduDbHelper;
import com.example.drilledu.models.ChecklistItem;
import com.example.drilledu.models.DrillItem;
import com.example.drilledu.models.LearningResource;
import com.example.drilledu.models.QuizResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProgressActivity extends AppCompatActivity {

    private DrillEduDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        dbHelper = DrillEduDbHelper.getInstance(this);

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        loadStats();
    }

    private void loadStats() {
        TextView tvDrills = findViewById(R.id.tv_stat_drills);
        TextView tvChecklist = findViewById(R.id.tv_stat_checklist);
        TextView tvQuizAvg = findViewById(R.id.tv_stat_quiz_avg);
        TextView tvBookmarks = findViewById(R.id.tv_stat_bookmarks);

        List<DrillItem> drills = dbHelper.getAllDrills();
        int completedDrills = 0;
        for (DrillItem d : drills) {
            if (d.isCompleted()) completedDrills++;
        }
        tvDrills.setText(completedDrills + " / " + drills.size());

        List<ChecklistItem> checklist = dbHelper.getChecklistByCategory("ALL");
        int completedChecklist = 0;
        for (ChecklistItem c : checklist) {
            if (c.isCompleted()) completedChecklist++;
        }
        int checklistPercent = checklist.size() > 0 ? (completedChecklist * 100 / checklist.size()) : 0;
        tvChecklist.setText(checklistPercent + "%");

        List<QuizResult> results = dbHelper.getQuizResults();
        int totalScorePercent = 0;
        if (!results.isEmpty()) {
            for (QuizResult r : results) {
                totalScorePercent += r.getPercentage();
            }
            tvQuizAvg.setText((totalScorePercent / results.size()) + "%");
        } else {
            tvQuizAvg.setText("N/A");
        }

        List<LearningResource> bookmarked = dbHelper.getAllResources(null, "ALL");
        int savedCount = 0;
        for (LearningResource r : bookmarked) {
            if (r.isBookmarked()) savedCount++;
        }
        tvBookmarks.setText(savedCount + " Saved");

        populateQuizHistory(results);
    }

    private void populateQuizHistory(List<QuizResult> results) {
        LinearLayout container = findViewById(R.id.ll_quiz_history_container);
        container.removeAllViews();

        if (results == null || results.isEmpty()) {
            TextView emptyText = new TextView(this);
            emptyText.setText("No quizzes attempted yet. Take a quiz from any safety guide!");
            emptyText.setTextColor(getColor(R.color.text_secondary));
            emptyText.setTextSize(13);
            emptyText.setPadding(0, 10, 0, 10);
            container.addView(emptyText);
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault());

        for (QuizResult r : results) {
            TextView itemText = new TextView(this);
            String category = r.getCategory() != null ? r.getCategory() : "General";
            String dateStr = sdf.format(new Date(r.getTimestamp()));
            itemText.setText("• " + category + " Quiz: " + r.getScore() + "/" + r.getTotalQuestions() + " (" + r.getPercentage() + "%) — " + dateStr);
            itemText.setTextColor(getColor(R.color.text_primary));
            itemText.setTextSize(13);
            itemText.setPadding(0, 8, 0, 8);
            container.addView(itemText);
        }
    }
}