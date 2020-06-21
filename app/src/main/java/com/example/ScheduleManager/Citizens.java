package com.example.ScheduleManager;

public class Citizens {


    private String Fname;
    private String Lname;
    private String id;
    private String province;
    private String district;
    private String sector;
    private String cell;
    private String token;

    public void setFname(String fname) {
        Fname = fname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public Citizens() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFname() {
        return Fname;
    }

    public String getLname() {
        return Lname;
    }

    public String getId() {
        return id;
    }

    public String getProvince() {
        return province;
    }

    public String getDistrict() {
        return district;
    }

    public String getSector() {
        return sector;
    }

    public String getCell() {
        return cell;
    }


}
