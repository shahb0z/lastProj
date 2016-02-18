package it.polito.mobile.polilast.data;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by shahboz on 9/23/15.
 */
@ParseClassName("Job")
public class Job extends ParseObject{
    public Job(){

    }
    //set and get username of the user
    public void setUser(String username){
        put(MyApp.USER,username);
    }
    public String getUser(){
        return getString(MyApp.USER);
    }

    //title
    public void setTitle(String title){
        put(MyApp.TITLE,title);
    }

    public String getTitle(){
        return getString(MyApp.TITLE);
    }
    //description
    public void setDescription(String desc){
        put(MyApp.DESCRIPTION,desc);
    }

    public String getDescription(){
        return getString(MyApp.DESCRIPTION);
    }

    //qualification

    public void setQualification(String qual){
        put(MyApp.QUALIFICATION,qual);
    }

    public String getQualification(){
        return getString(MyApp.QUALIFICATION);
    }
    //Category
    public void setCategory(String cat){
        put(MyApp.CATEGORY,cat);
    }

    public String getCategory(){
        return getString(MyApp.CATEGORY);
    }

    //salary, if possible
    public void setSalary(int salary){
        put(MyApp.SALARY,salary);
    }
    public int getSalary(){
        return getInt(MyApp.SALARY);
    }
}
