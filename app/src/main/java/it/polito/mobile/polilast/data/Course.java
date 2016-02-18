package it.polito.mobile.polilast.data;
import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import java.util.List;

import it.polito.mobile.polilast.data.MyApp;

/**
 * Created by shahboz on 9/23/15.
 */
@ParseClassName("Course")
public class Course extends ParseObject{
    public Course(){}
    //course id
    public void setID(String id){
        put(MyApp.COURSE_ID,id);
    }
    public String getID(){
        return getString(MyApp.COURSE_ID);
    }
    //name of the subject
    public void setName(String name){
        put(MyApp.SUBJECT_NAME,name);
    }
    public String getName(){
        return getString(MyApp.SUBJECT_NAME);
    }
    //department
    public void setDepartment(String department){
        put(MyApp.DEPARTMENT, department);
    }
    public String getDepartment(){
        return getString(MyApp.DEPARTMENT);
    }
    //list of majors where this subject is taught
    public void setMajor(String major){
        put(MyApp.MAJOR,major);
    }
    public String getMajor(){
        return getString(MyApp.MAJOR);
    }
    public void setProfessor(String professor){
        put(MyApp.PROFESSOR,professor);
    }
    public String getProfessor(){
        return getString(MyApp.PROFESSOR);
    }
    public void setRoom(String rooom){
        put(MyApp.ROOM,rooom);

    }
    public String getRoom(){
        return getString(MyApp.ROOM);
    }
    public void setStartTime(String startTime){
        put(MyApp.START_TIME,startTime);
    }
    public String getStartTime(){
        return getString(MyApp.START_TIME);
    }
    public void setFinishTime(String finishTime){
        put(MyApp.FINISH_TIME,finishTime);
    }
    public String getFinishTime(){
        return getString(MyApp.FINISH_TIME);
    }

    public ParseGeoPoint getLocation() {
        return getParseGeoPoint(MyApp.LOCATION);
    }

    public void setLocation(ParseGeoPoint value) {
        put(MyApp.LOCATION, value);
    }
}
