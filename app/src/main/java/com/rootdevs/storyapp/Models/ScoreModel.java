package com.rootdevs.storyapp.Models;

public class ScoreModel {

    private String setName, storyName, marks;

    public ScoreModel(String setName, String storyName, String marks) {
        this.setName = setName;
        this.storyName = storyName;
        this.marks = marks;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public String getStoryName() {
        return storyName;
    }

    public void setStoryName(String storyName) {
        this.storyName = storyName;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }
}
