package com.example.drilledu.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drilledu.R;
import com.example.drilledu.adapters.DrillAdapter;
import com.example.drilledu.database.DrillEduDbHelper;
import com.example.drilledu.models.DrillItem;

import java.util.List;

public class DrillsFragment extends Fragment {

    private RecyclerView rvDrills;
    private DrillAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drills, container, false);
        rvDrills = view.findViewById(R.id.rv_drills);
        rvDrills.setLayoutManager(new LinearLayoutManager(requireContext()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDrillsData();
    }

    private void loadDrillsData() {
        List<DrillItem> drills = DrillEduDbHelper.getInstance(requireContext()).getAllDrills();
        adapter = new DrillAdapter(requireContext(), drills);
        rvDrills.setAdapter(adapter);
    }
}