package com.example.drilledu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drilledu.R;
import com.example.drilledu.models.SafetyGuide;

public class SafetyDetailActivity extends AppCompatActivity {

    private SafetyGuide guide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_detail);

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        guide = (SafetyGuide) getIntent().getSerializableExtra("SAFETY_GUIDE");
        if (guide != null) {
            bindGuideData();
        }

        findViewById(R.id.btn_take_quiz).setOnClickListener(v -> {
            Intent intent = new Intent(SafetyDetailActivity.this, QuizActivity.class);
            String category = guide != null ? guide.getCategory() : "ALL";
            intent.putExtra("CATEGORY", category);
            startActivity(intent);
        });
    }

    private void bindGuideData() {
        TextView tvToolbarTitle = findViewById(R.id.tv_toolbar_title);
        TextView tvTitle = findViewById(R.id.tv_detail_title);
        TextView tvCategory = findViewById(R.id.tv_detail_category);
        TextView tvIntro = findViewById(R.id.tv_detail_intro);
        TextView tvHazards = findViewById(R.id.tv_detail_hazards);
        TextView tvPrevention = findViewById(R.id.tv_detail_prevention);
        TextView tvBefore = findViewById(R.id.tv_detail_before);
        TextView tvDuring = findViewById(R.id.tv_detail_during);
        TextView tvAfter = findViewById(R.id.tv_detail_after);
        TextView tvWarnings = findViewById(R.id.tv_detail_warnings);
        ImageView ivIcon = findViewById(R.id.iv_detail_icon);

        tvToolbarTitle.setText(guide.getTitle());
        tvTitle.setText(guide.getTitle());
        tvCategory.setText("Category: " + guide.getCategory());
        tvIntro.setText(guide.getIntro());
        tvHazards.setText(guide.getHazards());
        tvPrevention.setText(guide.getPrevention());
        tvBefore.setText(guide.getBeforeSteps());
        tvDuring.setText(guide.getDuringSteps());
        tvAfter.setText(guide.getAfterSteps());
        tvWarnings.setText(guide.getWarnings());

        int iconRes = R.drawable.ic_shield;
        if ("ic_fire".equals(guide.getIconName())) iconRes = R.drawable.ic_fire;
        else if ("ic_earthquake".equals(guide.getIconName())) iconRes = R.drawable.ic_earthquake;
        else if ("ic_flood".equals(guide.getIconName())) iconRes = R.drawable.ic_flood;
        else if ("ic_weather".equals(guide.getIconName())) iconRes = R.drawable.ic_weather;
        else if ("ic_medical".equals(guide.getIconName())) iconRes = R.drawable.ic_medical;

        ivIcon.setImageResource(iconRes);
    }
}