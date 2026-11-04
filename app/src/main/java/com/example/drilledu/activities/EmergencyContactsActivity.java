package com.example.drilledu.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drilledu.R;
import com.example.drilledu.adapters.EmergencyContactAdapter;
import com.example.drilledu.database.DrillEduDbHelper;
import com.example.drilledu.models.EmergencyContact;

import java.util.List;

public class EmergencyContactsActivity extends AppCompatActivity implements EmergencyContactAdapter.OnContactClickListener {

    private RecyclerView rvContacts;
    private DrillEduDbHelper dbHelper;
    private EmergencyContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);

        dbHelper = DrillEduDbHelper.getInstance(this);

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        rvContacts = findViewById(R.id.rv_contacts);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));

        loadContacts();
    }

    private void loadContacts() {
        List<EmergencyContact> list = dbHelper.getAllContacts();
        adapter = new EmergencyContactAdapter(this, list, this);
        rvContacts.setAdapter(adapter);
    }

    @Override
    public void onCallClick(EmergencyContact contact) {
        new AlertDialog.Builder(this)
                .setTitle("Place Emergency Call?")
                .setMessage("Are you sure you want to dial " + contact.getName() + " (" + contact.getPhoneNumber() + ")?")
                .setIcon(R.drawable.ic_call)
                .setPositiveButton("Dial Call", (dialog, which) -> {
                    try {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + contact.getPhoneNumber()));
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(this, "Unable to place call on this device.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onContactLongClick(EmergencyContact contact) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Contact: " + contact.getName());

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 20, 40, 20);

        final EditText etName = new EditText(this);
        etName.setHint("Contact Name");
        etName.setText(contact.getName());
        layout.addView(etName);

        final EditText etPhone = new EditText(this);
        etPhone.setHint("Phone Number");
        etPhone.setText(contact.getPhoneNumber());
        layout.addView(etPhone);

        builder.setView(layout);
        builder.setPositiveButton("Save", (dialog, which) -> {
            String newName = etName.getText().toString().trim();
            String newPhone = etPhone.getText().toString().trim();
            if (!newName.isEmpty() && !newPhone.isEmpty()) {
                dbHelper.updateContactNumber(contact.getId(), newName, newPhone);
                loadContacts();
                Toast.makeText(EmergencyContactsActivity.this, "Contact updated.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}