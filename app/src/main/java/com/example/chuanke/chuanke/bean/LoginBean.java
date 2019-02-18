package com.example.chuanke.chuanke.bean;

import java.util.Date;

public class LoginBean {
    private int uid;
    private String uname;
    private String uemail;
    private String uphone;
    private String ustate;
    private Date uzctime;
    public void setUid(int uid) {
        this.uid = uid;
    }
    public int getUid() {
        return uid;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
    public String getUname() {
        return uname;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }
    public String getUemail() {
        return uemail;
    }

    public void setUphone(String uphone) {
        this.uphone = uphone;
    }
    public String getUphone() {
        return uphone;
    }

    public void setUstate(String ustate) {
        this.ustate = ustate;
    }
    public String getUstate() {
        return ustate;
    }

    public void setUzctime(Date uzctime) {
        this.uzctime = uzctime;
    }
    public Date getUzctime() {
        return uzctime;
    }

}
