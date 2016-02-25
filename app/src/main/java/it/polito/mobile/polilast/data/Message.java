package it.polito.mobile.polilast.data;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by shahboz on 9/23/15.
 */
@ParseClassName("Message")
public class Message extends ParseObject{
    public Message(){

    }
    //set sender
    public void setSender(String username){
        put(MyApp.SENDER,username);
    }

    public String getSender(){
        return getString(MyApp.SENDER);
    }
    public void setReceiver(List<String> receiver){
        put(MyApp.RECEIVER,receiver);
    }
    public List<String> getReceiver(){
        return getList(MyApp.RECEIVER);
    }

    public void setDescription(String desc){
        put(MyApp.DESCRIPTION,desc);
    }

    public String getDescription(){
        return getString(MyApp.DESCRIPTION);
    }

    public void setGroup(String username){
        put(MyApp.GROUP,username);
    }

    public String getGroup(){
        return getString(MyApp.GROUP);
    }
    public void setType(String type){
        put(MyApp.TYPE,type);
    }

    public String getType(){
        return getString(MyApp.TYPE);
    }
}
