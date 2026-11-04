package com.example.drilledu.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drilledu.R;
import com.example.drilledu.database.DrillEduDbHelper;
import com.example.drilledu.models.ChecklistItem;

import java.util.List;

public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.ViewHolder> {

    public interface OnChecklistChangeListener {
        void onItemCheckChanged();
    }

    private final Context context;
    private final List<ChecklistItem> items;
    private final DrillEduDbHelper dbHelper;
    private final OnChecklistChangeListener listener;

    public ChecklistAdapter(Context context, List<ChecklistItem> items, OnChecklistChangeListener listener) {
        this.context = context;
        this.items = items;
        this.dbHelper = DrillEduDbHelper.getInstance(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_checklist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChecklistItem item = items.get(position);
        holder.tvText.setText(item.getText());
        holder.tvCategory.setText(item.getCategory() + " DRILL • " + item.getDrillType());

        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(item.isCompleted());

        updateStrikeThrough(holder.tvText, item.isCompleted());

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setCompleted(isChecked);
            dbHelper.updateChecklistItem(item.getId(), isChecked);
            updateStrikeThrough(holder.tvText, isChecked);
            if (listener != null) {
                listener.onItemCheckChanged();
            }
        });

        holder.itemView.setOnClickListener(v -> holder.checkBox.toggle());
    }

    private void updateStrikeThrough(TextView tv, boolean isChecked) {
        if (isChecked) {
            tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tv.setAlpha(0.6f);
        } else {
            tv.setPaintFlags(tv.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            tv.setAlpha(1.0f);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView tvText;
        TextView tvCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.cb_checklist_item);
            tvText = itemView.findViewById(R.id.tv_checklist_text);
            tvCategory = itemView.findViewById(R.id.tv_checklist_category);
        }
    }
}