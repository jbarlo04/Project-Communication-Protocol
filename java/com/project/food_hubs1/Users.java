package com.project.food_hubs1;

public class Users {

    private String name;
    private String bname;
    private String email;
    private String mobile;
    private String address;
    private String password;
    private String uType;
    private String city;

    public Users(String name, String bname, String email, String mobile, String address, String password, String uType, String city) {
        this.name = name;
        this.bname = bname;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.password = password;
        this.uType = uType;
        this.city = city;
    }

    public Users() {
    }



    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getuType() {
        return uType;
    }

    public void setuType(String uType) {
        this.uType = uType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
class  Ratings{
    Float rating;
    String message;

    public Ratings() {
    }

    public Ratings(Float rating, String message) {
        this.rating = rating;
        this.message = message;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
