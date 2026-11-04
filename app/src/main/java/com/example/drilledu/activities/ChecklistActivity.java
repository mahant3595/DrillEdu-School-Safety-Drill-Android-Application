package com.example.drilledu.activities;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drilledu.R;
import com.example.drilledu.adapters.ChecklistAdapter;
import com.example.drilledu.database.DrillEduDbHelper;
import com.example.drilledu.models.ChecklistItem;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class ChecklistActivity extends AppCompatActivity implements ChecklistAdapter.OnChecklistChangeListener {

    private RecyclerView rvChecklist;
    private ProgressBar pbChecklist;
    private TextView tvPercent;
    private DrillEduDbHelper dbHelper;
    private ChecklistAdapter adapter;
    private String currentCategory = "ALL";
    private List<ChecklistItem> currentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        dbHelper = DrillEduDbHelper.getInstance(this);

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
        findViewById(R.id.btn_reset_checklist).setOnClickListener(v -> showResetConfirmationDialog());

        rvChecklist = findViewById(R.id.rv_checklist);
        pbChecklist = findViewById(R.id.pb_checklist);
        tvPercent = findViewById(R.id.tv_checklist_percent);

        rvChecklist.setLayoutManager(new LinearLayoutManager(this));

        ChipGroup chipGroup = findViewById(R.id.chip_group_checklist);
        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.contains(R.id.chip_before)) currentCategory = "BEFORE";
            else if (checkedIds.contains(R.id.chip_during)) currentCategory = "DURING";
            else if (checkedIds.contains(R.id.chip_after)) currentCategory = "AFTER";
            else currentCategory = "ALL";
            loadChecklist();
        });

        loadChecklist();
    }

    private void loadChecklist() {
        currentList = dbHelper.getChecklistByCategory(currentCategory);
        adapter = new ChecklistAdapter(this, currentList, this);
        rvChecklist.setAdapter(adapter);
        updateProgress();
    }

    private void updateProgress() {
        List<ChecklistItem> allItems = dbHelper.getChecklistByCategory("ALL");
        int completed = 0;
        for (ChecklistItem item : allItems) {
            if (item.isCompleted()) completed++;
        }

        int percent = allItems.size() > 0 ? (completed * 100 / allItems.size()) : 0;
        pbChecklist.setProgress(percent);
        tvPercent.setText(percent + "%");
    }

    @Override
    public void onItemCheckChanged() {
        updateProgress();
    }

    private void showResetConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Reset Preparedness Checklist?")
                .setMessage("Are you sure you want to uncheck all checklist items? Your progress will be cleared.")
                .setPositiveButton("Reset All", (dialog, which) -> {
                    dbHelper.resetAllChecklists();
                    loadChecklist();
                    Toast.makeText(ChecklistActivity.this, "Checklist reset successfully.", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}