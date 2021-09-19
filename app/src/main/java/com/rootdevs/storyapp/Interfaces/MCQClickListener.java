package com.rootdevs.storyapp.Interfaces;

public interface MCQClickListener {
    void onSaveUserAnswer(String answer, String setId, String quesId);
    void deleteMCQ(String id);
}
