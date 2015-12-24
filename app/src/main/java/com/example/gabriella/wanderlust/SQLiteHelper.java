package com.example.gabriella.wanderlust;

/**
 * Created by Gabriella on 2015-12-23.
 *
 * This class handles the database
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class SQLiteHelper extends SQLiteOpenHelper{

    /* Logcat tag */
    private static final String LOG = "DatabaseHelper";

    /* Database version */
    private static final int DATABASE_VERSION = 1;

    /* Database name */
    private static final String DATABASE_NAME = "database";

    /* Table names */
    private static final String TABLE_USER           = "user";
    private static final String TABLE_COUNTRY        = "country";
    private static final String TABLE_TRAVEL         = "travel";
    private static final String TABLE_USER_TRAVEL    = "user_travel";
    private static final String TABLE_TRAVEL_COUNTRY = "travel_country";
    private static final String TABLE_BUCKET_LIST    = "bucket_list";

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

    /* Bucket_List table - column names */
    private static final String KEY_LIST_ID = "list_ID";
    private static final String KEY_ITEM = "item";


    /* Table create statements */
    private static final String CREATE_TABLE_USER = "create table " + TABLE_USER + "("
            + KEY_USER_ID + " INTEGER PRIMARY KEY, "
            + KEY_USER_USERNAME   + " TEXT, "
            + KEY_USER_PASSWORD   + " TEXT, "
            + KEY_USER_FIRST_NAME + " TEXT, "
            + KEY_USER_LAST_NAME  + " TEXT;)";

    private static final String CREATE_TABLE_COUNTRY = "create table " + TABLE_COUNTRY + "("
            + KEY_COUNTRY_NAME      + " TEXT PRIMARY KEY, "
            + KEY_COUNTRY_CONTINENT + " TEXT);";

    private static final String CREATE_TABLE_TRAVEL = "create table " + TABLE_TRAVEL + "("
            + KEY_TRAVEL_ID        + " INTEGER PRIMARY KEY, "
            + KEY_TRAVEL_TITLE     + " TEXT, "
            + KEY_TRAVEL_YEAR      + " INTEGER, "
            + KEY_TRAVEL_MONTH     + " INTEGER, "
            + KEY_TRAVEL_DAY       + " INTEGER, "
            + KEY_TRAVEL_WALLPAPER + " INTEGER);";

    private static final String CREATE_TABLE_USER_TRAVEL = "create table " + TABLE_USER_TRAVEL + "("
            + KEY_USER_ID   + " INTEGER PRIMARY KEY, "
            + KEY_TRAVEL_ID + " INTEGER PRIMARY KEY,"
            + "FOREIGN KEY (" + KEY_USER_ID + ") REFERENCES " + TABLE_USER + "(" + KEY_USER_ID + "), "
            + "FOREIGN KEY (" + KEY_TRAVEL_ID + ") REFERENCES " + TABLE_TRAVEL + "(" + KEY_TRAVEL_ID + "));";

    private static final String CREATE_TABLE_TRAVEL_COUNTRY = "create table " + TABLE_TRAVEL_COUNTRY + "("
            + KEY_COUNTRY_NAME + " TEXT PRIMARY KEY, "
            + KEY_TRAVEL_ID    + " INTEGER PRIMARY KEY, "
            + "FOREIGN KEY ("  + KEY_COUNTRY_NAME + ") REFERENCES " + TABLE_COUNTRY + "(" + KEY_COUNTRY_NAME + "), "
            + "FOREIGN KEY ("  + KEY_TRAVEL_ID + ") REFERENCES " + TABLE_TRAVEL + "(" + KEY_TRAVEL_ID + "));";

    private static final String CREATE_TABLE_BUCKET_LIST = "create table " + TABLE_BUCKET_LIST + "("
            + KEY_LIST_ID   + " INTEGER PRIMARY KEY, "
            + KEY_TRAVEL_ID + " INTEGER, "
            + KEY_ITEM      + " TEXT, "
            + "FOREIGN KEY (" + KEY_TRAVEL_ID + ") REFERENCES " + TABLE_TRAVEL + "(" + KEY_TRAVEL_ID + "));";



    /* Constructor */
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        /* Create tables */
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_COUNTRY);
        db.execSQL(CREATE_TABLE_TRAVEL);
        db.execSQL(CREATE_TABLE_USER_TRAVEL);
        db.execSQL(CREATE_TABLE_TRAVEL_COUNTRY);
        db.execSQL(CREATE_TABLE_BUCKET_LIST);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        /* Drop older tables while upgrading */
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_COUNTRY);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_TRAVEL);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_USER_TRAVEL);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_TRAVEL_COUNTRY);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_BUCKET_LIST);

        /* Create new tables */
        onCreate(db);

    }

    /* Create a user */
    public void createUser(DBUser user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID,         user.getUser_ID());
        values.put(KEY_USER_USERNAME,   user.getUsername());
        values.put(KEY_USER_PASSWORD,   user.getPassword());
        values.put(KEY_USER_FIRST_NAME, user.getFirst_name());
        values.put(KEY_USER_LAST_NAME,  user.getLast_name());

        /* Insert row */
        db.insert(TABLE_USER, null, values);

    }

    /* Delete a user */
    public void deleteUser(long user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_USER_ID + " = ?", new String[]{String.valueOf(user_id)});
    }


    /* Update user information */
    public void updateUser(int id, String username, String password, String firstname, String lastname) {
        SQLiteDatabase db = this.getWritableDatabase();
        DBUser user;

        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID,         id);
        values.put(KEY_USER_USERNAME,   username);
        values.put(KEY_USER_PASSWORD,   password);
        values.put(KEY_USER_FIRST_NAME, firstname);
        values.put(KEY_USER_LAST_NAME,  lastname);

        db.update(TABLE_USER, values, KEY_USER_ID + " = ?",
                  new String[] { String.valueOf(id)});

    }


    /* Get user information by id */
    public DBUser getUser(long user_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE "
                             + KEY_USER_ID + " = " + user_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
        }

        DBUser u = new DBUser();
        u.setUser_ID(c.getInt(c.getColumnIndex(KEY_USER_ID)));
        u.setUsername(c.getString(c.getColumnIndex(KEY_USER_USERNAME)));
        u.setPassword(c.getString(c.getColumnIndex(KEY_USER_PASSWORD)));
        u.setFirst_name(c.getString(c.getColumnIndex(KEY_USER_FIRST_NAME)));
        u.setLast_name(c.getString(c.getColumnIndex(KEY_USER_LAST_NAME)));

        c.close();

        return u;
    }

    /* Get user information by username */
    public DBUser getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE "
                             + KEY_USER_USERNAME + " = '" + username + "'";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
        }

        DBUser u = new DBUser();
        u.setUser_ID(c.getInt(c.getColumnIndex(KEY_USER_ID)));
        u.setUsername((c.getString(c.getColumnIndex(KEY_USER_USERNAME))));
        u.setPassword(c.getString(c.getColumnIndex(KEY_USER_PASSWORD)));
        u.setFirst_name(c.getString(c.getColumnIndex(KEY_USER_FIRST_NAME)));
        u.setLast_name(c.getString(c.getColumnIndex(KEY_USER_LAST_NAME)));

        return u;
    }


    /* Create a country */
    public long createCountry(DBCountry country) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_COUNTRY_NAME, country.getCountry());
        values.put(KEY_COUNTRY_CONTINENT, country.getContinent());

        /* Insert row */
        long country_id = db.insert(TABLE_COUNTRY, null, values);

        return country_id;
    }

    /* Add all countries of the world to table */
    public void setCountries() {

    }

    /* Get the involved country in the travel */
    public DBCountry getCountry(int travel_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        /* ÄNDRA SQL !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */
        String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE "
                + KEY_USER_USERNAME + " = 'hej'";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
        }

        DBCountry country = new DBCountry();
        country.setCountry(c.getString(c.getColumnIndex(KEY_COUNTRY_NAME)));
        country.setContinent((c.getString(c.getColumnIndex(KEY_COUNTRY_CONTINENT))));

        return country;
    }

    /* Get all countries */




    /* Create a travel */
    public void createTravel(DBTravel travel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TRAVEL_ID, travel.getTravel_ID());
        values.put(KEY_TRAVEL_TITLE, travel.getTitle());
        values.put(KEY_TRAVEL_YEAR, travel.getYear());
        values.put(KEY_TRAVEL_MONTH, travel.getMonth());
        values.put(KEY_TRAVEL_DAY, travel.getDay());
        values.put(KEY_TRAVEL_WALLPAPER, travel.getWallpaper());

        /* Insert row */
        long user_id = db.insert(TABLE_USER, null, values);
    }









}

