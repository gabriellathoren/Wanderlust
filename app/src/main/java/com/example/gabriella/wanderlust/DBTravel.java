package com.example.gabriella.wanderlust;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.Serializable;


/**
 * <h1>DBTravel</h1>
 *
 * A model class that handles the objects for the table travel in the database. This class contains
 * information about the user's coming travels.
 *
 * @author  Gabriella Thorén
 * @version 1
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


    /**
     *  Constructor without parameters
     */
    DBTravel(){}

    /**
     * Constructor with several parameters to create an DBTravel-object.
     *
     * @param title     the title of the travel
     * @param year      the year which the travel will occur
     * @param month     the month which the travel will occur
     * @param day       the day which the travel will occur
     * @param photo     the file path to the wallpaper
     */
    DBTravel(String title, int year, int month, int day, String photo){
        this.title       = title;
        this.year        = year;
        this.month       = month;
        this.day         = day;
        this.picturePath = photo;
    }

    /**
     * Constructor with several parameters to create a DBTravel-object.
     *
     * @param title     the title of the travel
     * @param year      the year which the travel will occur
     * @param month     the month which the travel will occur
     * @param day       the day which the travel will occur
     */
    DBTravel(String title, int year, int month, int day){
        this.title     = title;
        this.year      = year;
        this.month     = month;
        this.day       = day;
    }

    /**
     * Constructor with several parameters to create a DBTravel-object.
     *
     * @param id        the id of the travel
     * @param title     the title of the travel
     * @param year      the year which the travel will occur
     * @param month     the month which the travel will occur
     * @param day       the day which the travel will occur
     * @param photo     the file path to the wallpaper of the travel
     */
    DBTravel(int id, String title, int year, int month, int day, String photo){
        this.travelID    = id;
        this.title       = title;
        this.year        = year;
        this.month       = month;
        this.day         = day;
        this.picturePath = photo;
    }

    /**
     * Constructor with several parameters to create a DBTravel-object.
     *
     * @param id        the id of the travel
     * @param title     the title of the travel
     * @param year      the year which the travel will occur
     * @param month     the month which the travel will occur
     * @param day       the day which the travel will occur
     */
    DBTravel(int id, String title, int year, int month, int day){
        this.travelID  = id;
        this.title     = title;
        this.year      = year;
        this.month     = month;
        this.day       = day;
    }

    /**
     * Setter of travel id
     *
     * @param id    travel id
     */
    public void setTravelID(int id) {
        this.travelID = id;
    }

    /**
     * Setter of travel title.
     *
     * @param title     the title of the travel
     */

    public void setTitle(String title){
        this.title = title;
    }

    /**
     * Setter of the year which the travel will occur.
     *
     * @param year      the year which the travel will occur
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Setter of the month which the travel will occur.
     *
     * @param month     the month which the travel will occur.
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Setter of the day which the travel will occur.
     *
     * @param day       the day which the travel will occur
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Setter of the wallpaper of the travel.
     *
     * @param path      the file path to the wallpaper
     */
    public void setWallpaper(String path) {
        this.picturePath = path;
    }

    /**
     * Setter of the wallpaper of the travel.
     *
     * @param wallpaper     the wallpaper as a Bitmap
     */
    public void setWallpaper(Bitmap wallpaper) {
        this.wallpaper = wallpaper;
    }


    /** Getter of the travel id
     *
     *  @return int     returns the travel id
     */
    public int getTravelID() {
        return this.travelID;
    }

    /**
     * Getter of the title
     *
     * @return String   returns the travel title
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * Getter of the year which the travel will occur.
     *
     * @return int      returns the travel year
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Getter of the month which the travel will occur.
     *
     * @return int      returns the travel month
     */
    public int getMonth() {
        return this.month;
    }

    /**
     * Getters of the day which the travel will occur.
     *
     * @return int      returns the travel day
     */
    public int getDay() {
        return this.day;
    }

    /**
     * Getters of the file path to the wallpaper
     *
     * @return String   returns the path to the wallpaper
     */
    public String getPicturePath() {
        return this.picturePath;
    }

    /**
     * Getters to the travel wallpaper. This method reduces the size of the picture before it can
     * be used to reduce the memory allocation.
     *
     * @return Bitmap   returns the wallpaper as a Bitmap
     */
    public Bitmap getWallpaper() {
        Bitmap wallpaperBM;

        /* If file path is not null, that path must be decoded to a Bitmap */
        if(picturePath != null) {
            wallpaperBM = BitmapFactory.decodeFile(picturePath);
        }
        /* The image is already a Bitmap */
        else {
            wallpaperBM = this.wallpaper;
        }

        /* Set maxsize of image */
        final int maxSize = 800;

        /* Get width and height of wallpaper */
        int width  = wallpaperBM.getWidth();
        int height = wallpaperBM.getHeight();

        /* Set new width or height depending on if the picture is horizontal or vertical */
        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width  = maxSize;
            height = (int) (width / bitmapRatio);
        }
        else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        /* Set this.wallpaper to the reduced Bitmap image */
        this.wallpaper = Bitmap.createScaledBitmap(wallpaperBM, width, height, true);

        /* Return the reduced Bitmap image */
        return this.wallpaper;
    }

}
