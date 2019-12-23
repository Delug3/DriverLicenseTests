package com.newagemedia.rtoexam.models;

import java.io.Serializable;
import java.util.List;

public class Levels implements Serializable {

    //Json fields: Level name
    //questions->each level_name have ~10 questions
    //answers->4 answers, only one is correct
    //correct answer, to compare with the selected one

    public Number level_number;
    public String level_name;
    public String question;
    public String answer_one;
    public String answer_two;
    public String answer_three;
    public String answer_four;
    public String correct_answer;

    //quiz: array of objects containing the questions,answers and correct answer
    public List<String> quiz;

    public Levels() {
        this.level_name = level_name;
        this.level_number = level_number;
        this.question = question;
        this.answer_one = answer_one;
        this.answer_two = answer_two;
        this.answer_three = answer_three;
        this.answer_four = answer_four;
        this.correct_answer = correct_answer;
        this.quiz = quiz;
    }

    public Number getLevel_number() {
        return level_number;
    }

    public void setLevel_number(Number level_number) {
        this.level_number = level_number;
    }

    public String getLevel_name() {
        return level_name;
    }

    public void setLevel_name(String level_name) {
        this.level_name = level_name;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer_one() {
        return answer_one;
    }

    public void setAnswer_one(String answer_one) {
        this.answer_one = answer_one;
    }

    public String getAnswer_two() {
        return answer_two;
    }

    public void setAnswer_two(String answer_two) {
        this.answer_two = answer_two;
    }

    public String getAnswer_three() {
        return answer_three;
    }

    public void setAnswer_three(String answer_three) {
        this.answer_three = answer_three;
    }

    public String getAnswer_four() {
        return answer_four;
    }

    public void setAnswer_four(String answer_four) {
        this.answer_four = answer_four;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public List<String> getQuiz() {
        return quiz;
    }

    public void setQuiz(List<String> quiz) {
        this.quiz = quiz;
    }
}
