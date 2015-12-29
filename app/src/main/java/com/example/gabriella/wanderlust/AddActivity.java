package com.example.gabriella.wanderlust;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * This class handles the activity for adding travels.
 */

public class AddActivity extends AppCompatActivity {

    /* Current user */
    DBUser user;

    /* Database Helper */
    SQLiteHelper db = new SQLiteHelper(this);

    /* For logging */
    private final static String LOG_TAG = StartPage.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "Add Activity");
        setContentView(R.layout.activity_add);
    }


    public void addWallpaperHandler() {
        
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
