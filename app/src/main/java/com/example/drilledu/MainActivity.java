package com.example.drilledu;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.drilledu.ui.DrillsFragment;
import com.example.drilledu.ui.HomeFragment;
import com.example.drilledu.ui.MoreFragment;
import com.example.drilledu.ui.ResourcesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_navigation);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_drills) {
                selectedFragment = new DrillsFragment();
            } else if (itemId == R.id.nav_resources) {
                selectedFragment = new ResourcesFragment();
            } else if (itemId == R.id.nav_more) {
                selectedFragment = new MoreFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
                return true;
            }
            return false;
        });

        findViewById(R.id.btn_notification).setOnClickListener(v -> showNotificationDialog());
    }

    public void navigateToTab(int menuId) {
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(menuId);
        }
    }

    private void showNotificationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Safety Notifications & Alerts")
                .setMessage("No active school emergency alerts at this time.\n\nAll safety systems and drill schedules are normal.")
                .setIcon(R.drawable.ic_notification)
                .setPositiveButton("OK", null)
                .show();
    }
}