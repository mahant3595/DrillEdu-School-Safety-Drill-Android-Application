package com.example.drilledu.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drilledu.R;
import com.example.drilledu.activities.ResourceDetailActivity;
import com.example.drilledu.database.DrillEduDbHelper;
import com.example.drilledu.models.LearningResource;

import java.util.List;

public class ResourceAdapter extends RecyclerView.Adapter<ResourceAdapter.ViewHolder> {

    private final Context context;
    private final List<LearningResource> resources;
    private final DrillEduDbHelper dbHelper;

    public ResourceAdapter(Context context, List<LearningResource> resources) {
        this.context = context;
        this.resources = resources;
        this.dbHelper = DrillEduDbHelper.getInstance(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_resource, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LearningResource item = resources.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvMeta.setText(item.getCategory() + " • " + item.getReadTime());
        holder.tvSummary.setText(item.getSummary());

        holder.btnBookmark.setImageResource(item.isBookmarked() ? R.drawable.ic_bookmark_filled : R.drawable.ic_bookmark);

        int iconRes = R.drawable.ic_resources;
        if ("ic_fire".equals(item.getIconName())) iconRes = R.drawable.ic_fire;
        else if ("ic_earthquake".equals(item.getIconName())) iconRes = R.drawable.ic_earthquake;
        else if ("ic_medical".equals(item.getIconName())) iconRes = R.drawable.ic_medical;
        else if ("ic_flood".equals(item.getIconName())) iconRes = R.drawable.ic_flood;
        else if ("ic_lockdown".equals(item.getIconName())) iconRes = R.drawable.ic_lockdown;

        holder.ivIcon.setImageResource(iconRes);

        holder.btnBookmark.setOnClickListener(v -> {
            boolean newBookmarkState = !item.isBookmarked();
            item.setBookmarked(newBookmarkState);
            dbHelper.toggleBookmark(item.getId(), newBookmarkState);
            notifyItemChanged(position);
            Toast.makeText(context, newBookmarkState ? "Resource Bookmarked" : "Bookmark Removed", Toast.LENGTH_SHORT).show();
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ResourceDetailActivity.class);
            intent.putExtra("RESOURCE_ITEM", item);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return resources.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        ImageView btnBookmark;
        TextView tvTitle;
        TextView tvMeta;
        TextView tvSummary;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_resource_icon);
            btnBookmark = itemView.findViewById(R.id.btn_bookmark);
            tvTitle = itemView.findViewById(R.id.tv_resource_title);
            tvMeta = itemView.findViewById(R.id.tv_resource_meta);
            tvSummary = itemView.findViewById(R.id.tv_resource_summary);
        }
    }
}