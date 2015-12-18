/**
 * Created by Gabriella on 2015-11-30.
 */

package com.example.gabriella.wanderlust;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.ListActivity;
import android.widget.ArrayAdapter;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.AdapterView;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import java.util.ArrayList;
import java.util.Collection;



public class StartPage extends AppCompatActivity {

    private final static String LOG_TAG = StartPage.class.getSimpleName();

    String[] itemname ={
            "PARIS",
            "OSLO",
            "NEW YORK",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "");
        setContentView(R.layout.activity_start);
        Intent intent = getIntent();

        /* Adds toolbar to activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView mTitle = (ImageView) toolbar.findViewById(R.id.toolbar_title);
        */

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    /* Method that shows new activity after clicking cardview */
    public void travelLayout(View view) {
        Intent intent = new Intent(this, TravelPage.class);
        startActivity(intent);
        //setContentView(R.layout.activity_start);
    }

}
