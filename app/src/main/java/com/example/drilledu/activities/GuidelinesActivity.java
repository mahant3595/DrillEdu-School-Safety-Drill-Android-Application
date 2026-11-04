package com.example.drilledu.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drilledu.R;
import com.example.drilledu.adapters.SafetyGuideAdapter;
import com.example.drilledu.database.DrillEduDbHelper;
import com.example.drilledu.models.SafetyGuide;

import java.util.List;

public class GuidelinesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidelines);

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        RecyclerView rv = findViewById(R.id.rv_guidelines);
        rv.setLayoutManager(new LinearLayoutManager(this));

        List<SafetyGuide> guides = DrillEduDbHelper.getInstance(this).getAllGuides();
        rv.setAdapter(new SafetyGuideAdapter(this, guides));
    }
}