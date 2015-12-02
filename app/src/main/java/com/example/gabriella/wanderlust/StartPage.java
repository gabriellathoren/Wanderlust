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
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Collection;

import android.widget.AdapterView;

public class StartPage extends AppCompatActivity {

    private ListActivity lAct;

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

        /* Adds toolbar to activity */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        /* Adds list to activity */
        this.lAct.setListAdapter(new ArrayAdapter<String>(this, R.layout.travel_list,R.id.Itemname,itemname));
    }

}
