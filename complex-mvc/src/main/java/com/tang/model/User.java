package com.tang.model;

import java.util.Date;

public class User {

    private Integer id;
    private String userName;
    private String password;
    private String displayName;
    private String gender;
    private String telephoneNumber;
    private String email;
    private String photo;
    private Date createTime;
    private Date updateTime;
    private Integer createBy;
    private Integer updateBy;
    private String flag;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getTelephoneNumber() {
        return telephoneNumber;
    }
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
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
    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    public User(Integer id, String userName, String password,
            String displayName, String gender, String telephoneNumber,
            String email, String photo, Date createTime, Date updateTime,
            Integer createBy, Integer updateBy, String flag) {
        super();
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.displayName = displayName;
        this.gender = gender;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.photo = photo;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.createBy = createBy;
        this.updateBy = updateBy;
        this.flag = flag;
    }
    public User() {
        super();
    }
    @Override
    public String toString() {
        return "User [id=" + id + ", userName=" + userName + ", password="
                + password + ", displayName=" + displayName + ", gender="
                + gender + ", telephoneNumber=" + telephoneNumber + ", email="
                + email + ", photo=" + photo + ", createTime=" + createTime
                + ", updateTime=" + updateTime + ", createBy=" + createBy
                + ", updateBy=" + updateBy + ", flag=" + flag + "]";
    }
}
