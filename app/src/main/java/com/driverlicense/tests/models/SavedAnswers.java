package com.driverlicense.tests.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SavedAnswers implements Parcelable {

    public String savedCorrectAnswer;
    public String savedIncorrectAnswer;

    public SavedAnswers(Parcel in) {
        savedCorrectAnswer = in.readString();
        savedIncorrectAnswer = in.readString();
    }

    public static final Creator<SavedAnswers> CREATOR = new Creator<SavedAnswers>() {
        @Override
        public SavedAnswers createFromParcel(Parcel in) {
            return new SavedAnswers(in);
        }

        @Override
        public SavedAnswers[] newArray(int size) {
            return new SavedAnswers[size];
        }
    };

    public SavedAnswers(String savedCorrectAnswer, String savedIncorrectAnswer) {
        this.savedCorrectAnswer = savedCorrectAnswer;
        this.savedIncorrectAnswer = savedIncorrectAnswer;
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
        parcel.writeString(savedCorrectAnswer);
        parcel.writeString(savedIncorrectAnswer);
    }
}
