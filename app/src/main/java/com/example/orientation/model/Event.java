package com.example.orientation.model;

public class Event {
    public String name;
    public String stime;
    public String etime;
    public String description;
    public String location;
    public String locurl;
    public String imgname;
    public String latitude;
    public String longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Event(String name, String stime, String etime, String description, String location, String locurl, String imgname, String latitude, String longitude) {
        this.name = name;
        this.stime = stime;
        this.etime = etime;
        this.description = description;
        this.location = location;
        this.locurl = locurl;
        this.imgname = imgname;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Event() {
    }

    public String getimgname() {
        return imgname;
    }

    public void setimgname(String imgname) {
        this.imgname = imgname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocurl() {
        return locurl;
    }

    public void setLocurl(String locurl) {
        this.locurl = locurl;
    }
}
