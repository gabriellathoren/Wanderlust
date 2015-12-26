package com.example.gabriella.wanderlust;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Creates and updates table Country
 */
public class CountryTable {

    /* Database table */
    private static final String TABLE_COUNTRY = "country";

    /* Country table - column names */
    private static final String KEY_COUNTRY_NAME      = "country";
    private static final String KEY_COUNTRY_CONTINENT = "continent";

    private static final String CREATE_TABLE_COUNTRY = "create table " + TABLE_COUNTRY + "("
                                                     + KEY_COUNTRY_NAME      + " TEXT PRIMARY KEY, "
                                                     + KEY_COUNTRY_CONTINENT + " TEXT);";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_COUNTRY);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {

        Log.w(CountryTable.class.getName(), "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");

        /* Drop older table while upgrading */
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRY);

        onCreate(database);
    }

}
