package com.project.food_hubs1;

public class Rating {
    String oid;
    String rating;
    String message;
    String user;
    String carter;


    public Rating(String oid, String rating, String message, String user,String carter) {
        this.oid = oid;
        this.rating = rating;
        this.message = message;
        this.user = user;
        this.carter = carter;
    }

    public Rating() {
    }

    public String getCarter() {
        return carter;
    }

    public void setCarter(String carter) {
        this.carter = carter;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
