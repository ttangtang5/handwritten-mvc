package com.tang.model;

public class QuestionOption {

    private Integer id;
    private Integer questionId;
    private String questionOption;
    private Integer questionIndex;
    private String answer;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getQuestionId() {
        return questionId;
    }
    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }
    public String getQuestionOption() {
        return questionOption;
    }
    public void setQuestionOption(String questionOption) {
        this.questionOption = questionOption;
    }
    public Integer getQuestionIndex() {
        return questionIndex;
    }
    public void setQuestionIndex(Integer questionIndex) {
        this.questionIndex = questionIndex;
    }
    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public QuestionOption(Integer id, Integer questionId,
            String questionOption, Integer questionIndex, String answer) {
        super();
        this.id = id;
        this.questionId = questionId;
        this.questionOption = questionOption;
        this.questionIndex = questionIndex;
        this.answer = answer;
    }
    public QuestionOption() {
        super();
    }
    @Override
    public String toString() {
        return "QuestionOption [id=" + id + ", questionId=" + questionId
                + ", questionOption=" + questionOption + ", questionIndex="
                + questionIndex + ", answer=" + answer + "]";
    }
}
