package com.example.drilledu.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drilledu.R;
import com.google.android.material.chip.ChipGroup;

public class EvacuationMapActivity extends AppCompatActivity {

    private TextView tvZoneName;
    private TextView tvPrimaryRoute;
    private TextView tvSecondaryRoute;
    private TextView tvAssemblyPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evacuation_map);

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        tvZoneName = findViewById(R.id.tv_map_zone_name);
        tvPrimaryRoute = findViewById(R.id.tv_map_primary_route);
        tvSecondaryRoute = findViewById(R.id.tv_map_secondary_route);
        tvAssemblyPoint = findViewById(R.id.tv_map_assembly_point);

        ChipGroup chipGroup = findViewById(R.id.chip_group_map);
        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.contains(R.id.chip_map_first)) {
                showFirstFloor();
            } else if (checkedIds.contains(R.id.chip_map_auditorium)) {
                showAuditorium();
            } else {
                showGroundFloor();
            }
        });

        showGroundFloor();
    }

    private void showGroundFloor() {
        tvZoneName.setText("Ground Floor Exit Map");
        tvPrimaryRoute.setText("• Primary Route: Exit through South Gate 1 directly onto Main Playground.");
        tvSecondaryRoute.setText("• Secondary Route: If South Gate is obstructed, use East Exit Corridor 2.");
        tvAssemblyPoint.setText("• Safe Assembly Zone: Football Field Centre Circle.");
    }

    private void showFirstFloor() {
        tvZoneName.setText("1st Floor Evacuation Map");
        tvPrimaryRoute.setText("• Primary Route: Proceed down Central Stairwell A to Ground Floor Exit.");
        tvSecondaryRoute.setText("• Secondary Route: Use West Emergency Fire Escape Stairwell B.");
        tvAssemblyPoint.setText("• Safe Assembly Zone: Basketball Court Assembly Area.");
    }

    private void showAuditorium() {
        tvZoneName.setText("Auditorium & Multi-Purpose Hall Map");
        tvPrimaryRoute.setText("• Primary Route: Double Exit Doors on Stage Right leading to Open Courtyard.");
        tvSecondaryRoute.setText("• Secondary Route: Rear Double Doors to North Parking Exit.");
        tvAssemblyPoint.setText("• Safe Assembly Zone: Main Flag Host Ground.");
    }
}