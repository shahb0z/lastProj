package it.polito.mobile.polilast.ui;
import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import java.util.List;

import it.polito.mobile.polilast.data.MyApp;

/**
 * Created by shahboz on 9/23/15.
 */

public class Subject{


    private String prof;
    private String name;
    private String startTime;
    private String finishTime;
    private String day;
    private String room;
    private LatLng location;
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }


    public Subject(){}

    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

}
