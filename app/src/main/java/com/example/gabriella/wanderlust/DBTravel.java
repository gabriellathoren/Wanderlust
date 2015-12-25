package com.example.gabriella.wanderlust;

/**
 * Created by Gabriella on 2015-12-23.
 *
 * A model class that handles the table travel
 */
public class DBTravel {

    int travelID; /* Behövs den här? */
    String title;
    int year;
    int month;
    int day;
    int photo; // Vad ska det vara för datatyp här!?

    /* Constructors */
    DBTravel(){}

    DBTravel(String title, int year, int month, int day, int photo){
        this.title = title;
        this.year = year;
        this.month = month;
        this.day = day;
        this.photo = photo;
    }


    /* Setters */
    public void setTravelID(int id) {
        this.travelID = id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }


    /* Getters */
    public int getTravelID() {
        return this.travelID;
    }

    public String getTitle(){
        return this.title;
    }

    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return this.month;
    }

    public int getDay() {
        return this.day;
    }

    public int getWallpaper() {
        return this.photo;
    }

}
