package it.polito.mobile.polilast.data;

import com.parse.ParseClassName;
import com.parse.ParseUser;

/**
 * Created by shahboz on 9/21/15.
 */
@ParseClassName("_User")
public class MyUser extends ParseUser {
    //user type is used for distinguishing between users
    public String getType() {
        return getString(MyApp.TYPE);
    }
    //3 types os users: student, company, professor
    //please use 3 types in MyApp class (STUDENT_TYPE, COMPANY_TYPE, PROFESSOR_TYPE)
    //e.g setType(MyApp.STUDENT_TYPE)
    public void setType(String type) {
        this.put(MyApp.TYPE, type);
    }

    //major
    public void setMajor(String major){
        put(MyApp.MAJOR, major);
    }
    public String getMajor(){
        return getString(MyApp.MAJOR);
    }

    //subject
    public void setSubject(String major){
        put(MyApp.SUBJECT_NAME, major);
    }
    public String getSubject(){
        return getString(MyApp.SUBJECT_NAME);
    }


    public void setName(String name) {
        this.put(MyApp.FULLNAME, name);
    }
    public String getName() {
        return getString(MyApp.FULLNAME);
    }
}
