package com.example.drilledu.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drilledu.R;
import com.example.drilledu.database.DrillEduDbHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipGroup;

import java.util.Locale;

public class DrillTimerActivity extends AppCompatActivity {

    private TextView tvCountdown;
    private TextView tvPhasePrompt;
    private ProgressBar pbRing;
    private MaterialButton btnStart;
    private MaterialButton btnFinish;

    private CountDownTimer timer;
    private long totalMillis = 120000; // Default 2 min
    private long millisLeft = 120000;
    private boolean isRunning = false;
    private String currentChallenge = "Fire Evacuation Challenge";

    private DrillEduDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drill_timer);

        dbHelper = DrillEduDbHelper.getInstance(this);

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        tvCountdown = findViewById(R.id.tv_timer_countdown);
        tvPhasePrompt = findViewById(R.id.tv_drill_phase_prompt);
        pbRing = findViewById(R.id.pb_timer_ring);
        btnStart = findViewById(R.id.btn_timer_start);
        btnFinish = findViewById(R.id.btn_timer_finish);

        ChipGroup chipGroup = findViewById(R.id.chip_group_timer);
        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.contains(R.id.chip_timer_earthquake)) {
                totalMillis = 60000;
                currentChallenge = "Earthquake Drop-Cover-Hold Challenge";
            } else {
                totalMillis = 120000;
                currentChallenge = "Fire Evacuation Challenge";
            }
            resetTimer();
        });

        resetTimer();

        btnStart.setOnClickListener(v -> {
            if (!isRunning) {
                startTimer();
            } else {
                pauseTimer();
            }
        });

        btnFinish.setOnClickListener(v -> finishDrillEarly());
    }

    private void resetTimer() {
        if (timer != null) timer.cancel();
        isRunning = false;
        millisLeft = totalMillis;
        pbRing.setMax((int) (totalMillis / 1000));
        pbRing.setProgress((int) (millisLeft / 1000));
        btnStart.setText("START DRILL");
        updateCountdownText();
        tvPhasePrompt.setText("Press START to begin the simulated " + currentChallenge + ".");
    }

    private void startTimer() {
        isRunning = true;
        btnStart.setText("PAUSE");

        timer = new CountDownTimer(millisLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                millisLeft = millisUntilFinished;
                int secondsLeft = (int) (millisLeft / 1000);
                pbRing.setProgress(secondsLeft);
                updateCountdownText();
                updatePhasePrompt(secondsLeft);
            }

            @Override
            public void onFinish() {
                isRunning = false;
                pbRing.setProgress(0);
                tvCountdown.setText("00:00");
                tvPhasePrompt.setText("⏰ Time expired! Evacuation speed requirement missed. Practice again!");
                btnStart.setText("RETRY DRILL");
            }
        }.start();
    }

    private void pauseTimer() {
        if (timer != null) timer.cancel();
        isRunning = false;
        btnStart.setText("RESUME");
    }

    private void updateCountdownText() {
        int seconds = (int) (millisLeft / 1000);
        int mins = seconds / 60;
        int secs = seconds % 60;
        tvCountdown.setText(String.format(Locale.getDefault(), "%02d:%02d", mins, secs));
    }

    private void updatePhasePrompt(int secondsLeft) {
        if (totalMillis == 120000) {
            if (secondsLeft > 90) {
                tvPhasePrompt.setText("🚨 PHASE 1: Alarm sounding! Stop work, line up silently at door.");
            } else if (secondsLeft > 40) {
                tvPhasePrompt.setText("🚶 PHASE 2: Evacuate in single file. Stay low under smoke if present.");
            } else {
                tvPhasePrompt.setText("⛳ PHASE 3: Reach Assembly Point! Class leader report headcount.");
            }
        } else {
            if (secondsLeft > 40) {
                tvPhasePrompt.setText("🧎 PHASE 1: DROP to hands and knees immediately!");
            } else if (secondsLeft > 15) {
                tvPhasePrompt.setText("🛡️ PHASE 2: COVER head/neck under sturdy desk!");
            } else {
                tvPhasePrompt.setText("✊ PHASE 3: HOLD ON to desk leg until clear signal!");
            }
        }
    }

    private void finishDrillEarly() {
        if (millisLeft == totalMillis && !isRunning) {
            Toast.makeText(this, "Start the drill timer first!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (timer != null) timer.cancel();
        isRunning = false;

        long timeTakenSeconds = (totalMillis - millisLeft) / 1000;
        dbHelper.unlockAchievement("ach_drill_timer");

        new AlertDialog.Builder(this)
                .setTitle("Drill Challenge Completed! 🎉")
                .setMessage("Great job! You finished the " + currentChallenge + " in " + timeTakenSeconds + " seconds.\n\nEvacuation Efficiency: 100% (Speedy Evacuator Achievement Unlocked!)")
                .setIcon(R.drawable.ic_check_circle)
                .setPositiveButton("Awesome!", (dialog, which) -> resetTimer())
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) timer.cancel();
    }
}