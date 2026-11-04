package com.example.drilledu.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drilledu.R;
import com.example.drilledu.activities.SafetyDetailActivity;
import com.example.drilledu.models.SafetyGuide;

import java.util.List;

public class SafetyGuideAdapter extends RecyclerView.Adapter<SafetyGuideAdapter.ViewHolder> {

    private final Context context;
    private final List<SafetyGuide> guides;

    public SafetyGuideAdapter(Context context, List<SafetyGuide> guides) {
        this.context = context;
        this.guides = guides;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_safety_guide, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SafetyGuide guide = guides.get(position);
        holder.tvTitle.setText(guide.getTitle());
        holder.tvSummary.setText(guide.getSummary());

        int iconRes = R.drawable.ic_shield;
        if ("ic_fire".equals(guide.getIconName())) iconRes = R.drawable.ic_fire;
        else if ("ic_earthquake".equals(guide.getIconName())) iconRes = R.drawable.ic_earthquake;
        else if ("ic_flood".equals(guide.getIconName())) iconRes = R.drawable.ic_flood;
        else if ("ic_weather".equals(guide.getIconName())) iconRes = R.drawable.ic_weather;
        else if ("ic_medical".equals(guide.getIconName())) iconRes = R.drawable.ic_medical;

        holder.ivIcon.setImageResource(iconRes);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SafetyDetailActivity.class);
            intent.putExtra("SAFETY_GUIDE", guide);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return guides.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvTitle;
        TextView tvSummary;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_guide_icon);
            tvTitle = itemView.findViewById(R.id.tv_guide_title);
            tvSummary = itemView.findViewById(R.id.tv_guide_summary);
        }
    }
}