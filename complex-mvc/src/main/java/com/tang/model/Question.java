package com.tang.model;

import java.sql.Date;

public class Question {

    private Integer id;
    private String questionDescription;
    private Double questionScore;
    private Integer createBy;
    private Integer updateBy;
    private Date createTime;
    private Date updateTime;
    private String flag;
    private String displayId;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getQuestionDescription() {
        return questionDescription;
    }
    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }
    public Double getQuestionScore() {
        return questionScore;
    }
    public void setQuestionScore(Double questionScore) {
        this.questionScore = questionScore;
    }
    public Integer getCreateBy() {
        return createBy;
    }
    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }
    public Integer getUpdateBy() {
        return updateBy;
    }
    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    public String getDisplayId() {
        return displayId;
    }
    public void setDisplayId(String displayId) {
        this.displayId = displayId;
    }
    public Question(Integer id, String questionDescription,
            Double questionScore, Integer createBy, Integer updateBy,
            Date createTime, Date updateTime, String flag, String displayId) {
        super();
        this.id = id;
        this.questionDescription = questionDescription;
        this.questionScore = questionScore;
        this.createBy = createBy;
        this.updateBy = updateBy;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.flag = flag;
        this.displayId = displayId;
    }
    public Question() {
        super();
    }
    @Override
    public String toString() {
        return "Question [id=" + id + ", questionDescription="
                + questionDescription + ", questionScore=" + questionScore
                + ", createBy=" + createBy + ", updateBy=" + updateBy
                + ", createTime=" + createTime + ", updateTime=" + updateTime
                + ", flag=" + flag + ", displayId=" + displayId + "]";
    }

}
