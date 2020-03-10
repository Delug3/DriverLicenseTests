package com.driverlicense.tests.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Test implements Serializable {
    @SerializedName("question")
    @Expose
    public String question;
    @SerializedName("answer_a")
    @Expose
    public String answerA;
    @SerializedName("answer_b")
    @Expose
    public String answerB;
    @SerializedName("answer_c")
    @Expose
    public String answerC;
    @SerializedName("answer_d")
    @Expose
    public String answerD;
    @SerializedName("image_url")
    @Expose
    public String imageUrl;
    @SerializedName("correct_answer")
    @Expose
    public String correctAnswer;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

}