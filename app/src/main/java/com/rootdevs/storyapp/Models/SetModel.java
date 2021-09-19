package com.rootdevs.storyapp.Models;

public class SetModel {

    private String setId, setName, storyId;

    public SetModel(String setId, String setName, String storyId) {
        this.setId = setId;
        this.setName = setName;
        this.storyId = storyId;
    }

    public String getSetId() {
        return setId;
    }

    public void setSetId(String setId) {
        this.setId = setId;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }
}
