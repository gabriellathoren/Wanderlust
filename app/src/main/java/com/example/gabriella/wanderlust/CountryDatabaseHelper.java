package com.example.gabriella.wanderlust;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *  This class extends SQLiteHelper and calls the static methods of
 *  the CountryTable helper class.


public class CountryDatabaseHelper extends SQLiteOpenHelper {

    /* Logcat tag
    private static final String LOG = "DatabaseHelper";

    /* Database version
    private static final int DATABASE_VERSION = 1;

    /* Database name
    private static final String DATABASE_NAME = "database";

    public CountryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        CountryTable.onCreate(database);
        /* Set values in table country
        CountryContentProvider.setCountries();
    }

    /* Method is called during an upgrade of the database
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        CountryTable.onUpgrade(database, oldVersion, newVersion);
    }

}
*/