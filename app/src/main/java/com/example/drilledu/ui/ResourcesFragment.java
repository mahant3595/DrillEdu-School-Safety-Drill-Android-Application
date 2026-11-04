package com.example.drilledu.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drilledu.R;
import com.example.drilledu.adapters.ResourceAdapter;
import com.example.drilledu.database.DrillEduDbHelper;
import com.example.drilledu.models.LearningResource;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class ResourcesFragment extends Fragment {

    private RecyclerView rvResources;
    private EditText etSearch;
    private ResourceAdapter adapter;
    private DrillEduDbHelper dbHelper;
    private String currentSearch = "";
    private String currentCategory = "ALL";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resources, container, false);
        dbHelper = DrillEduDbHelper.getInstance(requireContext());

        rvResources = view.findViewById(R.id.rv_resources);
        etSearch = view.findViewById(R.id.et_search_resources);
        rvResources.setLayoutManager(new LinearLayoutManager(requireContext()));

        ChipGroup chipGroup = view.findViewById(R.id.chip_group_resources);
        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.contains(R.id.chip_res_articles)) currentCategory = "Articles";
            else if (checkedIds.contains(R.id.chip_res_infographics)) currentCategory = "Infographics";
            else if (checkedIds.contains(R.id.chip_res_firstaid)) currentCategory = "First-Aid";
            else if (checkedIds.contains(R.id.chip_res_drills)) currentCategory = "Drill Guides";
            else currentCategory = "ALL";
            loadResources();
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentSearch = s.toString();
                loadResources();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadResources();
    }

    private void loadResources() {
        List<LearningResource> list = dbHelper.getAllResources(currentSearch, currentCategory);
        adapter = new ResourceAdapter(requireContext(), list);
        rvResources.setAdapter(adapter);
    }
}