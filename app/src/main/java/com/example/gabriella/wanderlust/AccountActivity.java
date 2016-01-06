package com.example.gabriella.wanderlust;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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

    CountryAdapter dataAdapter;

    /* List of countries */
    List<DBCountry> africa;
    List<DBCountry> asia;
    List<DBCountry> europe;
    List<DBCountry> northAmerica;
    List<DBCountry> oceania;
    List<DBCountry> southAmerica;

    ListView lv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Get parameters which where passed through MainActitivy to get the current user */
        user = (DBUser)getIntent().getSerializableExtra("user");

        /* Set layout */
        setContentView(R.layout.activity_account);

        /* Change the icon for user account to settings */
        settings = (ImageButton)findViewById(R.id.settings);
        settings.setBackgroundResource(R.drawable.ic_settings_white_24dp);

        lv = (ListView) findViewById(R.id.list_view_africa);
        africa = db.getAllCountriesAfrica();
        CountryAdapter adapter = new CountryAdapter(this, africa);
        lv.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }



    @Override
    protected void onStart() {
        super.onStart();
    }


}
