package com.example.gabriella.wanderlust;

/**
 * This class handles the database
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

    /* Set context, otherwice the getResource() won't work */
    private Context context;

    /* Table names */
    private static final String TABLE_USER           = "user";
    private static final String TABLE_COUNTRY        = "country";
    private static final String TABLE_TRAVEL         = "travel";
    private static final String TABLE_TRAVEL_COUNTRY = "travel_country";

    /* User table - column names */
    private static final String KEY_USER_ID         = "user_ID";
    private static final String KEY_USER_USERNAME   = "username";
    private static final String KEY_USER_PASSWORD   = "password";
    private static final String KEY_USER_FIRST_NAME = "first_name";
    private static final String KEY_USER_LAST_NAME  = "last_name";

    /* Country table - column names */
    private static final String KEY_COUNTRY_NAME      = "country";
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
            + KEY_TRAVEL_WALLPAPER + " BLOB, "
            + KEY_USER_ID          + " INTEGER NOT NULL, "
            + "FOREIGN KEY (" + KEY_USER_ID + ") REFERENCES " + TABLE_USER + "(" + KEY_USER_ID + "))";

    private static final String CREATE_TABLE_TRAVEL_COUNTRY = "create table  if not exists " + TABLE_TRAVEL_COUNTRY + "("
            + KEY_COUNTRY_NAME + " TEXT UNIQUE, "
            + KEY_TRAVEL_ID    + " INTEGER UNIQUE, "
            + "PRIMARY KEY ("  + KEY_COUNTRY_NAME + "," + KEY_TRAVEL_ID + ") "
            + "FOREIGN KEY ("  + KEY_COUNTRY_NAME + ") REFERENCES " + TABLE_COUNTRY + "(" + KEY_COUNTRY_NAME + "), "
            + "FOREIGN KEY ("  + KEY_TRAVEL_ID    + ") REFERENCES " + TABLE_TRAVEL  + "(" + KEY_TRAVEL_ID    + "))";


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
        db.execSQL(CREATE_TABLE_TRAVEL_COUNTRY);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        /* Drop older tables while upgrading */
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_COUNTRY);
        db.execSQL(CREATE_TABLE_TRAVEL);
        db.execSQL(CREATE_TABLE_TRAVEL_COUNTRY);

        /* Create new tables */
        onCreate(db);
    }





    /* METHODS FOR ACCESSING THE TABLE USER */


    /* Create a user */
    public void createUser(DBUser user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID,         user.getUserID());
        values.put(KEY_USER_USERNAME,   user.getUsername());
        values.put(KEY_USER_PASSWORD,   user.getPassword());
        values.put(KEY_USER_FIRST_NAME, user.getFirstName());
        values.put(KEY_USER_LAST_NAME, user.getLastName());

        /* Insert row */
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    /* Delete a user */
    public void deleteUser(long user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_USER_ID + " = ?", new String[]{String.valueOf(user_id)});
        db.close();
    }


    /* Update user information */
    public void updateUser(DBUser user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_USERNAME, user.getUsername());
        values.put(KEY_USER_PASSWORD, user.getPassword());
        values.put(KEY_USER_FIRST_NAME, user.getFirstName());
        values.put(KEY_USER_LAST_NAME, user.getLastName());

        db.update(TABLE_USER, values, KEY_USER_ID + " = ?", new String[]{String.valueOf(user.getUserID())});

        db.close();

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
        u.setUserID(c.getInt(c.getColumnIndex(KEY_USER_ID)));
        u.setUsername(c.getString(c.getColumnIndex(KEY_USER_USERNAME)));
        u.setPassword(c.getString(c.getColumnIndex(KEY_USER_PASSWORD)));
        u.setFirstName(c.getString(c.getColumnIndex(KEY_USER_FIRST_NAME)));
        u.setLastName(c.getString(c.getColumnIndex(KEY_USER_LAST_NAME)));

        c.close();
        db.close();

        return u;
    }

    /* Get user information by username */
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

    /* Check if user exists */
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


    /* METHODS FOR ACCESSING THE TABLE TRAVEL */

    /* Create a travel */
    public void createTravel(DBTravel travel, DBUser user) {
        SQLiteDatabase db = this.getWritableDatabase();

        /* Save the data in ContentValues to store it in database */
        ContentValues values = new ContentValues();
        values.put(KEY_TRAVEL_TITLE,     travel.getTitle());
        values.put(KEY_TRAVEL_YEAR,      travel.getYear());
        values.put(KEY_TRAVEL_MONTH,     travel.getMonth());
        values.put(KEY_TRAVEL_DAY,       travel.getDay());
        values.put(KEY_TRAVEL_WALLPAPER, travel.getWallpaperAsByte());
        values.put(KEY_USER_ID,          user.getUserID());

        /* Insert row to the table user*/
        db.insert(TABLE_TRAVEL, null, values);
        db.close();
    }


    /* Delete travel */
    public void deleteTravel(long travelID, long userID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRAVEL, KEY_TRAVEL_ID + " = ?", new String[]{String.valueOf(travelID)});
        db.close();
    }

    /* Update travel information */
    public void updateTravel(DBTravel travel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TRAVEL_TITLE, travel.getTitle());
        values.put(KEY_TRAVEL_YEAR,      travel.getYear());
        values.put(KEY_TRAVEL_MONTH, travel.getMonth());
        values.put(KEY_TRAVEL_DAY,       travel.getDay());
        values.put(KEY_TRAVEL_WALLPAPER, travel.getWallpaperAsByte());

        db.update(TABLE_USER, values, KEY_USER_ID + " = ?",
                new String[]{String.valueOf(travel.getTravelID())});

        db.close();

    }

    /* List all the user's travels */
    public List<DBTravel> getTravels(DBUser user) {
        Log.d(LOG, "SQLiteHelper.getTravels()");
        SQLiteDatabase db = this.getReadableDatabase();
        List<DBTravel> travels = new ArrayList<>();

        String selectQuery = ("SELECT * FROM " + TABLE_TRAVEL + "," + TABLE_USER + " "
                           +  "WHERE " + TABLE_TRAVEL  + "." + KEY_USER_ID       + " = " + TABLE_USER + "." + KEY_USER_ID + " "
                           +  "AND "   + TABLE_USER    + "." + KEY_USER_USERNAME + " = '" + user.getUsername() + "'");

        Log.e(LOG, selectQuery);


        Cursor c = db.rawQuery(selectQuery, null);


        /* Looping through all rows and adding to list */
        if (c.moveToFirst()) {
            do {
                DBTravel t = new DBTravel();
                t.setTravelID(c.getInt   (c.getColumnIndex(TABLE_TRAVEL + "." + KEY_TRAVEL_ID)));
                t.setTitle   (c.getString(c.getColumnIndex(TABLE_TRAVEL + "." + KEY_TRAVEL_TITLE)));
                t.setYear    (c.getInt   (c.getColumnIndex(TABLE_TRAVEL + "." + KEY_TRAVEL_YEAR)));
                t.setMonth   (c.getInt   (c.getColumnIndex(TABLE_TRAVEL + "." + KEY_TRAVEL_MONTH)));
                t.setDay     (c.getInt   (c.getColumnIndex(TABLE_TRAVEL + "." + KEY_TRAVEL_DAY)));

                /* Control if the user choose own image as a wallpaper */
                if (c.getColumnIndex(TABLE_TRAVEL + "." + KEY_TRAVEL_WALLPAPER) != -1) {
                    byte[] bytes = c.getBlob(c.getColumnIndex(TABLE_TRAVEL + "." + KEY_TRAVEL_WALLPAPER));
                    t.setWallpaperFromDatabase(bytes);
                }
                /* If not, the travel gets an default background */
                else {
                    switch(wallpaperColor) {
                        case 1:
                            Bitmap color = BitmapFactory.decodeResource(context.getResources(), R.drawable.backg1);
                            t.setWallpaper(color);
                            wallpaperColor++;
                            break;

                        case 2:
                            color = BitmapFactory.decodeResource(context.getResources(), R.drawable.backg2);
                            t.setWallpaper(color);
                            wallpaperColor++;
                            break;

                        case 3:
                            color = BitmapFactory.decodeResource(context.getResources(), R.drawable.backg3);
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
        return travels;

    }


    /* Get a single travel
    public DBTravel getTravel(DBTravel travel) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_TRAVEL + " "
                           + "WHERE " + KEY_TRAVEL_ID + " = " + travel.getTravelID();

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
        }

        DBTravel t = new DBTravel();
        t.setTravelID(c.getInt(c.getColumnIndex(KEY_TRAVEL_ID)));
        t.setYear(c.getInt(c.getColumnIndex(KEY_TRAVEL_YEAR)));
        t.setMonth(c.getInt(c.getColumnIndex(KEY_TRAVEL_MONTH)));
        t.setDay(c.getInt(c.getColumnIndex(KEY_TRAVEL_DAY)));
        t.setWallpaper(c.getInt(c.getColumnIndex(KEY_TRAVEL_WALLPAPER)));


        c.close();
        db.close();

        return t;
    }
    */

    /* METHODS FOR ACCESSING THE TABLE COUNTRY */

    /* Get the involved countries in the travel */
    public List<DBCountry> getCountries(int travelID) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<DBCountry> countries = new ArrayList<>();

        String selectQuery = ("SELECT " + KEY_COUNTRY_NAME     + " "
                           +  "FROM "   + TABLE_COUNTRY        + "," + TABLE_TRAVEL_COUNTRY   + "," + TABLE_TRAVEL   + " "
                           +  "WHERE "  + TABLE_TRAVEL_COUNTRY + "." + KEY_TRAVEL_ID    + "=" + TABLE_TRAVEL  + "." + KEY_TRAVEL_ID
                           +  "AND "    + TABLE_TRAVEL_COUNTRY + "." + KEY_COUNTRY_NAME + "=" + TABLE_COUNTRY + "." + KEY_COUNTRY_NAME
                + "AND "    + TABLE_TRAVEL         + "." + KEY_TRAVEL_ID + "=" + travelID);

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        /* Looping through all rows and adding to list */
        if (c.moveToFirst()) {
            do {
                DBCountry country = new DBCountry();
                country.setCountry(c.getString(c.getColumnIndex(KEY_COUNTRY_NAME)));
                country.setContinent((c.getString(c.getColumnIndex(KEY_COUNTRY_CONTINENT))));
                countries.add(country);
            }
            while (c.moveToNext());
        }

        c.close();
        db.close();
        return countries;
    }

    /* Get/list all countries in Africa */
    public List<DBCountry> getAllCountriesAfrica() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<DBCountry> countries = new ArrayList<>();

        String selectQuery = ("SELECT "   + KEY_COUNTRY_NAME     + " "
                +  "FROM "     + TABLE_COUNTRY        + ", "
                + "WHERE " + KEY_COUNTRY_CONTINENT + "= 'Africa' "
                +  "ORDER BY " + KEY_COUNTRY_NAME + " ASC");

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        /* Looping through all rows and adding to list */
        if (c.moveToFirst()) {
            do {
                DBCountry country = new DBCountry();
                country.setCountry(c.getString(c.getColumnIndex(KEY_COUNTRY_NAME)));
                country.setContinent((c.getString(c.getColumnIndex(KEY_COUNTRY_CONTINENT))));
                countries.add(country);
            }
            while (c.moveToNext());
        }

        c.close();
        db.close();
        return countries;
    }

    /* Get/list all countries in Asia */
    public List<DBCountry> getAllCountriesAsia() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<DBCountry> countries = new ArrayList<>();

        String selectQuery = ("SELECT "   + KEY_COUNTRY_NAME     + " "
                +  "FROM "     + TABLE_COUNTRY        + ", "
                + "WHERE "    + KEY_COUNTRY_CONTINENT + "= 'Asia' "
                +  "ORDER BY " + KEY_COUNTRY_NAME + " ASC");

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        /* Looping through all rows and adding to list */
        if (c.moveToFirst()) {
            do {
                DBCountry country = new DBCountry();
                country.setCountry(c.getString(c.getColumnIndex(KEY_COUNTRY_NAME)));
                country.setContinent((c.getString(c.getColumnIndex(KEY_COUNTRY_CONTINENT))));
                countries.add(country);
            }
            while (c.moveToNext());
        }

        c.close();
        db.close();
        return countries;
    }

    /* Get/list all countries in Europe */
    public List<DBCountry> getAllCountriesEurope() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<DBCountry> countries = new ArrayList<>();

        String selectQuery = ("SELECT "   + KEY_COUNTRY_NAME     + " "
                +  "FROM "     + TABLE_COUNTRY        + ", "
                + "WHERE " + KEY_COUNTRY_CONTINENT + "= 'Europe' "
                +  "ORDER BY " + KEY_COUNTRY_NAME + " ASC");

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        /* Looping through all rows and adding to list */
        if (c.moveToFirst()) {
            do {
                DBCountry country = new DBCountry();
                country.setCountry(c.getString(c.getColumnIndex(KEY_COUNTRY_NAME)));
                country.setContinent((c.getString(c.getColumnIndex(KEY_COUNTRY_CONTINENT))));
                countries.add(country);
            }
            while (c.moveToNext());
        }

        c.close();
        db.close();
        return countries;
    }

    /* Get/list all countries in North America */
    public List<DBCountry> getAllCountriesNorthAmerica() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<DBCountry> countries = new ArrayList<>();

        String selectQuery = ("SELECT "   + KEY_COUNTRY_NAME     + " "
                +  "FROM "     + TABLE_COUNTRY        + ", "
                +  "WHERE "    + KEY_COUNTRY_CONTINENT + "= 'North America' "
                +  "ORDER BY " + KEY_COUNTRY_NAME + " ASC");

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        /* Looping through all rows and adding to list */
        if (c.moveToFirst()) {
            do {
                DBCountry country = new DBCountry();
                country.setCountry(c.getString(c.getColumnIndex(KEY_COUNTRY_NAME)));
                country.setContinent((c.getString(c.getColumnIndex(KEY_COUNTRY_CONTINENT))));
                countries.add(country);
            }
            while (c.moveToNext());
        }

        c.close();
        db.close();
        return countries;
    }

    /* Get/list all countries in Oceania */
    public List<DBCountry> getAllCountriesOceania() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<DBCountry> countries = new ArrayList<>();

        String selectQuery = ("SELECT "   + KEY_COUNTRY_NAME     + " "
                +  "FROM "     + TABLE_COUNTRY        + ", "
                + "WHERE " + KEY_COUNTRY_CONTINENT + "= 'Oceania' "
                +  "ORDER BY " + KEY_COUNTRY_NAME + " ASC");

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        /* Looping through all rows and adding to list */
        if (c.moveToFirst()) {
            do {
                DBCountry country = new DBCountry();
                country.setCountry(c.getString(c.getColumnIndex(KEY_COUNTRY_NAME)));
                country.setContinent((c.getString(c.getColumnIndex(KEY_COUNTRY_CONTINENT))));
                countries.add(country);
            }
            while (c.moveToNext());
        }

        c.close();
        db.close();
        return countries;
    }

    /* Get/list all countries in South America */
    public List<DBCountry> getAllCountriesSouthAmerica() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<DBCountry> countries = new ArrayList<>();

        String selectQuery = ("SELECT "   + KEY_COUNTRY_NAME     + " "
                +  "FROM "     + TABLE_COUNTRY        + ", "
                +  "WHERE "    + KEY_COUNTRY_CONTINENT + "= 'South America' "
                +  "ORDER BY " + KEY_COUNTRY_NAME + " ASC");

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        /* Looping through all rows and adding to list */
        if (c.moveToFirst()) {
            do {
                DBCountry country = new DBCountry();
                country.setCountry(c.getString(c.getColumnIndex(KEY_COUNTRY_NAME)));
                country.setContinent((c.getString(c.getColumnIndex(KEY_COUNTRY_CONTINENT))));
                countries.add(country);
            }
            while (c.moveToNext());
        }

        c.close();
        db.close();
        return countries;
    }

    /* Create a country */
    public void createCountry(DBCountry country) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_COUNTRY_NAME,      country.getCountry());
        values.put(KEY_COUNTRY_CONTINENT, country.getContinent());

        /* Insert row */
        db.insert(TABLE_COUNTRY, null, values);
        db.close();

        Log.v("MyActivity","createCountry (" + country.getCountry() + "," + country.getContinent() + ")");
    }


    /* Add all countries of the world to table */
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
        country = new DBCountry("Congo, Democratic Republic of", "Africa");
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

