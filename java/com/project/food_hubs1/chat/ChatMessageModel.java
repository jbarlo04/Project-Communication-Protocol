package com.project.food_hubs1.chat;

public class ChatMessageModel {
    String cmobile;
    String usermobile;
    String oid;
    String bname;
    String date;
    String message;
    String usermobile_cmobile;
    String senderId;


    public ChatMessageModel(String cmobile, String mobile, String oid, String bname, String date, String message,String usermobile_cmobile,String senderId) {
        this.cmobile = cmobile;
        this.usermobile = mobile;
        this.oid = oid;
        this.bname = bname;
        this.date = date;
        this.message = message;
        this.usermobile_cmobile = usermobile_cmobile;
        this.senderId  = senderId;

    }

    public ChatMessageModel() {
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getUsermobile_cmobile() {
        return usermobile_cmobile;
    }

    public void setUsermobile_cmobile(String usermobile_cmobile) {
        this.usermobile_cmobile = usermobile_cmobile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String lastmessage) {
        this.message = lastmessage;
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
