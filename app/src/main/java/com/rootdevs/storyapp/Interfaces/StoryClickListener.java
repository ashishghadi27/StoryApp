package com.rootdevs.storyapp.Interfaces;

import com.rootdevs.storyapp.Models.StoryModel;

public interface StoryClickListener {
    void onStoryClick(StoryModel story);
    void deleteStory(String id);
}
