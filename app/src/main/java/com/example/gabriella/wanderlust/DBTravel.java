package com.example.gabriella.wanderlust;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES10;
import android.opengl.GLSurfaceView;
import android.util.Base64;
import android.util.Log;

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
    private String picturePath;

    /* For logging */
    private final static String LOG_TAG = StartPage.class.getSimpleName();

    /* Constructors */
    DBTravel(){}

    DBTravel(String title, int year, int month, int day, Bitmap photo){
        this.title     = title;
        this.year      = year;
        this.month     = month;
        this.day       = day;
        this.wallpaper = photo;
    }

    DBTravel(String title, int year, int month, int day, String photo){
        this.title       = title;
        this.year        = year;
        this.month       = month;
        this.day         = day;
        this.picturePath = photo;
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

    DBTravel(int id, String title, int year, int month, int day, String photo){
        this.travelID    = id;
        this.title       = title;
        this.year        = year;
        this.month       = month;
        this.day         = day;
        this.picturePath = photo;
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

    public void setWallpaper(String path) {
        this.picturePath = path;
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

    public String getPicturePath() {
        return this.picturePath;
    }

    public Bitmap getWallpaper() {
        Bitmap wallpaperBM;

        if(picturePath != null) {
            wallpaperBM = BitmapFactory.decodeFile(picturePath);
        }
        else {
            wallpaperBM = this.wallpaper;
        }

        final int maxSize = 3000;

        /* Get width and height of wallpaper */
        int width  = wallpaperBM.getWidth();
        int height = wallpaperBM.getHeight();

        /* Set new width or height depending on if the picture is horizontal or vertical */
        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width  = maxSize;
            height = (int) (width / bitmapRatio);
            Log.d(LOG_TAG, "width: " + width + " height: " + height);
        }
        else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
            Log.d(LOG_TAG, "width: " + width + " height: " + height);
        }

        this.wallpaper = Bitmap.createScaledBitmap(wallpaperBM, width, height, true);

        return this.wallpaper;
    }

    /* To store image in database the image must be saved as byte[], therefore the method
     * getWallpaperAsByte() converts the bitmap-image to byte[]
    public byte[] getWallpaperAsByte() {

        if (wallpaper == null) {
            return null;
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        wallpaper.compress(Bitmap.CompressFormat.JPEG, 0, outputStream);

        return outputStream.toByteArray();
    }

*/


    /* Returns a scaled Bitmap, which is needed to display a too big of an image in ImageView, the
     * maxsize is the possible maxsize of the image to be shown in the ImageView.

    public void setWallpaper(Bitmap wallpaper) {
        final int maxSize = 3000;

        /* Get width and height of wallpaper
        int width  = wallpaper.getWidth();
        int height = wallpaper.getHeight();

        /* Set new width or height depending on if the picture is horizontal or vertical
        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width  = maxSize;
            height = (int) (width / bitmapRatio);
            Log.d(LOG_TAG, "width: " + width + " height: " + height);
        }
        else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
            Log.d(LOG_TAG, "width: " + width + " height: " + height);
        }

        this.wallpaper = Bitmap.createScaledBitmap(wallpaper, width, height, true);
    }
*/


}
