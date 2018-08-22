package com.example.omar.uktour11;

/**
 * Created by omar on 1/6/2018.
 */

public class Journey {

    private  String name, date, duration, location, id;

    public Journey(){}
    public Journey(String name, String date, String duration, String location , String id) {
        this.name = name;
        this.date = date;
        this.duration = duration;
        this.location = location;
        this.id= id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
