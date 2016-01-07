package com.example.gabriella.wanderlust;

/**
 * <h1>SQLiteHelper</h1>
 *
 * This class handles the database.
 *
 * There are four tables in the database:
 * <ol>
 *     <li>user which represents the users of the application</li>
 *     <li>country which represents the countries of the world</li>
 *     <li>travel which represents the user's travels</li>
 *     <li>user_country which represents the places the user has traveled to</li>
 * </ol>
 *
 * @author  Gabriella Thorén
 * @version 1
 *
 *
 * Wanderlust - the application that keeps track of your travels.
 * Copyright (C) 2016 Gabriella Thorén
 * <p>
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class SQLiteHelper extends SQLiteOpenHelper {

    /* Logcat tag */
    private static final String LOG = SQLiteHelper.class.getName();

    /* Database version */
    private static final int DATABASE_VERSION = 4;

    /* Database name */
    private static final String DATABASE_NAME = "database";

    /* int to determine which wallpaper color to use when the user has not chosen one herself/himself */
    private int wallpaperColor = 1;

    /* Table names */
    private static final String TABLE_USER         = "user";
    private static final String TABLE_COUNTRY      = "country";
    private static final String TABLE_TRAVEL       = "travel";
    private static final String TABLE_USER_COUNTRY = "user_country";

    /* User table - column names */
    private static final String KEY_USER_ID         = "user_ID";
    private static final String KEY_USER_USERNAME   = "username";
    private static final String KEY_USER_PASSWORD   = "password";
    private static final String KEY_USER_FIRST_NAME = "first_name";
    private static final String KEY_USER_LAST_NAME  = "last_name";

    /* Country table - column names */
    private static final String KEY_COUNTRY_NAME      = "country_name";
    private static final String KEY_COUNTRY_CONTINENT = "continent";

    /* Travel table - column names */
    private static final String KEY_TRAVEL_ID        = "travel_ID";
    private static final String KEY_TRAVEL_TITLE     = "title";
    private static final String KEY_TRAVEL_YEAR      = "year";
    private static final String KEY_TRAVEL_MONTH     = "month";
    private static final String KEY_TRAVEL_DAY       = "day";
    private static final String KEY_TRAVEL_WALLPAPER = "wallpaper";


    /* Table create statements */
    private static final String CREATE_TABLE_USER = "create table if not exists " + TABLE_USER + "("
            + KEY_USER_ID         + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_USER_USERNAME   + " TEXT NOT NULL UNIQUE, "
            + KEY_USER_PASSWORD   + " TEXT NOT NULL, "
            + KEY_USER_FIRST_NAME + " TEXT, "
            + KEY_USER_LAST_NAME  + " TEXT)";

    private static final String CREATE_TABLE_COUNTRY = "create table if not exists " + TABLE_COUNTRY + "("
            + KEY_COUNTRY_NAME      + " TEXT PRIMARY KEY, "
            + KEY_COUNTRY_CONTINENT + " TEXT)";

    private static final String CREATE_TABLE_TRAVEL = "create table if not exists " + TABLE_TRAVEL + "("
            + KEY_TRAVEL_ID        + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_TRAVEL_TITLE     + " TEXT, "
            + KEY_TRAVEL_YEAR      + " INTEGER NOT NULL, "
            + KEY_TRAVEL_MONTH     + " INTEGER NOT NULL, "
            + KEY_TRAVEL_DAY       + " INTEGER NOT NULL, "
            + KEY_TRAVEL_WALLPAPER + " STRING, "
            + KEY_USER_ID          + " INTEGER NOT NULL, "
            + "FOREIGN KEY (" + KEY_USER_ID + ") REFERENCES " + TABLE_USER + "(" + KEY_USER_ID + "))";

    private static final String CREATE_TABLE_USER_COUNTRY = "create table  if not exists " + TABLE_USER_COUNTRY + "("
            + KEY_USER_ID      + " INTEGER, "
            + KEY_COUNTRY_NAME + " TEXT, "
            + "PRIMARY KEY ("  + KEY_USER_ID  + "," + KEY_COUNTRY_NAME + ") "
            + "FOREIGN KEY ("  + KEY_USER_ID      + ") REFERENCES " + TABLE_USER     + "(" + KEY_USER_ID      + "), "
            + "FOREIGN KEY ("  + KEY_COUNTRY_NAME + ") REFERENCES " + TABLE_COUNTRY  + "(" + KEY_COUNTRY_NAME + "))";



    /**
     * Constructor for the SQLiteHelper.
     *
     * @param context   the context of the relevant activity
     */
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * Creates the database tables
     *
     * @param db    the database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        /* Create tables */
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_COUNTRY);
        db.execSQL(CREATE_TABLE_TRAVEL);
        db.execSQL(CREATE_TABLE_USER_COUNTRY);

    }

    /**
     * Updates the tables in the database if the database changes
     *
     * @param db            the database
     * @param oldVersion    the old version of the database
     * @param newVersion    the new version of the database
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        /* Drop older tables while upgrading */
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_COUNTRY);
        db.execSQL(CREATE_TABLE_TRAVEL);
        db.execSQL(CREATE_TABLE_USER_COUNTRY);

        /* Create new tables */
        onCreate(db);
    }


    /* METHODS FOR ACCESSING THE TABLE USER_TRAVEL */

    /**
     *  Method which returns the number of countries that the user has traveled to.
     *
     *  @param  user     the current user
     *  @return int      the number of countries which the user has traveled to
     */
    public int getTotalVisitedCountries(DBUser user) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT count(*) FROM " + TABLE_USER_COUNTRY + " "
                           + "WHERE " + KEY_USER_ID + " = " + user.getUserID();

        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
        }

        int total = c.getInt(0);

        db.close();
        c.close();

        return total;

    }

    /**
     * Method which returns the whole list of the countries which the user has visited
     *
     * @param user              the user
     * @return List<DBCountry>  a list of countries where the user has been
     * @see    DBCountry
     */
    public List<DBCountry> getVisitedCountries(DBUser user) {

        SQLiteDatabase db = this.getReadableDatabase();
        List<DBCountry> countries = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_USER_COUNTRY + " "
                           + "WHERE " + KEY_USER_ID + " = " + user.getUserID();

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        /* Looping through all rows and adding to list */
        if (c.moveToFirst()) {
            do {
                DBCountry country = new DBCountry();
                country.setCountry (c.getString(c.getColumnIndex(KEY_COUNTRY_NAME)));
                country.setSelected(true);
                countries.add(country);
            }
            while (c.moveToNext());
        }

        c.close();
        db.close();

        return countries;
    }

    /**
     *  Creates a row in the table user_country which shows the countries where the user has been by
     *  adding the user id and the country name to the table.
     *
     *  @param user     the user
     *  @param country  the country where the user has been
     */
    public void createVisitedCountry(DBUser user, DBCountry country) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID,      user.getUserID());
        values.put(KEY_COUNTRY_NAME, country.getCountry());

        /* Insert row */
        db.insert(TABLE_USER_COUNTRY, null, values);
        db.close();
    }

    /**
     * Deletes a row from the table user_country, which shows the countries where the user has been,
     * by comparing the user id and the country name with the rows in the table.
     *
     * @param user    the user
     * @param country the country
     */
    public void deleteVisitedCountry(DBUser user, DBCountry country) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER_COUNTRY, KEY_USER_ID + " = ? AND " + KEY_COUNTRY_NAME + " = ?",
                new String[]{String.valueOf(user.getUserID()), String.valueOf(country.getCountry())});
        db.close();
    }

    /**
     * Deletes all the users visited countries, which is needed if the user is deleted from the
     * database. The method finds the rows which contains the user id.
     *
     * @param user  the relevant user
     */
    public void deleteAllVisitedCountries(DBUser user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER_COUNTRY, KEY_USER_ID + " = ?", new String[]{String.valueOf(user.getUserID())});
        db.close();
    }


    /* METHODS FOR ACCESSING THE TABLE USER */

    /**
     * Creates a user by adding information from an DBUser object to the database.
     *
     * @param user
     */
    public void createUser(DBUser user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_USERNAME,   user.getUsername());
        values.put(KEY_USER_PASSWORD,   user.getPassword());
        values.put(KEY_USER_FIRST_NAME, user.getFirstName());
        values.put(KEY_USER_LAST_NAME,  user.getLastName());

        /* Insert row */
        db.insert(TABLE_USER, null, values);
        db.close();
    }


    /**
     * Deletes a user by finding the row with the relevant user id. It is not enough to delete
     * the user, therefore the associated travels and visited countries are deleted from the
     * database.
     *
     * @param user  the relevant user
     * @see   #deleteAllTravels(DBUser)
     * @see   #deleteAllVisitedCountries(DBUser)
     */
    public void deleteUser(DBUser user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_USER_ID + " = ?", new String[]{String.valueOf(user.getUserID())});
        db.close();

        /* Delete everything in database associated with the user */
        deleteAllTravels(user);
        deleteAllVisitedCountries(user);
    }


    /**
     * Updates user information
     *
     * @param user      the relevant user
     */
    public void updateUser(DBUser user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_USERNAME,   user.getUsername());
        values.put(KEY_USER_PASSWORD,   user.getPassword());
        values.put(KEY_USER_FIRST_NAME, user.getFirstName());
        values.put(KEY_USER_LAST_NAME,  user.getLastName());

        db.update(TABLE_USER, values, KEY_USER_ID + " = ?", new String[]{String.valueOf(user.getUserID())});

        db.close();

    }

    /**
     * Returns user information by giving username as a parameter
     *
     * @param username      the user's username
     * @see   DBUser
     */
    public DBUser getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USER + " "
                           + "WHERE " + KEY_USER_USERNAME + " = '" + username + "'";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
        }

        DBUser u = new DBUser();
        u.setUserID   (c.getInt   (c.getColumnIndex(KEY_USER_ID)));
        u.setUsername (c.getString(c.getColumnIndex(KEY_USER_USERNAME)));
        u.setPassword (c.getString(c.getColumnIndex(KEY_USER_PASSWORD)));
        u.setFirstName(c.getString(c.getColumnIndex(KEY_USER_FIRST_NAME)));
        u.setLastName (c.getString(c.getColumnIndex(KEY_USER_LAST_NAME)));

        c.close();
        db.close();

        return u;
    }

    /**
     * Check if user exists by giving information about the user's username and the user's
     * password.
     *
     * @param  username  the user's username
     * @param  password  the user's password
     * @return Boolean   <code>true</code> if the user exists
     *                   <code>false</code> if the user does not exists
     */
    public Boolean ifUserExists(String username, String password) {

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USER + " "
                           + "WHERE " + KEY_USER_USERNAME  + " = '" + username + "' "
                           + "AND "   + KEY_USER_PASSWORD  + " = '" + password + "'";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        /* Check if user does not exist */
        if (c.getCount() <= 0) {
            c.close();
            db.close();
            return false;
        }

        c.close();
        db.close();

        /* User exists */
        return true;
    }

    /**
     *  Check if user exists by giving information about the user's username.
     *
     *  @param  username     the user to control
     *  @return Boolean      <code>true</code> if the user exists
     *                       <code>false</code> if the user does not exists
     */
    public Boolean ifUserExists(String username) {

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USER + " "
                           + "WHERE " + KEY_USER_USERNAME  + " = '" + username + "' ";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        /* Check if user does not exist */
        if (c.getCount() <= 0) {
            c.close();
            db.close();
            return false;
        }

        c.close();
        db.close();

        /* User exists */
        return true;
    }


    /* METHODS FOR ACCESSING THE TABLE TRAVEL */

    /**
     *  Creates a row in the travel table with the given information about the travel and which
     *  user that has created it.
     *
     *  @param user     the user which created the travel
     *  @param travel   the new travel
     */
    public void createTravel(DBTravel travel, DBUser user) {
        SQLiteDatabase db = this.getWritableDatabase();

        /* Save the data in ContentValues to store it in database */
        ContentValues values = new ContentValues();
        values.put(KEY_TRAVEL_TITLE,     travel.getTitle());
        values.put(KEY_TRAVEL_YEAR,      travel.getYear());
        values.put(KEY_TRAVEL_MONTH,     travel.getMonth());
        values.put(KEY_TRAVEL_DAY,       travel.getDay());
        values.put(KEY_TRAVEL_WALLPAPER, travel.getPicturePath());
        values.put(KEY_USER_ID,          user.getUserID());

        /* Insert row to the table user*/
        db.insert(TABLE_TRAVEL, null, values);
        db.close();
    }

    /**
     *  Deletes the value of the stored wallpaper with the given information about the travel which
     *  has the wallpaper.
     *
     *  @param travel   the travel which contains the wallpaper
     */
    public void deleteWallpaper(DBTravel travel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.putNull(KEY_TRAVEL_WALLPAPER);

        db.update(TABLE_TRAVEL, values, KEY_TRAVEL_ID + " = ?",
                new String[]{String.valueOf(travel.getTravelID())});
    }


    /**
     * Deletes a row from the travel table (a travel).
     *
     * @param travel    the travel which is going to be deleted
     */
    public void deleteTravel(DBTravel travel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRAVEL, KEY_TRAVEL_ID + " = ?", new String[]{String.valueOf(travel.getTravelID())});
        db.close();
    }

    /**
     * Deletes all travels which a user has created.
     *
     * @param user  the user of the travels which is going to be deleted.
     */
    public void deleteAllTravels(DBUser user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRAVEL, KEY_USER_ID + " = ?", new String[]{String.valueOf(user.getUserID())});
        db.close();
    }

    /**
     * Updates travel information.
     *
     * @param travel    the travel with the new information, but still contains the old travel id
     */
    public void updateTravel(DBTravel travel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TRAVEL_TITLE,     travel.getTitle());
        values.put(KEY_TRAVEL_YEAR,      travel.getYear());
        values.put(KEY_TRAVEL_MONTH,     travel.getMonth());
        values.put(KEY_TRAVEL_DAY, travel.getDay());
        values.put(KEY_TRAVEL_WALLPAPER, travel.getPicturePath());

        db.update(TABLE_TRAVEL, values, KEY_TRAVEL_ID + " = " + travel.getTravelID(), null);

        db.close();
    }

    /**
     * Lists all the user's travels. If the user has not set an own wallpaper, this method gives the
     * list object a default one.
     *
     * @param     user      the relevant user
     * @param     context   the activity contaxt
     * @see       DBUser
     * @exception Exception
     *
     */
    public List<DBTravel> getTravels(DBUser user, Context context) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<DBTravel> travels = new ArrayList<>();

        try {
            String selectQuery = ("SELECT * FROM " + TABLE_TRAVEL + "," + TABLE_USER + " "
                               +  "WHERE "    + TABLE_TRAVEL + "." + KEY_USER_ID + " = " + TABLE_USER + "." + KEY_USER_ID + " "
                               +  "AND "      + TABLE_USER   + "." + KEY_USER_USERNAME + " = '" + user.getUsername() + "' "
                               +  "ORDER BY " + TABLE_TRAVEL + "." + KEY_TRAVEL_YEAR + " ASC, " + TABLE_TRAVEL + "." + KEY_TRAVEL_MONTH + " ASC, " + TABLE_TRAVEL + "." + KEY_TRAVEL_DAY + " ASC");

            Log.e(LOG, selectQuery);

            Cursor c = db.rawQuery(selectQuery, null);

            /* Looping through all rows and adding to list */
            if (c.moveToFirst()) {
                do {
                    DBTravel t = new DBTravel();
                    t.setTravelID(c.getInt   (c.getColumnIndex(KEY_TRAVEL_ID)));
                    t.setTitle   (c.getString(c.getColumnIndex(KEY_TRAVEL_TITLE)));
                    t.setYear    (c.getInt   (c.getColumnIndex(KEY_TRAVEL_YEAR)));
                    t.setMonth   (c.getInt   (c.getColumnIndex(KEY_TRAVEL_MONTH)));
                    t.setDay     (c.getInt   (c.getColumnIndex(KEY_TRAVEL_DAY)));


                    /* Control if the user choose own image as a wallpaper */
                    String path = c.getString(c.getColumnIndex(KEY_TRAVEL_WALLPAPER));
                    if (path != null) {
                        t.setWallpaper(path);
                    }
                    /* If not, the travel gets an default background */
                    else {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inScaled = false;

                        /*
                         * There is three different default wallpapers which is alternated. The
                         * switch statement checks the state of the variable wallpaperColor to see
                         * which color the previous list object had. By doing that the first object
                         * gets the backg1 as a wallpaper, the second gets backg2, the third one gets
                         * backg3, the fourth backg1 etc.
                         */
                        switch (wallpaperColor) {

                            case 1:
                                Bitmap color = BitmapFactory.decodeResource(context.getResources(), R.drawable.backg1, options);
                                t.setWallpaper(color);
                                wallpaperColor++;
                                break;

                            case 2:
                                color = BitmapFactory.decodeResource(context.getResources(), R.drawable.backg2, options);
                                t.setWallpaper(color);
                                wallpaperColor++;
                                break;

                            case 3:
                                color = BitmapFactory.decodeResource(context.getResources(), R.drawable.backg3, options);
                                t.setWallpaper(color);
                                wallpaperColor = 1;
                                break;
                        }
                    }
                    travels.add(t);
                }
                while (c.moveToNext());
            }

        c.close();
        db.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return travels;

    }


    /**
     * Get data from database about a specific travel.
     *
     * @param travelID  the travelID of the wanted travel
     * @param user      the user which created the travel
     */
    public DBTravel getTravel(int travelID, DBUser user) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = ("SELECT * FROM " + TABLE_TRAVEL + "," + TABLE_USER + " "
                           +  "WHERE "    + TABLE_TRAVEL  + "." + KEY_USER_ID       + " = "    + TABLE_USER + "." + KEY_USER_ID + " "
                           +  "AND "      + TABLE_USER    + "." + KEY_USER_USERNAME + " = '"   + user.getUsername() + "' "
                           +  "AND "      + TABLE_TRAVEL  + "." + KEY_TRAVEL_ID     + " = "    + travelID     + " "
                           +  "ORDER BY " + TABLE_TRAVEL  + "." + KEY_TRAVEL_YEAR   + " ASC, " + TABLE_TRAVEL + "." + KEY_TRAVEL_MONTH + " ASC, " + TABLE_TRAVEL + "." + KEY_TRAVEL_DAY + " ASC");

        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
        }

        /* Get data from the database an create a DBTravel object. */
        DBTravel t = new DBTravel();
        t.setTravelID(c.getInt   (c.getColumnIndex(KEY_TRAVEL_ID)));
        t.setTitle   (c.getString(c.getColumnIndex(KEY_TRAVEL_TITLE)));
        t.setYear    (c.getInt   (c.getColumnIndex(KEY_TRAVEL_YEAR)));
        t.setMonth   (c.getInt   (c.getColumnIndex(KEY_TRAVEL_MONTH)));
        t.setDay     (c.getInt   (c.getColumnIndex(KEY_TRAVEL_DAY)));

        /*
         * Control if the user choose own image as a wallpaper, if it has, the wallpaper of the
         * DBTravel object till be set. Otherwice the object do not get a wallpaper.
         */
        String path = c.getString(c.getColumnIndex(KEY_TRAVEL_WALLPAPER));
        if (path != null) {
            t.setWallpaper(path);
        }

        c.close();
        db.close();

        return t;
    }



    /* METHODS FOR ACCESSING THE TABLE COUNTRY */

    /**
     * Creates a list of all countries in Africa.
     *
     * @return List<DBCountry>
     * @see    DBCountry
     */
    public List<DBCountry> getAllCountriesAfrica() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<DBCountry> countries = new ArrayList<>();

        String selectQuery = ("SELECT "   + KEY_COUNTRY_NAME      + " "
                           +  "FROM "     + TABLE_COUNTRY         + " "
                           +  "WHERE "    + KEY_COUNTRY_CONTINENT + "= 'Africa' "
                           +  "ORDER BY " + KEY_COUNTRY_NAME      + " ASC");

        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        /* Looping through all rows and adding to list */
        if (c.moveToFirst()) {
            do {
                DBCountry country = new DBCountry();
                country.setCountry(c.getString(c.getColumnIndex(KEY_COUNTRY_NAME)));
                countries.add(country);
            }
            while (c.moveToNext());
        }

        c.close();
        db.close();

        return countries;
    }

    /**
     *  Creates a list of all countries in Asia
     *
     *  @return List<DBCountry>
     *  @see    DBCountry
     */
    public List<DBCountry> getAllCountriesAsia() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<DBCountry> countries = new ArrayList<>();

        String selectQuery = ("SELECT "   + KEY_COUNTRY_NAME      + " "
                           +  "FROM "     + TABLE_COUNTRY         + " "
                           +  "WHERE "    + KEY_COUNTRY_CONTINENT + "= 'Asia' "
                           +  "ORDER BY " + KEY_COUNTRY_NAME      + " ASC");

        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        /* Looping through all rows and adding it to list */
        if (c.moveToFirst()) {
            do {
                DBCountry country = new DBCountry();
                country.setCountry(c.getString(c.getColumnIndex(KEY_COUNTRY_NAME)));
                countries.add(country);
            }
            while (c.moveToNext());
        }

        c.close();
        db.close();
        return countries;
    }

    /**
     * Creates a list of all countries in Europe.
     *
     * @return List<DBCountry>
     * @see    DBCountry
     */
    public List<DBCountry> getAllCountriesEurope() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<DBCountry> countries = new ArrayList<>();

        String selectQuery = ("SELECT "   + KEY_COUNTRY_NAME      + " "
                           +  "FROM "     + TABLE_COUNTRY         + " "
                           +  "WHERE "    + KEY_COUNTRY_CONTINENT + "= 'Europe' "
                           +  "ORDER BY " + KEY_COUNTRY_NAME      + " ASC");

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        /* Looping through all rows and adding to list */
        if (c.moveToFirst()) {
            do {
                DBCountry country = new DBCountry();
                country.setCountry(c.getString(c.getColumnIndex(KEY_COUNTRY_NAME)));
                countries.add(country);
            }
            while (c.moveToNext());
        }

        c.close();
        db.close();
        return countries;
    }

    /**
     * Creates a list of all countries in North America.
     *
     * @return List<DBCountry>
     * @see    DBCountry
     */
    public List<DBCountry> getAllCountriesNorthAmerica() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<DBCountry> countries = new ArrayList<>();

        String selectQuery = ("SELECT "   + KEY_COUNTRY_NAME      + " "
                           +  "FROM "     + TABLE_COUNTRY         + " "
                           +  "WHERE "    + KEY_COUNTRY_CONTINENT + "= 'North America' "
                           +  "ORDER BY " + KEY_COUNTRY_NAME      + " ASC");

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        /* Looping through all rows and adding to list */
        if (c.moveToFirst()) {
            do {
                DBCountry country = new DBCountry();
                country.setCountry(c.getString(c.getColumnIndex(KEY_COUNTRY_NAME)));
                countries.add(country);
            }
            while (c.moveToNext());
        }

        c.close();
        db.close();
        return countries;
    }

    /**
     * Cretes a list of all countries in Oceania.
     *
     * @return List<DBCountry>
     * @see    DBCountry
     */
    public List<DBCountry> getAllCountriesOceania() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<DBCountry> countries = new ArrayList<>();

        String selectQuery = ("SELECT "   + KEY_COUNTRY_NAME      + " "
                           +  "FROM "     + TABLE_COUNTRY         + " "
                           +  "WHERE "    + KEY_COUNTRY_CONTINENT + "= 'Oceania' "
                           +  "ORDER BY " + KEY_COUNTRY_NAME      + " ASC");

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        /* Looping through all rows and adding to list */
        if (c.moveToFirst()) {
            do {
                DBCountry country = new DBCountry();
                country.setCountry(c.getString(c.getColumnIndex(KEY_COUNTRY_NAME)));
                countries.add(country);
            }
            while (c.moveToNext());
        }

        c.close();
        db.close();
        return countries;
    }

    /**
     * Creates a list of all countries in South America.
     *
     * @return List<DBCountry>
     * @see    DBCountry
     */
    public List<DBCountry> getAllCountriesSouthAmerica() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<DBCountry> countries = new ArrayList<>();

        String selectQuery = ("SELECT "   + KEY_COUNTRY_NAME      + " "
                           +  "FROM "     + TABLE_COUNTRY         + " "
                           +  "WHERE "    + KEY_COUNTRY_CONTINENT + "= 'South America' "
                           +  "ORDER BY " + KEY_COUNTRY_NAME      + " ASC");

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        /* Looping through all rows and adding to list */
        if (c.moveToFirst()) {
            do {
                DBCountry country = new DBCountry();
                country.setCountry(c.getString(c.getColumnIndex(KEY_COUNTRY_NAME)));
                countries.add(country);
            }
            while (c.moveToNext());
        }

        c.close();
        db.close();
        return countries;
    }

    /**
     * Creates a row in the table country.
     *
     * @param     country   the new country
     * @exception Exception
     */
    public void createCountry(DBCountry country) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_COUNTRY_NAME, country.getCountry());
            values.put(KEY_COUNTRY_CONTINENT, country.getContinent());

            /*
             * Insert row with constraint so it only stores the countries if it does not already
             * exists in database. Otherwice the application must call on these method only once. In
             * this way the application can call on these method every time.
             */
            db.insertWithOnConflict(TABLE_COUNTRY, null, values, SQLiteDatabase.CONFLICT_IGNORE);

            /* Close database */
            db.close();
        }
        catch (Exception e) {
            e.getStackTrace();
        }
    }


    /**
     *  Creates DBCountry objects of all countries in the world and calls on the method
     *  createCountry(DBCountry) which stores the information in the database.
     *
     *  @see DBCountry
     *  @see #createCountry(DBCountry)
     */
    public void setCountries() {
        DBCountry country = new DBCountry("Algeria", "Africa");
        createCountry(country);
        country = new DBCountry("Angola", "Africa");
        createCountry(country);
        country = new DBCountry("Benin", "Africa");
        createCountry(country);
        country = new DBCountry("Botswana", "Africa");
        createCountry(country);
        country = new DBCountry("Burkina", "Africa");
        createCountry(country);
        country = new DBCountry("Burundi", "Africa");
        createCountry(country);
        country = new DBCountry("Cameroon", "Africa");
        createCountry(country);
        country = new DBCountry("Cape Verde", "Africa");
        createCountry(country);
        country = new DBCountry("Central African Republic", "Africa");
        createCountry(country);
        country = new DBCountry("Chad", "Africa");
        createCountry(country);
        country = new DBCountry("Comoros", "Africa");
        createCountry(country);
        country = new DBCountry("Congo", "Africa");
        createCountry(country);
        country = new DBCountry("Democratic Republic of Congo", "Africa");
        createCountry(country);
        country = new DBCountry("Djibouti", "Africa");
        createCountry(country);
        country = new DBCountry("Egypt", "Africa");
        createCountry(country);
        country = new DBCountry("Equatorial Guinea", "Africa");
        createCountry(country);
        country = new DBCountry("Eritrea", "Africa");
        createCountry(country);
        country = new DBCountry("Ethiopia", "Africa");
        createCountry(country);
        country = new DBCountry("Gabon", "Africa");
        createCountry(country);
        country = new DBCountry("Gambia", "Africa");
        createCountry(country);
        country = new DBCountry("Ghana", "Africa");
        createCountry(country);
        country = new DBCountry("Guinea", "Africa");
        createCountry(country);
        country = new DBCountry("Guinea-Bissau", "Africa");
        createCountry(country);
        country = new DBCountry("Ivory Coast", "Africa");
        createCountry(country);
        country = new DBCountry("Kenya", "Africa");
        createCountry(country);
        country = new DBCountry("Lesotho", "Africa");
        createCountry(country);
        country = new DBCountry("Liberia", "Africa");
        createCountry(country);
        country = new DBCountry("Libya", "Africa");
        createCountry(country);
        country = new DBCountry("Madagascar", "Africa");
        createCountry(country);
        country = new DBCountry("Malawi", "Africa");
        createCountry(country);
        country = new DBCountry("Mali", "Africa");
        createCountry(country);
        country = new DBCountry("Mauritania", "Africa");
        createCountry(country);
        country = new DBCountry("Mauritius", "Africa");
        createCountry(country);
        country = new DBCountry("Morocco", "Africa");
        createCountry(country);
        country = new DBCountry("Mozambique", "Africa");
        createCountry(country);
        country = new DBCountry("Namibia", "Africa");
        createCountry(country);
        country = new DBCountry("Niger", "Africa");
        createCountry(country);
        country = new DBCountry("Nigeria", "Africa");
        createCountry(country);
        country = new DBCountry("Rwanda", "Africa");
        createCountry(country);
        country = new DBCountry("Sao Tome and Principe", "Africa");
        createCountry(country);
        country = new DBCountry("Senegal", "Africa");
        createCountry(country);
        country = new DBCountry("Seychelles", "Africa");
        createCountry(country);
        country = new DBCountry("Sierra Leone", "Africa");
        createCountry(country);
        country = new DBCountry("Somalia", "Africa");
        createCountry(country);
        country = new DBCountry("South Africa", "Africa");
        createCountry(country);
        country = new DBCountry("South Sudan", "Africa");
        createCountry(country);
        country = new DBCountry("Sudan", "Africa");
        createCountry(country);
        country = new DBCountry("Swaziland", "Africa");
        createCountry(country);
        country = new DBCountry("Tanzania", "Africa");
        createCountry(country);
        country = new DBCountry("Togo", "Africa");
        createCountry(country);
        country = new DBCountry("Tunisia", "Africa");
        createCountry(country);
        country = new DBCountry("Uganda", "Africa");
        createCountry(country);
        country = new DBCountry("Zambia", "Africa");
        createCountry(country);
        country = new DBCountry("Zimbabwe", "Africa");
        createCountry(country);
        country = new DBCountry("Afghanistan", "Asia");
        createCountry(country);
        country = new DBCountry("Bahrain", "Asia");
        createCountry(country);
        country = new DBCountry("Bangladesh", "Asia");
        createCountry(country);
        country = new DBCountry("Bhutan", "Asia");
        createCountry(country);
        country = new DBCountry("Brunei", "Asia");
        createCountry(country);
        country = new DBCountry("Burma (Myanmar)", "Asia");
        createCountry(country);
        country = new DBCountry("Cambodia", "Asia");
        createCountry(country);
        country = new DBCountry("China", "Asia");
        createCountry(country);
        country = new DBCountry("East Timor", "Asia");
        createCountry(country);
        country = new DBCountry("India", "Asia");
        createCountry(country);
        country = new DBCountry("Indonesia", "Asia");
        createCountry(country);
        country = new DBCountry("Iran", "Asia");
        createCountry(country);
        country = new DBCountry("Iraq", "Asia");
        createCountry(country);
        country = new DBCountry("Israel", "Asia");
        createCountry(country);
        country = new DBCountry("Japan", "Asia");
        createCountry(country);
        country = new DBCountry("Jordan", "Asia");
        createCountry(country);
        country = new DBCountry("Kazakhstan", "Asia");
        createCountry(country);
        country = new DBCountry("Korea, North", "Asia");
        createCountry(country);
        country = new DBCountry("Korea, South", "Asia");
        createCountry(country);
        country = new DBCountry("Kuwait", "Asia");
        createCountry(country);
        country = new DBCountry("Kyrgyzstan", "Asia");
        createCountry(country);
        country = new DBCountry("Laos", "Asia");
        createCountry(country);
        country = new DBCountry("Lebanon", "Asia");
        createCountry(country);
        country = new DBCountry("Malaysia", "Asia");
        createCountry(country);
        country = new DBCountry("Maldives", "Asia");
        createCountry(country);
        country = new DBCountry("Mongolia", "Asia");
        createCountry(country);
        country = new DBCountry("Nepal", "Asia");
        createCountry(country);
        country = new DBCountry("Oman", "Asia");
        createCountry(country);
        country = new DBCountry("Pakistan", "Asia");
        createCountry(country);
        country = new DBCountry("Philippines", "Asia");
        createCountry(country);
        country = new DBCountry("Qatar", "Asia");
        createCountry(country);
        country = new DBCountry("Russian Federation", "Asia");
        createCountry(country);
        country = new DBCountry("Saudi Arabia", "Asia");
        createCountry(country);
        country = new DBCountry("Singapore", "Asia");
        createCountry(country);
        country = new DBCountry("Sri Lanka", "Asia");
        createCountry(country);
        country = new DBCountry("Syria", "Asia");
        createCountry(country);
        country = new DBCountry("Tajikistan", "Asia");
        createCountry(country);
        country = new DBCountry("Thailand", "Asia");
        createCountry(country);
        country = new DBCountry("Turkey", "Asia");
        createCountry(country);
        country = new DBCountry("Turkmenistan", "Asia");
        createCountry(country);
        country = new DBCountry("United Arab Emirates", "Asia");
        createCountry(country);
        country = new DBCountry("Uzbekistan", "Asia");
        createCountry(country);
        country = new DBCountry("Vietnam", "Asia");
        createCountry(country);
        country = new DBCountry("Yemen", "Asia");
        createCountry(country);
        country = new DBCountry("Albania", "Europe");
        createCountry(country);
        country = new DBCountry("Andorra", "Europe");
        createCountry(country);
        country = new DBCountry("Armenia", "Europe");
        createCountry(country);
        country = new DBCountry("Austria", "Europe");
        createCountry(country);
        country = new DBCountry("Azerbaijan", "Europe");
        createCountry(country);
        country = new DBCountry("Belarus", "Europe");
        createCountry(country);
        country = new DBCountry("Belgium", "Europe");
        createCountry(country);
        country = new DBCountry("Bosnia and Herzegovina", "Europe");
        createCountry(country);
        country = new DBCountry("Bulgaria", "Europe");
        createCountry(country);
        country = new DBCountry("Croatia", "Europe");
        createCountry(country);
        country = new DBCountry("Cyprus", "Europe");
        createCountry(country);
        country = new DBCountry("Czech Republic", "Europe");
        createCountry(country);
        country = new DBCountry("Denmark", "Europe");
        createCountry(country);
        country = new DBCountry("Estonia", "Europe");
        createCountry(country);
        country = new DBCountry("Finland", "Europe");
        createCountry(country);
        country = new DBCountry("France", "Europe");
        createCountry(country);
        country = new DBCountry("Georgia", "Europe");
        createCountry(country);
        country = new DBCountry("Germany", "Europe");
        createCountry(country);
        country = new DBCountry("Greece", "Europe");
        createCountry(country);
        country = new DBCountry("Hungary", "Europe");
        createCountry(country);
        country = new DBCountry("Iceland", "Europe");
        createCountry(country);
        country = new DBCountry("Ireland", "Europe");
        createCountry(country);
        country = new DBCountry("Italy", "Europe");
        createCountry(country);
        country = new DBCountry("Latvia", "Europe");
        createCountry(country);
        country = new DBCountry("Liechtenstein", "Europe");
        createCountry(country);
        country = new DBCountry("Lithuania", "Europe");
        createCountry(country);
        country = new DBCountry("Luxembourg", "Europe");
        createCountry(country);
        country = new DBCountry("Macedonia", "Europe");
        createCountry(country);
        country = new DBCountry("Malta", "Europe");
        createCountry(country);
        country = new DBCountry("Moldova", "Europe");
        createCountry(country);
        country = new DBCountry("Monaco", "Europe");
        createCountry(country);
        country = new DBCountry("Montenegro", "Europe");
        createCountry(country);
        country = new DBCountry("Netherlands", "Europe");
        createCountry(country);
        country = new DBCountry("Norway", "Europe");
        createCountry(country);
        country = new DBCountry("Poland", "Europe");
        createCountry(country);
        country = new DBCountry("Portugal", "Europe");
        createCountry(country);
        country = new DBCountry("Romania", "Europe");
        createCountry(country);
        country = new DBCountry("San Marino", "Europe");
        createCountry(country);
        country = new DBCountry("Serbia", "Europe");
        createCountry(country);
        country = new DBCountry("Slovakia", "Europe");
        createCountry(country);
        country = new DBCountry("Slovenia", "Europe");
        createCountry(country);
        country = new DBCountry("Spain", "Europe");
        createCountry(country);
        country = new DBCountry("Sweden", "Europe");
        createCountry(country);
        country = new DBCountry("Switzerland", "Europe");
        createCountry(country);
        country = new DBCountry("Ukraine", "Europe");
        createCountry(country);
        country = new DBCountry("United Kingdom", "Europe");
        createCountry(country);
        country = new DBCountry("Vatican City", "Europe");
        createCountry(country);
        country = new DBCountry("Antigua and Barbuda", "North America");
        createCountry(country);
        country = new DBCountry("Bahamas", "North America");
        createCountry(country);
        country = new DBCountry("Barbados", "North America");
        createCountry(country);
        country = new DBCountry("Belize", "North America");
        createCountry(country);
        country = new DBCountry("Canada", "North America");
        createCountry(country);
        country = new DBCountry("Costa Rica", "North America");
        createCountry(country);
        country = new DBCountry("Cuba", "North America");
        createCountry(country);
        country = new DBCountry("Dominica", "North America");
        createCountry(country);
        country = new DBCountry("Dominican Republic", "North America");
        createCountry(country);
        country = new DBCountry("El Salvador", "North America");
        createCountry(country);
        country = new DBCountry("Grenada", "North America");
        createCountry(country);
        country = new DBCountry("Guatemala", "North America");
        createCountry(country);
        country = new DBCountry("Haiti", "North America");
        createCountry(country);
        country = new DBCountry("Honduras", "North America");
        createCountry(country);
        country = new DBCountry("Jamaica", "North America");
        createCountry(country);
        country = new DBCountry("Mexico", "North America");
        createCountry(country);
        country = new DBCountry("Nicaragua", "North America");
        createCountry(country);
        country = new DBCountry("Panama", "North America");
        createCountry(country);
        country = new DBCountry("Saint Kitts and Nevis", "North America");
        createCountry(country);
        country = new DBCountry("Saint Lucia", "North America");
        createCountry(country);
        country = new DBCountry("Saint Vincent and the Grenadines", "North America");
        createCountry(country);
        country = new DBCountry("Trinidad and Tobago", "North America");
        createCountry(country);
        country = new DBCountry("United States", "North America");
        createCountry(country);
        country = new DBCountry("Australia", "Oceania");
        createCountry(country);
        country = new DBCountry("Fiji", "Oceania");
        createCountry(country);
        country = new DBCountry("Kiribati", "Oceania");
        createCountry(country);
        country = new DBCountry("Marshall Islands", "Oceania");
        createCountry(country);
        country = new DBCountry("Micronesia", "Oceania");
        createCountry(country);
        country = new DBCountry("Nauru", "Oceania");
        createCountry(country);
        country = new DBCountry("New Zealand", "Oceania");
        createCountry(country);
        country = new DBCountry("Palau", "Oceania");
        createCountry(country);
        country = new DBCountry("Papua New Guinea", "Oceania");
        createCountry(country);
        country = new DBCountry("Samoa", "Oceania");
        createCountry(country);
        country = new DBCountry("Solomon Islands", "Oceania");
        createCountry(country);
        country = new DBCountry("Tonga", "Oceania");
        createCountry(country);
        country = new DBCountry("Tuvalu", "Oceania");
        createCountry(country);
        country = new DBCountry("Vanuatu", "Oceania");
        createCountry(country);
        country = new DBCountry("Argentina", "South America");
        createCountry(country);
        country = new DBCountry("Bolivia", "South America");
        createCountry(country);
        country = new DBCountry("Brazil", "South America");
        createCountry(country);
        country = new DBCountry("Chile", "South America");
        createCountry(country);
        country = new DBCountry("Colombia", "South America");
        createCountry(country);
        country = new DBCountry("Ecuador", "South America");
        createCountry(country);
        country = new DBCountry("Guyana", "South America");
        createCountry(country);
        country = new DBCountry("Paraguay", "South America");
        createCountry(country);
        country = new DBCountry("Peru", "South America");
        createCountry(country);
        country = new DBCountry("Suriname", "South America");
        createCountry(country);
        country = new DBCountry("Uruguay", "South America");
        createCountry(country);
        country = new DBCountry("Venezuela", "South America");
        createCountry(country);
    }
}

