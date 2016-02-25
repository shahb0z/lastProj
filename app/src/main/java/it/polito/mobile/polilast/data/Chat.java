package it.polito.mobile.polilast.data;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by shahboz on 25/02/2016
 * in context of PoliLast.
 */
@ParseClassName("Chat")
public class Chat extends ParseObject {
    public Chat() {

    }
    public void setUser1(String username){
        put("user1",username);
    }

    public String getUser1(){
        return getString("user1");
    }
    public void setUser2(String username){
        put("user2",username);
    }

    public String getUser2(){
        return getString("user2");
    }
}