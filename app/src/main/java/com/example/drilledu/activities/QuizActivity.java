package com.example.drilledu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drilledu.R;
import com.example.drilledu.database.DrillEduDbHelper;
import com.example.drilledu.models.QuizQuestion;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private String category = "ALL";
    private List<QuizQuestion> questions;
    private int currentIndex = 0;
    private int score = 0;
    private boolean isAnswered = false;

    private TextView tvCategoryTitle;
    private TextView tvCounter;
    private TextView tvScore;
    private TextView tvQuestionText;
    private ProgressBar pbProgress;

    private RadioGroup rgOptions;
    private RadioButton rbOptionA;
    private RadioButton rbOptionB;
    private RadioButton rbOptionC;
    private RadioButton rbOptionD;

    private View cardExplanation;
    private TextView tvExplanationHeader;
    private TextView tvExplanationText;
    private MaterialButton btnAction;

    private DrillEduDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        dbHelper = DrillEduDbHelper.getInstance(this);

        if (getIntent().hasExtra("CATEGORY")) {
            category = getIntent().getStringExtra("CATEGORY");
        }

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        tvCategoryTitle = findViewById(R.id.tv_quiz_category_title);
        tvCounter = findViewById(R.id.tv_question_counter);
        tvScore = findViewById(R.id.tv_current_score);
        tvQuestionText = findViewById(R.id.tv_question_text);
        pbProgress = findViewById(R.id.pb_quiz_progress);

        rgOptions = findViewById(R.id.rg_options);
        rbOptionA = findViewById(R.id.rb_option_a);
        rbOptionB = findViewById(R.id.rb_option_b);
        rbOptionC = findViewById(R.id.rb_option_c);
        rbOptionD = findViewById(R.id.rb_option_d);

        cardExplanation = findViewById(R.id.card_explanation);
        tvExplanationHeader = findViewById(R.id.tv_feedback_header);
        tvExplanationText = findViewById(R.id.tv_explanation_text);
        btnAction = findViewById(R.id.btn_action_quiz);

        tvCategoryTitle.setText((category != null && !category.equals("ALL")) ? category + " Safety Quiz" : "Safety Practice Quiz");

        questions = dbHelper.getQuizQuestions(category);
        if (questions == null || questions.isEmpty()) {
            questions = dbHelper.getQuizQuestions("ALL");
        }

        if (questions == null || questions.isEmpty()) {
            Toast.makeText(this, "No quiz questions available.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        pbProgress.setMax(questions.size());
        displayQuestion();

        btnAction.setOnClickListener(v -> {
            if (!isAnswered) {
                checkAnswer();
            } else {
                nextQuestion();
            }
        });
    }

    private void displayQuestion() {
        if (currentIndex >= questions.size()) return;

        QuizQuestion q = questions.get(currentIndex);
        tvCounter.setText("Question " + (currentIndex + 1) + " of " + questions.size());
        tvScore.setText("Score: " + score);
        pbProgress.setProgress(currentIndex + 1);

        tvQuestionText.setText(q.getQuestionText());
        rbOptionA.setText("A. " + q.getOptionA());
        rbOptionB.setText("B. " + q.getOptionB());
        rbOptionC.setText("C. " + q.getOptionC());
        rbOptionD.setText("D. " + q.getOptionD());

        rgOptions.clearCheck();
        for (int i = 0; i < rgOptions.getChildCount(); i++) {
            rgOptions.getChildAt(i).setEnabled(true);
        }

        cardExplanation.setVisibility(View.GONE);
        btnAction.setText("Submit Answer");
        isAnswered = false;
    }

    private void checkAnswer() {
        int selectedId = rgOptions.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Please select an answer first!", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedIndex = -1;
        if (selectedId == R.id.rb_option_a) selectedIndex = 0;
        else if (selectedId == R.id.rb_option_b) selectedIndex = 1;
        else if (selectedId == R.id.rb_option_c) selectedIndex = 2;
        else if (selectedId == R.id.rb_option_d) selectedIndex = 3;

        QuizQuestion q = questions.get(currentIndex);
        isAnswered = true;

        for (int i = 0; i < rgOptions.getChildCount(); i++) {
            rgOptions.getChildAt(i).setEnabled(false);
        }

        if (selectedIndex == q.getCorrectOptionIndex()) {
            score++;
            tvExplanationHeader.setText("Correct Answer! ✓");
            tvExplanationHeader.setTextColor(getColor(R.color.success_green));
        } else {
            tvExplanationHeader.setText("Incorrect Answer ✗");
            tvExplanationHeader.setTextColor(getColor(R.color.emergency_red));
        }

        tvExplanationText.setText(q.getExplanation());
        cardExplanation.setVisibility(View.VISIBLE);

        if (currentIndex == questions.size() - 1) {
            btnAction.setText("View Results");
        } else {
            btnAction.setText("Next Question →");
        }
    }

    private void nextQuestion() {
        currentIndex++;
        if (currentIndex < questions.size()) {
            displayQuestion();
        } else {
            dbHelper.saveQuizResult(category, score, questions.size());
            Intent intent = new Intent(QuizActivity.this, QuizResultActivity.class);
            intent.putExtra("SCORE", score);
            intent.putExtra("TOTAL", questions.size());
            intent.putExtra("CATEGORY", category);
            startActivity(intent);
            finish();
        }
    }
}