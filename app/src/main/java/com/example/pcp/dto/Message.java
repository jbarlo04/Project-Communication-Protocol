package com.example.pcp.dto;

public class Message {
    private String message;
    private String senderId;
    private String groupId;

    public Message(String message, String senderId, String groupId) {
        this.message = message;
        this.senderId = senderId;
        this.groupId = groupId;
    }

    public String getMessage() {
        return message;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
