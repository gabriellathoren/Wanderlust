package com.example.gabriella.wanderlust;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Gabriella on 2015-12-18.
 */
public class TravelPage extends AppCompatActivity {

    private final static String LOG_TAG = TravelPage.class.getSimpleName();

    private DBTravel travel;

    /* Components */
    ImageButton imageButton;
    EditText    travelTitle;
    DatePicker  datePicker;
    ImageView   wallpaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "TravelPage.java");

        /* Get parameters which where passed through MainActitivy to get the current user */
        travel = (DBTravel)getIntent().getSerializableExtra("travel");

        /* Reuse the layout for adding new travels */
        setContentView(R.layout.activity_add);

        /* Initialize datePicker with the related xml-component and set minimum date to today's date */
        datePicker = (DatePicker) findViewById(R.id.travel_date_picker);
        datePicker.setMinDate(new Date().getTime());

        /* Initialize EditText with the related xml-component */
        travelTitle = (EditText) findViewById(R.id.travel_title);

        /* Change the settings button in toolbar to an OK-button which the user clicks on when
         * done with their input for adding travel
         */
        imageButton = (ImageButton)findViewById(R.id.settings);
        imageButton.setBackgroundResource(R.drawable.ic_check_box_white_24dp);

        /* Set DatePicker to travel date */
        int year  = travel.getYear();
        int month = travel.getMonth();
        int day   = travel.getDay();

        datePicker.updateDate(year, month, day);

        /* Set title */
        travelTitle.setText(travel.getTitle());

        /* Set background */
        travel.getWallpaper();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
