package com.project.food_hubs1.chat;

public class ChatListModel {
    String cmobile;
    String usermobile;
    String oid;
    String bname;
    String date;
    String lastmessage;


    public ChatListModel(String cmobile, String mobile, String oid, String bname, String date,String lastmessage) {
        this.cmobile = cmobile;
        this.usermobile = mobile;
        this.oid = oid;
        this.bname = bname;
        this.date = date;
        this.lastmessage = lastmessage;

    }

    public ChatListModel() {
    }


    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }

    public String getCmobile() {
        return cmobile;
    }

    public void setCmobile(String cmobile) {
        this.cmobile = cmobile;
    }

    public String getUsermobile() {
        return usermobile;
    }

    public void setUsermobile(String usermobile) {
        this.usermobile = usermobile;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
