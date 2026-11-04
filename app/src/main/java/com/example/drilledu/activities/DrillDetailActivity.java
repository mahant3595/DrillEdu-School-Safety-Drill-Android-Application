package com.example.drilledu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.drilledu.R;
import com.example.drilledu.database.DrillEduDbHelper;
import com.example.drilledu.models.DrillItem;
import com.google.android.material.button.MaterialButton;

public class DrillDetailActivity extends AppCompatActivity {

    private DrillItem drill;
    private MaterialButton btnComplete;
    private DrillEduDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drill_detail);

        dbHelper = DrillEduDbHelper.getInstance(this);

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        drill = (DrillItem) getIntent().getSerializableExtra("DRILL_ITEM");
        if (drill != null) {
            bindDrillData();
        }

        btnComplete = findViewById(R.id.btn_complete_drill);
        updateButtonState();

        btnComplete.setOnClickListener(v -> {
            if (drill != null) {
                boolean newStatus = !drill.isCompleted();
                drill.setCompleted(newStatus);
                dbHelper.updateDrillCompletion(drill.getId(), newStatus);
                updateButtonState();

                String msg = newStatus ? "Drill marked as COMPLETED! Great job!" : "Drill marked as PENDING.";
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_drill_quiz).setOnClickListener(v -> {
            Intent intent = new Intent(DrillDetailActivity.this, QuizActivity.class);
            String category = drill != null ? drill.getType() : "ALL";
            intent.putExtra("CATEGORY", category);
            startActivity(intent);
        });
    }

    private void updateButtonState() {
        if (btnComplete == null || drill == null) return;
        if (drill.isCompleted()) {
            btnComplete.setText("Drill Completed ✓ (Tap to Undo)");
            btnComplete.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.card_bg_elevated));
        } else {
            btnComplete.setText("Mark Drill as Completed ✓");
            btnComplete.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.success_green));
        }
    }

    private void bindDrillData() {
        TextView tvToolbar = findViewById(R.id.tv_toolbar_title);
        TextView tvTitle = findViewById(R.id.tv_drill_title);
        TextView tvType = findViewById(R.id.tv_drill_type);
        TextView tvPurpose = findViewById(R.id.tv_drill_purpose);
        TextView tvPrep = findViewById(R.id.tv_drill_prep);
        TextView tvProcedure = findViewById(R.id.tv_drill_procedure);
        TextView tvDosDonts = findViewById(R.id.tv_drill_dos_donts);
        TextView tvReminders = findViewById(R.id.tv_drill_reminders);
        ImageView ivIcon = findViewById(R.id.iv_detail_icon);

        tvToolbar.setText(drill.getTitle());
        tvTitle.setText(drill.getTitle());
        tvType.setText("Type: " + drill.getType() + " Drill");
        tvPurpose.setText(drill.getPurpose());
        tvPrep.setText(drill.getPrepInstructions());
        tvProcedure.setText(drill.getProcedureSteps());
        tvDosDonts.setText(drill.getDosAndDonts());
        tvReminders.setText(drill.getSafetyReminders());

        int iconRes = R.drawable.ic_fire;
        if ("ic_earthquake".equals(drill.getIconName())) iconRes = R.drawable.ic_earthquake;
        else if ("ic_flood".equals(drill.getIconName())) iconRes = R.drawable.ic_flood;
        else if ("ic_weather".equals(drill.getIconName())) iconRes = R.drawable.ic_weather;
        else if ("ic_lockdown".equals(drill.getIconName())) iconRes = R.drawable.ic_lockdown;
        else if ("ic_evacuation".equals(drill.getIconName())) iconRes = R.drawable.ic_evacuation;

        ivIcon.setImageResource(iconRes);
    }
}