package com.driverlicense.tests.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Sheet implements Parcelable {
    public Integer savedQuestionNumber;
    public String savedQuestion;
    public String savedCorrectAnswer;
    public String savedIncorrectAnswer;


    public Sheet(Parcel in) {
        savedQuestionNumber = in.readInt();
        savedQuestion = in.readString();
        savedCorrectAnswer = in.readString();
        savedIncorrectAnswer = in.readString();
    }

    public static final Creator<Sheet> CREATOR = new Creator<Sheet>() {
        @Override
        public Sheet createFromParcel(Parcel in) {
            return new Sheet(in);
        }

        @Override
        public Sheet[] newArray(int size) {
            return new Sheet[size];
        }
    };


    public Sheet(Integer savedQuestionNumber, String savedQuestion, String savedCorrectAnswer, String savedIncorrectAnswer) {
        this.savedQuestionNumber = savedQuestionNumber;
        this.savedQuestion = savedQuestion;
        this.savedCorrectAnswer = savedCorrectAnswer;
        this.savedIncorrectAnswer = savedIncorrectAnswer;
    }

    public Integer getSavedQuestionNumber() {
        return savedQuestionNumber;
    }

    public void setSavedQuestionNumber(Integer savedQuestionNumber) {
        this.savedQuestionNumber = savedQuestionNumber;
    }

    public String getSavedQuestion() {
        return savedQuestion;
    }

    public void setSavedQuestion(String savedQuestion) {
        this.savedQuestion = savedQuestion;
    }

    public String getSavedCorrectAnswer() {
        return savedCorrectAnswer;
    }

    public void setSavedCorrectAnswer(String savedCorrectAnswer) {
        this.savedCorrectAnswer = savedCorrectAnswer;
    }

    public String getSavedIncorrectAnswer() {
        return savedIncorrectAnswer;
    }

    public void setSavedIncorrectAnswer(String savedIncorrectAnswer) {
        this.savedIncorrectAnswer = savedIncorrectAnswer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(savedQuestionNumber);
        parcel.writeString(savedQuestion);
        parcel.writeString(savedCorrectAnswer);
        parcel.writeString(savedIncorrectAnswer);
    }
}
