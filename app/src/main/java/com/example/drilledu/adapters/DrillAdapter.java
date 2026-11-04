package com.example.drilledu.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drilledu.R;
import com.example.drilledu.activities.DrillDetailActivity;
import com.example.drilledu.models.DrillItem;

import java.util.List;

public class DrillAdapter extends RecyclerView.Adapter<DrillAdapter.ViewHolder> {

    private final Context context;
    private final List<DrillItem> drills;

    public DrillAdapter(Context context, List<DrillItem> drills) {
        this.context = context;
        this.drills = drills;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_drill, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DrillItem drill = drills.get(position);
        holder.tvTitle.setText(drill.getTitle());
        holder.tvPurpose.setText(drill.getPurpose());

        if (drill.isCompleted()) {
            holder.tvStatus.setText("COMPLETED ✓");
            holder.tvStatus.setBackgroundResource(R.drawable.bg_status_completed);
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.success_green));
        } else {
            holder.tvStatus.setText("PENDING");
            holder.tvStatus.setBackgroundResource(R.drawable.bg_status_pending);
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.text_secondary));
        }

        int iconRes = R.drawable.ic_fire;
        if ("ic_earthquake".equals(drill.getIconName())) iconRes = R.drawable.ic_earthquake;
        else if ("ic_flood".equals(drill.getIconName())) iconRes = R.drawable.ic_flood;
        else if ("ic_weather".equals(drill.getIconName())) iconRes = R.drawable.ic_weather;
        else if ("ic_lockdown".equals(drill.getIconName())) iconRes = R.drawable.ic_lockdown;
        else if ("ic_evacuation".equals(drill.getIconName())) iconRes = R.drawable.ic_evacuation;

        holder.ivIcon.setImageResource(iconRes);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DrillDetailActivity.class);
            intent.putExtra("DRILL_ITEM", drill);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return drills.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvTitle;
        TextView tvPurpose;
        TextView tvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_drill_icon);
            tvTitle = itemView.findViewById(R.id.tv_drill_title);
            tvPurpose = itemView.findViewById(R.id.tv_drill_purpose);
            tvStatus = itemView.findViewById(R.id.tv_drill_status);
        }
    }
}