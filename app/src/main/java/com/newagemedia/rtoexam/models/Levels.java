package com.newagemedia.rtoexam.models;

import java.io.Serializable;

public class Levels implements Serializable {

    //Json fields: Level name
    //questions->each level have ~10 questions
    //answers->4 answers, only one is correct
    //correct answer, to compare with the selected one

    public String level;

    public Levels() {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
