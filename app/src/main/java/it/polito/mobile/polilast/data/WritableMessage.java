package it.polito.mobile.polilast.data;

/**
 * Created by shahboz on 24/02/2016
 * in context of PoliLast.
 */
public class WritableMessage {

    private String title;
    private String description;

    public WritableMessage(String t, String d){
        this.title = t;
        this.description = d;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
