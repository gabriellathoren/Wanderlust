package com.example.gabriella.wanderlust;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Gabriella on 2015-12-18.
 */
public class TravelPage extends AppCompatActivity {

    private final static String LOG_TAG = TravelPage.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "");
        setContentView(R.layout.activity_travel);



        /* Adds toolbar to activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView mTitle = (ImageView) toolbar.findViewById(R.id.toolbar_title);
        */

    }
    @Override
    protected void onStart() {
        super.onStart();
    }

}
