package com.example.drilledu.models;

import java.io.Serializable;

public class EmergencyContact implements Serializable {
    private long id;
    private String name;
    private String category; // National, Fire, Medical, Police, School, Parent
    private String phoneNumber;
    private String description;
    private String iconName;
    private boolean isCustom;

    public EmergencyContact(long id, String name, String category, String phoneNumber,
                            String description, String iconName, boolean isCustom) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.iconName = iconName;
        this.isCustom = isCustom;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getDescription() { return description; }
    public String getIconName() { return iconName; }
    public boolean isCustom() { return isCustom; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setName(String name) { this.name = name; }
}