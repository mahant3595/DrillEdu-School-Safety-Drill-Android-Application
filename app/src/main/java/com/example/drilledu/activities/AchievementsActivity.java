package com.example.drilledu.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drilledu.R;
import com.example.drilledu.adapters.AchievementAdapter;
import com.example.drilledu.database.DrillEduDbHelper;
import com.example.drilledu.models.AchievementItem;

import java.util.List;

public class AchievementsActivity extends AppCompatActivity {

    private RecyclerView rvAchievements;
    private TextView tvHeader;
    private DrillEduDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        dbHelper = DrillEduDbHelper.getInstance(this);

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        rvAchievements = findViewById(R.id.rv_achievements);
        tvHeader = findViewById(R.id.tv_badge_count_header);

        rvAchievements.setLayoutManager(new LinearLayoutManager(this));

        loadAchievements();
    }

    private void loadAchievements() {
        List<AchievementItem> list = dbHelper.getAllAchievements();
        int unlockedCount = 0;
        for (AchievementItem a : list) {
            if (a.isUnlocked()) unlockedCount++;
        }

        tvHeader.setText("Unlocked " + unlockedCount + " of " + list.size() + " Badges");
        rvAchievements.setAdapter(new AchievementAdapter(this, list));
    }
}