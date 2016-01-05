package com.example.gabriella.wanderlust;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

/**
 * Class which handles the users account.
 */

public class AccountActivity extends AppCompatActivity {

    /* For logging */
    private final static String LOG_TAG = TravelPage.class.getSimpleName();

    /* Current user */
    DBUser user;

    /* Database Helper */
    SQLiteHelper db = new SQLiteHelper(this);

    /* Components */
    ImageButton settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Get parameters which where passed through MainActitivy to get the current user */
        user = (DBUser)getIntent().getSerializableExtra("user");

        /* Change the icon for user account to settings */
        settings = (ImageButton)findViewById(R.id.settings);
        settings.setBackgroundResource(R.drawable.ic_settings_white_24dp);

        /* Set layout */
        setContentView(R.layout.activity_account);



    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
