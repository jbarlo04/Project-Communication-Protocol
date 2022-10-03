package com.project.food_hubs1.videocall;

public class VideoCallModel {
    String cmobile;
    String mobile;
    String oid;
    String isread;
    String status;
    String senderId;

    public VideoCallModel(String cmobile, String mobile, String oid, String status, String isread, String senderId) {
        this.cmobile = cmobile;
        this.mobile = mobile;
        this.oid = oid;
        this.status = status;
        this.isread = isread;
        this.senderId = senderId;
    }

    public VideoCallModel() {
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getIsread() {
        return isread;
    }

    public void setIsread(String type) {
        this.isread = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCmobile() {
        return cmobile;
    }

    public void setCmobile(String cmobile) {
        this.cmobile = cmobile;
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
}
