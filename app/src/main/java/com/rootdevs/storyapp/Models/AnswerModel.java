package com.rootdevs.storyapp.Models;

public class AnswerModel {

    private String ansId, answer, setId, quesId, userId;

    public AnswerModel(String ansId, String answer, String setId, String quesId, String userId) {
        this.ansId = ansId;
        this.answer = answer;
        this.setId = setId;
        this.quesId = quesId;
        this.userId = userId;
    }

    public AnswerModel() {
    }

    public String getAnsId() {
        return ansId;
    }

    public void setAnsId(String ansId) {
        this.ansId = ansId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSetId() {
        return setId;
    }

    public void setSetId(String setId) {
        this.setId = setId;
    }

    public String getQuesId() {
        return quesId;
    }

    public void setQuesId(String quesId) {
        this.quesId = quesId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
