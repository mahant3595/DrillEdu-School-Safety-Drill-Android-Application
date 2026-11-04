package com.example.drilledu.models;

import java.io.Serializable;

public class ChecklistItem implements Serializable {
    private long id;
    private String category; // BEFORE, DURING, AFTER
    private String text;
    private String drillType; // Fire, Earthquake, General, etc.
    private boolean isCompleted;

    public ChecklistItem(long id, String category, String text, String drillType, boolean isCompleted) {
        this.id = id;
        this.category = category;
        this.text = text;
        this.drillType = drillType;
        this.isCompleted = isCompleted;
    }

    public long getId() { return id; }
    public String getCategory() { return category; }
    public String getText() { return text; }
    public String getDrillType() { return drillType; }
    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
}