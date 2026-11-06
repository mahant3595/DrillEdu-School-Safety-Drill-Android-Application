package com.example.drilledu.models;

import java.io.Serializable;

public class AchievementItem implements Serializable {
    private String id;
    private String title;
    private String description;
    private String iconName;
    private boolean isUnlocked;
    private long unlockedTimestamp;
    private int currentProgress;
    private int targetProgress;

    public AchievementItem(String id, String title, String description, String iconName,
                           boolean isUnlocked, long unlockedTimestamp, int currentProgress, int targetProgress) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.iconName = iconName;
        this.isUnlocked = isUnlocked;
        this.unlockedTimestamp = unlockedTimestamp;
        this.currentProgress = currentProgress;
        this.targetProgress = targetProgress;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getIconName() { return iconName; }
    public boolean isUnlocked() { return isUnlocked; }
    public void setUnlocked(boolean unlocked) { isUnlocked = unlocked; }
    public long getUnlockedTimestamp() { return unlockedTimestamp; }
    public void setUnlockedTimestamp(long unlockedTimestamp) { this.unlockedTimestamp = unlockedTimestamp; }
    public int getCurrentProgress() { return currentProgress; }
    public void setCurrentProgress(int currentProgress) { this.currentProgress = currentProgress; }
    public int getTargetProgress() { return targetProgress; }
}