package com.example.drilledu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.drilledu.R;
import com.example.drilledu.activities.AchievementsActivity;
import com.example.drilledu.activities.ChecklistActivity;
import com.example.drilledu.activities.DrillTimerActivity;
import com.example.drilledu.activities.EmergencyContactsActivity;
import com.example.drilledu.activities.EvacuationMapActivity;
import com.example.drilledu.activities.FlashcardsActivity;
import com.example.drilledu.activities.GuidelinesActivity;
import com.example.drilledu.activities.ProgressActivity;
import com.example.drilledu.activities.ReportIncidentActivity;
import com.example.drilledu.activities.SettingsActivity;
import com.example.drilledu.utils.PreferencesManager;

public class MoreFragment extends Fragment {

    private TextView tvName;
    private TextView tvClass;
    private PreferencesManager prefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        prefs = new PreferencesManager(requireContext());

        tvName = view.findViewById(R.id.tv_profile_name);
        tvClass = view.findViewById(R.id.tv_profile_class);

        view.findViewById(R.id.btn_edit_profile).setOnClickListener(v ->
                startActivity(new Intent(requireContext(), SettingsActivity.class)));

        view.findViewById(R.id.menu_achievements).setOnClickListener(v ->
                startActivity(new Intent(requireContext(), AchievementsActivity.class)));

        view.findViewById(R.id.menu_timer).setOnClickListener(v ->
                startActivity(new Intent(requireContext(), DrillTimerActivity.class)));

        view.findViewById(R.id.menu_report).setOnClickListener(v ->
                startActivity(new Intent(requireContext(), ReportIncidentActivity.class)));

        view.findViewById(R.id.menu_map).setOnClickListener(v ->
                startActivity(new Intent(requireContext(), EvacuationMapActivity.class)));

        view.findViewById(R.id.menu_flashcards).setOnClickListener(v ->
                startActivity(new Intent(requireContext(), FlashcardsActivity.class)));

        view.findViewById(R.id.menu_guidelines).setOnClickListener(v ->
                startActivity(new Intent(requireContext(), GuidelinesActivity.class)));

        view.findViewById(R.id.menu_checklist).setOnClickListener(v ->
                startActivity(new Intent(requireContext(), ChecklistActivity.class)));

        view.findViewById(R.id.menu_contacts).setOnClickListener(v ->
                startActivity(new Intent(requireContext(), EmergencyContactsActivity.class)));

        view.findViewById(R.id.menu_progress).setOnClickListener(v ->
                startActivity(new Intent(requireContext(), ProgressActivity.class)));

        view.findViewById(R.id.menu_settings).setOnClickListener(v ->
                startActivity(new Intent(requireContext(), SettingsActivity.class)));

        view.findViewById(R.id.menu_about).setOnClickListener(v -> showAboutDialog());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        tvName.setText(prefs.getStudentName());
        tvClass.setText(prefs.getStudentClass());
    }

    private void showAboutDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("About DrillEdu")
                .setMessage("DrillEdu — School Safety Drill Application\nVersion 2.0\n\nDesigned for disaster preparedness, emergency drill education, hazard reporting, and school safety awareness.\n\nKey Modules:\n• Safety Guidelines\n• Emergency Drills & Speed Simulator\n• Campus Hazard Reporter\n• Badges & Achievements\n• Evacuation Maps\n• Flashcard Practice\n• Preparedness Checklist\n• Practice Quizzes\n• Emergency Contacts\n\nBe Aware. Be Prepared. Be Safe.")
                .setIcon(R.drawable.ic_logo)
                .setPositiveButton("Close", null)
                .show();
    }
}