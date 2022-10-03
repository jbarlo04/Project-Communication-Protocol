package com.project.food_hubs1;

public class Orders {
    String cmobile;
    String mobile;
    String oid;
    String bname;
    String date;
    String itemsordered;
    String customername;
    String status;

    public Orders(String cmobile, String mobile, String oid, String bname, String date, String itemsordered,String customername, String status) {
        this.cmobile = cmobile;
        this.mobile = mobile;
        this.oid = oid;
        this.bname = bname;
        this.date = date;
        this.itemsordered = itemsordered;
        this.customername = customername;
        this.status = status;
    }

    public Orders() {
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getCmobile() {
        return cmobile;
    }

    public void setCmobile(String cmobile) {
        this.cmobile = cmobile;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getItemsordered() {
        return itemsordered;
    }

    public void setItemsordered(String itemsordered) {
        this.itemsordered = itemsordered;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }
}
