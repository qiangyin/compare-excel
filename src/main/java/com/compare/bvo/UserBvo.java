package com.compare.bvo;

/**
 * Created by ytq on 2014/9/16.
 */
public class UserBvo {
    private String id;
    private String userName;
    private String idCard;
    private String department;
    private String waitExamTime;
    private String attendTimes;
    private String lateExamTime;
    private String tel;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getWaitExamTime() {
        return waitExamTime;
    }

    public void setWaitExamTime(String waitExamTime) {
        this.waitExamTime = waitExamTime;
    }

    public String getAttendTimes() {
        return attendTimes;
    }

    public void setAttendTimes(String attendTimes) {
        this.attendTimes = attendTimes;
    }

    public String getLateExamTime() {
        return lateExamTime;
    }

    public void setLateExamTime(String lateExamTime) {
        this.lateExamTime = lateExamTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
