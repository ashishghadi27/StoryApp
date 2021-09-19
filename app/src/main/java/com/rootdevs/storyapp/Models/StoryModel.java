package com.rootdevs.storyapp.Models;
//https://www.simplifiedcoding.net/upload-image-to-server/
public class StoryModel {

    private String id, storyName, categoryId, storySummary, content, featuredLink;

    public StoryModel(String id, String storyName, String categoryId, String storySummary, String content, String featuredLink) {
        this.id = id;
        this.storyName = storyName;
        this.categoryId = categoryId;
        this.storySummary = storySummary;
        this.content = content;
        this.featuredLink = featuredLink;
    }

    public String getStoryName() {
        return storyName;
    }

    public void setStoryName(String storyName) {
        this.storyName = storyName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getStorySummary() {
        return storySummary;
    }

    public void setStorySummary(String storySummary) {
        this.storySummary = storySummary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFeaturedLink() {
        return featuredLink;
    }

    public void setFeaturedLink(String featuredLink) {
        this.featuredLink = featuredLink;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
