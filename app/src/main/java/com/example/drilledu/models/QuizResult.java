package com.example.drilledu.models;

import java.io.Serializable;

public class QuizResult implements Serializable {
    private long id;
    private String category;
    private int score;
    private int totalQuestions;
    private long timestamp;

    public QuizResult(long id, String category, int score, int totalQuestions, long timestamp) {
        this.id = id;
        this.category = category;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.timestamp = timestamp;
    }

    public long getId() { return id; }
    public String getCategory() { return category; }
    public int getScore() { return score; }
    public int getTotalQuestions() { return totalQuestions; }
    public long getTimestamp() { return timestamp; }
    public int getPercentage() {
        return totalQuestions > 0 ? (int) Math.round(((double) score / totalQuestions) * 100) : 0;
    }
}