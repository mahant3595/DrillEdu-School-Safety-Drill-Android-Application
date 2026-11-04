package com.example.drilledu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drilledu.R;
import com.example.drilledu.models.EmergencyContact;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class EmergencyContactAdapter extends RecyclerView.Adapter<EmergencyContactAdapter.ViewHolder> {

    public interface OnContactClickListener {
        void onCallClick(EmergencyContact contact);
        void onContactLongClick(EmergencyContact contact);
    }

    private final Context context;
    private final List<EmergencyContact> contacts;
    private final OnContactClickListener listener;

    public EmergencyContactAdapter(Context context, List<EmergencyContact> contacts, OnContactClickListener listener) {
        this.context = context;
        this.contacts = contacts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_emergency_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EmergencyContact contact = contacts.get(position);
        holder.tvName.setText(contact.getName());
        holder.tvNumber.setText(contact.getPhoneNumber());
        holder.tvDesc.setText(contact.getDescription());

        int iconRes = R.drawable.ic_call;
        if ("ic_fire".equals(contact.getIconName())) iconRes = R.drawable.ic_fire;
        else if ("ic_medical".equals(contact.getIconName())) iconRes = R.drawable.ic_medical;
        else if ("ic_shield".equals(contact.getIconName())) iconRes = R.drawable.ic_shield;
        else if ("ic_school".equals(contact.getIconName())) iconRes = R.drawable.ic_school;
        else if ("ic_person".equals(contact.getIconName())) iconRes = R.drawable.ic_person;

        holder.ivIcon.setImageResource(iconRes);

        holder.btnCall.setOnClickListener(v -> {
            if (listener != null) listener.onCallClick(contact);
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) listener.onContactLongClick(contact);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvName;
        TextView tvNumber;
        TextView tvDesc;
        MaterialButton btnCall;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_contact_icon);
            tvName = itemView.findViewById(R.id.tv_contact_name);
            tvNumber = itemView.findViewById(R.id.tv_contact_number);
            tvDesc = itemView.findViewById(R.id.tv_contact_desc);
            btnCall = itemView.findViewById(R.id.btn_call_action);
        }
    }
}