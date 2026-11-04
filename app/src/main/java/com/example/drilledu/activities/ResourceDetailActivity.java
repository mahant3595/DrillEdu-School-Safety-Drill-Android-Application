package com.example.drilledu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drilledu.R;
import com.example.drilledu.database.DrillEduDbHelper;
import com.example.drilledu.models.LearningResource;

public class ResourceDetailActivity extends AppCompatActivity {

    private LearningResource resource;
    private ImageView btnBookmark;
    private DrillEduDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_detail);

        dbHelper = DrillEduDbHelper.getInstance(this);

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        resource = (LearningResource) getIntent().getSerializableExtra("RESOURCE_ITEM");
        btnBookmark = findViewById(R.id.btn_bookmark);

        if (resource != null) {
            bindResourceData();
        }

        btnBookmark.setOnClickListener(v -> {
            if (resource != null) {
                boolean newState = !resource.isBookmarked();
                resource.setBookmarked(newState);
                dbHelper.toggleBookmark(resource.getId(), newState);
                btnBookmark.setImageResource(newState ? R.drawable.ic_bookmark_filled : R.drawable.ic_bookmark);
                Toast.makeText(this, newState ? "Resource Bookmarked" : "Bookmark Removed", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_practice_quiz).setOnClickListener(v -> {
            Intent intent = new Intent(ResourceDetailActivity.this, QuizActivity.class);
            String category = resource != null ? resource.getCategory() : "ALL";
            intent.putExtra("CATEGORY", category);
            startActivity(intent);
        });
    }

    private void bindResourceData() {
        TextView tvMeta = findViewById(R.id.tv_resource_meta);
        TextView tvTitle = findViewById(R.id.tv_resource_title);
        TextView tvSummary = findViewById(R.id.tv_resource_summary);
        TextView tvFull = findViewById(R.id.tv_resource_full);

        tvMeta.setText(resource.getCategory() + " • " + resource.getReadTime());
        tvTitle.setText(resource.getTitle());
        tvSummary.setText(resource.getSummary());
        tvFull.setText(resource.getFullContent());

        btnBookmark.setImageResource(resource.isBookmarked() ? R.drawable.ic_bookmark_filled : R.drawable.ic_bookmark);
    }
}