package com.example.gabriella.wanderlust;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;

/**
 *  Class which handles the settings activity where the user is able to make changes in their
 *  saved information, delete their account or log out.
 */
public class SettingsActivity extends AppCompatActivity {

    /* For logging */
    private final static String LOG_TAG = TravelPage.class.getSimpleName();

    /* Current user */
    DBUser user;

    /* Database Helper */
    SQLiteHelper db = new SQLiteHelper(this);

    /* Components */
    ImageButton saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "TravelPage.java");

        /* Get parameters which where passed through MainActitivy to get the current user */
        user = (DBUser)getIntent().getSerializableExtra("user");

        /* Reuse the layout for adding new travels */
        setContentView(R.layout.activity_settings);

        /* Change the button in toolbar to an OK-button which the user clicks on when
         * done with their input for updating travel.
         */
        saveButton = (ImageButton)findViewById(R.id.settings);
        saveButton.setBackgroundResource(R.drawable.ic_check_box_white_24dp);



    }
}