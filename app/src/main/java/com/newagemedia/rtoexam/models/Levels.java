package com.newagemedia.rtoexam.models;

import java.io.Serializable;

public class Levels implements Serializable {

    public String level;

    public Levels(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
