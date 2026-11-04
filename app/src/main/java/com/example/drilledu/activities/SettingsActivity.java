package com.example.drilledu.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drilledu.R;
import com.example.drilledu.database.DrillEduDbHelper;
import com.example.drilledu.utils.PreferencesManager;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etClass;
    private SwitchMaterial switchNotifications;
    private PreferencesManager prefs;
    private DrillEduDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        prefs = new PreferencesManager(this);
        dbHelper = DrillEduDbHelper.getInstance(this);

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        etName = findViewById(R.id.et_student_name);
        etClass = findViewById(R.id.et_student_class);
        switchNotifications = findViewById(R.id.switch_notifications);

        etName.setText(prefs.getStudentName());
        etClass.setText(prefs.getStudentClass());
        switchNotifications.setChecked(prefs.areNotificationsEnabled());

        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.setNotificationsEnabled(isChecked);
            Toast.makeText(this, isChecked ? "Notifications enabled" : "Notifications disabled", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btn_save_profile).setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String className = etClass.getText().toString().trim();
            if (!name.isEmpty()) {
                prefs.setStudentName(name);
                prefs.setStudentClass(className);
                Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please enter a valid student name.", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_clear_bookmarks).setOnClickListener(v -> {
            dbHelper.clearAllBookmarks();
            Toast.makeText(this, "All bookmarks cleared.", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btn_reset_all_data).setOnClickListener(v -> showResetAllDataConfirmation());
    }

    private void showResetAllDataConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Reset All Learning & Drill Data?")
                .setMessage("Are you sure you want to reset all drill completion records, checklist progress, quiz scores, and saved bookmarks? This action cannot be undone.")
                .setIcon(R.drawable.ic_warning)
                .setPositiveButton("Reset Everything", (dialog, which) -> {
                    dbHelper.resetAllData();
                    Toast.makeText(SettingsActivity.this, "All learning data has been reset.", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}