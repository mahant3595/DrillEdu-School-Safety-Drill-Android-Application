package com.example.drilledu.models;

import java.io.Serializable;

public class LearningResource implements Serializable {
    private String id;
    private String title;
    private String category; // Articles, Infographics, Guides, First-Aid, Videos, Tips
    private String type; // Article, Guide, Video, Infographic
    private String readTime;
    private String summary;
    private String fullContent;
    private String iconName;
    private boolean isBookmarked;

    public LearningResource(String id, String title, String category, String type,
                            String readTime, String summary, String fullContent,
                            String iconName, boolean isBookmarked) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.type = type;
        this.readTime = readTime;
        this.summary = summary;
        this.fullContent = fullContent;
        this.iconName = iconName;
        this.isBookmarked = isBookmarked;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getType() { return type; }
    public String getReadTime() { return readTime; }
    public String getSummary() { return summary; }
    public String getFullContent() { return fullContent; }
    public String getIconName() { return iconName; }
    public boolean isBookmarked() { return isBookmarked; }
    public void setBookmarked(boolean bookmarked) { isBookmarked = bookmarked; }
}