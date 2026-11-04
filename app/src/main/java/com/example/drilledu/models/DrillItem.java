package com.example.drilledu.models;

import java.io.Serializable;

public class DrillItem implements Serializable {
    private String id;
    private String title;
    private String type; // Fire, Earthquake, Flood, Weather, Lockdown, Evacuation
    private String iconName;
    private String purpose;
    private String prepInstructions;
    private String procedureSteps; // JSON or formatted bullet points
    private String dosAndDonts;
    private String safetyReminders;
    private boolean isCompleted;
    private long completedTimestamp;

    public DrillItem(String id, String title, String type, String iconName, String purpose,
                     String prepInstructions, String procedureSteps, String dosAndDonts,
                     String safetyReminders, boolean isCompleted, long completedTimestamp) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.iconName = iconName;
        this.purpose = purpose;
        this.prepInstructions = prepInstructions;
        this.procedureSteps = procedureSteps;
        this.dosAndDonts = dosAndDonts;
        this.safetyReminders = safetyReminders;
        this.isCompleted = isCompleted;
        this.completedTimestamp = completedTimestamp;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getType() { return type; }
    public String getIconName() { return iconName; }
    public String getPurpose() { return purpose; }
    public String getPrepInstructions() { return prepInstructions; }
    public String getProcedureSteps() { return procedureSteps; }
    public String getDosAndDonts() { return dosAndDonts; }
    public String getSafetyReminders() { return safetyReminders; }
    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
    public long getCompletedTimestamp() { return completedTimestamp; }
    public void setCompletedTimestamp(long completedTimestamp) { this.completedTimestamp = completedTimestamp; }
}