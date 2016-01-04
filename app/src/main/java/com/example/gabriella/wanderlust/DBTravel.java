package com.example.gabriella.wanderlust;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES10;
import android.opengl.GLSurfaceView;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import javax.microedition.khronos.opengles.GL10;

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

    DBTravel(int id, String title, int year, int month, int day, Bitmap photo){
        this.travelID  = id;
        this.title     = title;
        this.year      = year;
        this.month     = month;
        this.day       = day;
        this.wallpaper = photo;
    }
    DBTravel(int id, String title, int year, int month, int day){
        this.travelID  = id;
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

    /* Returns a scaled Bitmap, which is needed to display a too big of an image in ImageView, the
     * maxsize is the possible maxsize of the image to be shown in the ImageView. */
    public Bitmap getWallpaper() {
        /* Get the maximum texture size of the device and convert the int[1] to and int */
        int[] maxTextureSize = new int[1];
        GLES10.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE, maxTextureSize, 0);
        StringBuilder convert = new StringBuilder();
        convert.append(maxTextureSize);
        int maxSize = Integer.parseInt(convert.toString());

        /* Get width and height of wallpaper */
        int width  = wallpaper.getWidth();
        int height = wallpaper.getHeight();

        /* Set new width or height depending on if the picture is horizontal or vertical */
        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width  = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width  = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(wallpaper, width, height, true);
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
