package com.example.drilledu.models;

import java.io.Serializable;

public class QuizQuestion implements Serializable {
    private long id;
    private String category; // Fire, Earthquake, Evacuation, General
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private int correctOptionIndex; // 0 for A, 1 for B, 2 for C, 3 for D
    private String explanation;

    public QuizQuestion(long id, String category, String questionText, String optionA,
                        String optionB, String optionC, String optionD,
                        int correctOptionIndex, String explanation) {
        this.id = id;
        this.category = category;
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctOptionIndex = correctOptionIndex;
        this.explanation = explanation;
    }

    public long getId() { return id; }
    public String getCategory() { return category; }
    public String getQuestionText() { return questionText; }
    public String getOptionA() { return optionA; }
    public String getOptionB() { return optionB; }
    public String getOptionC() { return optionC; }
    public String getOptionD() { return optionD; }
    public int getCorrectOptionIndex() { return correctOptionIndex; }
    public String getExplanation() { return explanation; }
}