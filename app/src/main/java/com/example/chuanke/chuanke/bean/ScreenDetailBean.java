package com.example.chuanke.chuanke.bean;

public class ScreenDetailBean {
    private String stype;
    private String sratio;
    private String sresolution;
    private String spic;
    private String sprice;
    private String slongitude;
    private String slatitude;
    private int sstate;


    public String getSlongitude() {
        return slongitude;
    }

    public void setSlongitude(String slongitude) {
        this.slongitude = slongitude;
    }

    public String getSlatitude() {
        return slatitude;
    }

    public void setSlatitude(String slatitude) {
        this.slatitude = slatitude;
    }

    public int getSstate() {
        return sstate;
    }

    public void setSstate(int sstate) {
        this.sstate = sstate;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    private String sid;

    public String getSplace() {
        return splace;
    }

    public void setSplace(String splace) {
        this.splace = splace;
    }

    private String splace;
//    private int;

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public String getSratio() {
        return sratio;
    }

    public void setSratio(String screenSize) {
        this.sratio = screenSize;
    }

    public String getSresolution() {
        return sresolution;
    }

    public void setSresolution(String sresolution) {
        this.sresolution = sresolution;
    }

    public String getSpic() {
        return spic;
    }

    public void setSpic(String spic) {
        this.spic = spic;
    }

    public String getSprice() {
        return sprice;
    }

    public void setSprice(String sprice) {
        this.sprice = sprice;
    }
}
