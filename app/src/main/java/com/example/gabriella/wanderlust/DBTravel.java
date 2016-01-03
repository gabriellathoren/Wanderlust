package com.example.gabriella.wanderlust;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

/**
 * Created by Gabriella on 2015-12-23.
 *
 * A model class that handles the table travel
 */
public class DBTravel implements Serializable {

    private int travelID;
    private String title;
    private int year;
    private int month;
    private int day;
    private Bitmap wallpaper;

    /* Constructors */
    DBTravel(){}

    DBTravel(String title, int year, int month, int day, Bitmap photo){
        this.title     = title;
        this.year      = year;
        this.month     = month;
        this.day       = day;
        this.wallpaper = photo;
    }
    DBTravel(String title, int year, int month, int day){
        this.title     = title;
        this.year      = year;
        this.month     = month;
        this.day       = day;
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

    public void setWallpaper(Bitmap wallpaper) {
        this.wallpaper = wallpaper;
    }

    /* When the image is stored in database */
    public void setWallpaperFromDatabase(byte[] wallpaper) {
        this.wallpaper = BitmapFactory.decodeByteArray(wallpaper, 0, wallpaper.length);
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

    public Bitmap getWallpaper() {
        return this.wallpaper;
    }

    /* To store image in database the image must be saved as byte[], therefore the method
     * getWallpaperAsByte() converts the bitmap-image to byte[] */
    public byte[] getWallpaperAsByte() {

        if (wallpaper == null) {
            return null;
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        wallpaper.compress(Bitmap.CompressFormat.PNG, 0, outputStream);

        return outputStream.toByteArray();
    }

}
