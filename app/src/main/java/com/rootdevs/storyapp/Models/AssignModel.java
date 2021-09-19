package com.rootdevs.storyapp.Models;

public class AssignModel {

    private String quesId, ques, answer, setId, marks;

    public AssignModel(String quesId, String ques, String answer, String setId, String marks) {
        this.quesId = quesId;
        this.ques = ques;
        this.answer = answer;
        this.setId = setId;
        this.marks = marks;
    }

    public AssignModel() {

    }

    public String getQuesId() {
        return quesId;
    }

    public void setQuesId(String quesId) {
        this.quesId = quesId;
    }

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
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

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }
}
