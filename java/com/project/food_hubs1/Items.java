package com.project.food_hubs1;

public class Items {
    String key;
    String name;
    String price;
    String desc;
    String img;
    String cmobile;
    String mobile;
    String oid;
    String bname;
    String status;


    public Items(String key, String name, String price, String desc, String img, String cmobile, String mobile, String oid, String bname, String status) {
        this.key = key;
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.img = img;
        this.cmobile = cmobile;
        this.mobile = mobile;
        this.oid = oid;
        this.bname = bname;
        this.status = status;
    }



    public Items() {

    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
