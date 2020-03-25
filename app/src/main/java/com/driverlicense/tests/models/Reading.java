package com.driverlicense.tests.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reading {

    @SerializedName("question")
    @Expose
    public String question;
    @SerializedName("correct_answer")
    @Expose
    public String correctAnswer;
    @SerializedName("image_url")
    @Expose
    public String imageUrl;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}