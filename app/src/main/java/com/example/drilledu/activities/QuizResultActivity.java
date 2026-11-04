package com.example.drilledu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drilledu.MainActivity;
import com.example.drilledu.R;

public class QuizResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        int score = getIntent().getIntExtra("SCORE", 0);
        int total = getIntent().getIntExtra("TOTAL", 1);
        String category = getIntent().getStringExtra("CATEGORY");

        int percent = total > 0 ? (int) Math.round(((double) score / total) * 100) : 0;

        TextView tvScore = findViewById(R.id.tv_result_score);
        TextView tvFeedback = findViewById(R.id.tv_result_feedback);

        tvScore.setText("Score: " + score + " / " + total + " (" + percent + "%)");

        if (percent >= 80) {
            tvFeedback.setText("Outstanding! You have mastered the school safety drill guidelines.");
        } else if (percent >= 50) {
            tvFeedback.setText("Good effort! Review the safety guidelines to score 100%.");
        } else {
            tvFeedback.setText("Keep learning! Revisit the emergency drill instructions and try again.");
        }

        findViewById(R.id.btn_retry_quiz).setOnClickListener(v -> {
            Intent intent = new Intent(QuizResultActivity.this, QuizActivity.class);
            intent.putExtra("CATEGORY", category);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.btn_done_quiz).setOnClickListener(v -> {
            Intent intent = new Intent(QuizResultActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }
}