package com.example.drilledu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drilledu.R;
import com.example.drilledu.models.AchievementItem;

import java.util.List;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.ViewHolder> {

    private final Context context;
    private final List<AchievementItem> achievements;

    public AchievementAdapter(Context context, List<AchievementItem> achievements) {
        this.context = context;
        this.achievements = achievements;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_achievement, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AchievementItem item = achievements.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvDesc.setText(item.getDescription());

        if (item.isUnlocked()) {
            holder.tvStatus.setText("UNLOCKED 🏆");
            holder.tvStatus.setBackgroundResource(R.drawable.bg_status_completed);
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.safety_yellow));
            holder.itemView.setAlpha(1.0f);
        } else {
            holder.tvStatus.setText("LOCKED 🔒");
            holder.tvStatus.setBackgroundResource(R.drawable.bg_status_pending);
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.text_muted));
            holder.itemView.setAlpha(0.6f);
        }

        int iconRes = R.drawable.ic_achievement;
        if ("ic_fire".equals(item.getIconName())) iconRes = R.drawable.ic_fire;
        else if ("ic_earthquake".equals(item.getIconName())) iconRes = R.drawable.ic_earthquake;
        else if ("ic_checklist".equals(item.getIconName())) iconRes = R.drawable.ic_checklist;
        else if ("ic_report".equals(item.getIconName())) iconRes = R.drawable.ic_report;
        else if ("ic_timer".equals(item.getIconName())) iconRes = R.drawable.ic_timer;

        holder.ivIcon.setImageResource(iconRes);
    }

    @Override
    public int getItemCount() {
        return achievements.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvTitle;
        TextView tvDesc;
        TextView tvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_achievement_icon);
            tvTitle = itemView.findViewById(R.id.tv_achievement_title);
            tvDesc = itemView.findViewById(R.id.tv_achievement_desc);
            tvStatus = itemView.findViewById(R.id.tv_achievement_status);
        }
    }
}