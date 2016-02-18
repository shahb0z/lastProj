package it.polito.mobile.polilast.data;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by shahboz on 9/23/15.
 */
@ParseClassName("Notice")
public class Notice extends ParseObject {
    public Notice(){

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
    //Category
    public void setCategory(String cat){
        put(MyApp.CATEGORY,cat);
    }

    public String getCategory(){
        return getString(MyApp.CATEGORY);
    }

    //price, only for rent and sale objects
    public void setPrice(int price){
        put(MyApp.PRICE,price);
    }

    public int getPrice(){
        return getInt(MyApp.PRICE);
    }
    //Address, if needed
    public void setAddress(String address){
        put(MyApp.ADDRESS,address);
    }

    public String getAddress(){
        return getString(MyApp.ADDRESS);
    }





}
