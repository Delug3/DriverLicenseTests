package com.carlicense.tests.models;

import java.io.Serializable;
import java.util.List;

public class States implements Serializable {

    public String state_name;
    public String capital_name;
    public String state_quiz_name;

    public List<String> states;

    public States() {
        this.state_name = state_name;
        this.capital_name = capital_name;
        this.state_quiz_name = state_quiz_name;
        this.states = states;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getCapital_name() {
        return capital_name;
    }

    public void setCapital_name(String capital_name) {
        this.capital_name = capital_name;
    }

    public String getState_quiz_name() {
        return state_quiz_name;
    }

    public void setState_quiz_name(String state_quiz_name) {
        this.state_quiz_name = state_quiz_name;
    }

    public List<String> getStates() {
        return states;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }
}
