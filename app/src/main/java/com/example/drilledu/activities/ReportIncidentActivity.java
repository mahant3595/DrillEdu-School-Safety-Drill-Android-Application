package com.example.drilledu.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drilledu.R;
import com.example.drilledu.database.DrillEduDbHelper;
import com.example.drilledu.models.IncidentReport;
import com.google.android.material.chip.ChipGroup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportIncidentActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etLocation;
    private EditText etDesc;
    private ChipGroup chipGroupUrgency;
    private String selectedUrgency = "MEDIUM";
    private DrillEduDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_incident);

        dbHelper = DrillEduDbHelper.getInstance(this);

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        etTitle = findViewById(R.id.et_incident_title);
        etLocation = findViewById(R.id.et_incident_location);
        etDesc = findViewById(R.id.et_incident_desc);
        chipGroupUrgency = findViewById(R.id.chip_group_urgency);

        chipGroupUrgency.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.contains(R.id.chip_urgency_critical)) selectedUrgency = "CRITICAL";
            else if (checkedIds.contains(R.id.chip_urgency_high)) selectedUrgency = "HIGH";
            else selectedUrgency = "MEDIUM";
        });

        findViewById(R.id.btn_submit_report).setOnClickListener(v -> submitReport());

        loadRecentReports();
    }

    private void submitReport() {
        String title = etTitle.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();

        if (title.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please enter incident title and location.", Toast.LENGTH_SHORT).show();
            return;
        }

        IncidentReport report = new IncidentReport(0, title, "General Hazard", location,
                selectedUrgency, desc, System.currentTimeMillis(), "LOGGED");

        dbHelper.insertIncidentReport(report);

        etTitle.setText("");
        etLocation.setText("");
        etDesc.setText("");

        Toast.makeText(this, "Hazard report submitted! Safety officer notified.", Toast.LENGTH_SHORT).show();
        loadRecentReports();
    }

    private void loadRecentReports() {
        LinearLayout container = findViewById(R.id.ll_reports_container);
        container.removeAllViews();

        List<IncidentReport> list = dbHelper.getAllIncidents();
        if (list == null || list.isEmpty()) {
            TextView empty = new TextView(this);
            empty.setText("No active hazard reports logged yet.");
            empty.setTextColor(getColor(R.color.text_secondary));
            empty.setTextSize(13);
            empty.setPadding(0, 10, 0, 10);
            container.addView(empty);
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault());

        for (IncidentReport r : list) {
            TextView itemText = new TextView(this);
            String dateStr = sdf.format(new Date(r.getTimestamp()));
            itemText.setText("⚠️ [" + r.getUrgency() + "] " + r.getTitle() + "\nLocation: " + r.getLocation() + " • " + dateStr);
            itemText.setTextColor(getColor(R.color.text_primary));
            itemText.setTextSize(13);
            itemText.setPadding(0, 8, 0, 8);
            container.addView(itemText);
        }
    }
}