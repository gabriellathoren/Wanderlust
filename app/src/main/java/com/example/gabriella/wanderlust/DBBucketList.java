package com.example.gabriella.wanderlust;

/**
 * Created by Gabriella on 2015-12-23.
 *
 * A model class that handles the table bucket_list
 */
public class DBBucketList {

    int list_ID;
    int travel_ID;
    String item;

    /* Constructors */
    public DBBucketList(){}

    public DBBucketList(int list_ID, int travel_ID, String item) {
        this.list_ID   = list_ID;
        this.travel_ID = travel_ID;
        this.item      = item;
    }

    /* Setters */
    public void setListID(int id) {
        this.list_ID = id;
    }

    public void setTravelID(int travel_ID){
        this.travel_ID = travel_ID;
    }

    public void setItem(String item) {
        this.item = item;
    }

    /* Getters */
    public int getListID() {
        return this.list_ID;
    }

    public int getTravelID(){
        return this.travel_ID;
    }

    public String getItem() {
        return this.item;
    }


}
