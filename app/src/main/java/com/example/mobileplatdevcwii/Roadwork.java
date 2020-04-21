// Iuri Insali S1504620

package com.example.mobileplatdevcwii;

import java.io.Serializable;
import java.util.Date;

public class Roadwork implements Serializable {

    private String title, description, lat, lon;
    public Date setStartDate;
    public Date setEndDate;
    public String startDate;
    public String endDate;

    public Roadwork(String title, String description, String lat, String lon)
    {
        this.title = title;
        this.description = description;
        this.lon = lon;
        this.lat = lat;

    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat){
        this.lat = lat;
    }

    public String getLon(){
        return lon;
    }

    public void setLon(String lon){
        this.lon = lon;
    }








}
