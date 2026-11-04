package com.example.drilledu.models;

import java.io.Serializable;

public class SafetyGuide implements Serializable {
    private String id;
    private String title;
    private String category;
    private String summary;
    private String iconName;
    private String intro;
    private String hazards;
    private String prevention;
    private String beforeSteps;
    private String duringSteps;
    private String afterSteps;
    private String warnings;

    public SafetyGuide(String id, String title, String category, String summary, String iconName,
                       String intro, String hazards, String prevention,
                       String beforeSteps, String duringSteps, String afterSteps, String warnings) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.summary = summary;
        this.iconName = iconName;
        this.intro = intro;
        this.hazards = hazards;
        this.prevention = prevention;
        this.beforeSteps = beforeSteps;
        this.duringSteps = duringSteps;
        this.afterSteps = afterSteps;
        this.warnings = warnings;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getSummary() { return summary; }
    public String getIconName() { return iconName; }
    public String getIntro() { return intro; }
    public String getHazards() { return hazards; }
    public String getPrevention() { return prevention; }
    public String getBeforeSteps() { return beforeSteps; }
    public String getDuringSteps() { return duringSteps; }
    public String getAfterSteps() { return afterSteps; }
    public String getWarnings() { return warnings; }
}