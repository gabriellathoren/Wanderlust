package com.example.gabriella.wanderlust;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * <h1>AccountActivity</h1>
 *
 * The AccountActivity class takes data from the database and generate a list of countries. Every
 * country is represented as checkboxes which the user can select depending on where he/she has
 * traveled. The program then counts the amount of checked boxes and displays the total in the
 * activity.
 *
 * @author  Gabriella Thorén
 * @version 1
 */

public class AccountActivity extends AppCompatActivity {

    /* For logging */
    private final static String LOG_TAG = TravelPage.class.getSimpleName();

    /* Current user */
    DBUser user;

    /* Database Helper */
    SQLiteHelper db;

    /* Components */
    ImageButton  settings;
    LinearLayout ll;
    TextView     total;

    /* List of countries */
    List<DBCountry> africa;
    List<DBCountry> asia;
    List<DBCountry> europe;
    List<DBCountry> northAmerica;
    List<DBCountry> oceania;
    List<DBCountry> southAmerica;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Initialize the database */
        db = new SQLiteHelper(this);

        /* Store all countries in database. setCountries store a list of countries in the database. */
        db.setCountries();

        /* Get parameters which where passed through MainActitivy to get the current user */
        user = (DBUser)getIntent().getSerializableExtra("user");

        /* Set layout */
        setContentView(R.layout.activity_account);

        /* Change the icon for user account to settings */
        settings = (ImageButton)findViewById(R.id.settings);
        settings.setBackgroundResource(R.drawable.ic_settings_white_24dp);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settings();
            }
        });

        /* Set total of visited countries via db.getTotalVisitedCountries which counts how many
        * countries the user has checked. These are saved in the database. */
        total = (TextView) findViewById(R.id.total_countries);
        total.setText("" + db.getTotalVisitedCountries(user));

        /* Get LinearLayout to fill it with checkboxes */
        ll = (LinearLayout) findViewById(R.id.linear_country_layout);

        /* Get lists with all countries per continent through the database */
        africa       = db.getAllCountriesAfrica();
        asia         = db.getAllCountriesAsia();
        europe       = db.getAllCountriesEurope();
        northAmerica = db.getAllCountriesNorthAmerica();
        oceania      = db.getAllCountriesOceania();
        southAmerica = db.getAllCountriesSouthAmerica();

        /* Set title: "Africa" with underline and create checkboxes */
        createTitle("Africa");
        createUnderline();
        createCheckBoxes(africa);

        /* Set title: "Asia" with underline and create checkboxes */
        createTitle("Asia");
        createUnderline();
        createCheckBoxes(asia);

        /* Set title: "Europe" with underline and create checkboxes */
        createTitle("Europe");
        createUnderline();
        createCheckBoxes(europe);

        /* Set title: "North America" with underline and create checkboxes */
        createTitle("North America");
        createUnderline();
        createCheckBoxes(northAmerica);

        /* Set title: "Oceania" with underline and create checkboxes */
        createTitle("Oceania");
        createUnderline();
        createCheckBoxes(oceania);

        /* Set title: "South America" with underline and create checkboxes */
        createTitle("South America");
        createUnderline();
        createCheckBoxes(southAmerica);

    }

    /**
     * Controls if a checkbox is selected or not by comparing the name of the checkbox (the country)
     * with all components of the list containing all the user's visited countries which is stored
     * in the database.
     *
     * @param  checkbox  the checkbox that is being controlled to see if the user has selected it
     * @return           <code>true</code> if the checkbox is selected by the user
     *                   <code>false</code> if the checkbox is not selected by the user
     * @see              DBCountry
     */
    public boolean controlCheckBoxes(DBCountry checkbox) {

        List <DBCountry> visited = db.getVisitedCountries(user);

        for (int i=0; i<visited.size(); i++) {
            if (checkbox.getCountry().equals(visited.get(i).getCountry())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method which creates and places checkboxes in the activity layout. The checkboxes represents
     * countries which the user will be able to select to see how many countries he or she has
     * visited.
     *
     * @param countries     List of countries to be made in to checkboxes
     * @see                 CheckBox
     * @see                 DBCountry
     * @see                 #controlCheckBoxes(DBCountry)
     */
    public void createCheckBoxes(final List<DBCountry> countries) {

        for (int i=0; i<countries.size(); i++) {

            /* Create checkbox */
            CheckBox cb = new CheckBox(this);

            /* Set parameters with width and height */
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            /* Set margins */
            params.leftMargin = 70;

            /* Other attributes for the checkbox */
            cb.setText(countries.get(i).getCountry());
            cb.setTextSize(15);
            cb.setAllCaps(true);
            cb.setElegantTextHeight(true);

            /* Add checkbox to view with margins */
            ll.addView(cb, params);

            final DBCountry country = countries.get(i);

            /* Control if checkbox is selected or not */
            if (controlCheckBoxes(country)) {
                /*
                 * If it is the checkbox will be checked and the object will be updated with this
                 * information
                 */
                cb.setChecked(true);
                countries.get(i).setSelected(true);
            }
            else {
                /*
                 * If not the checkbox will be unchecked and the object will be updated on this
                 * information
                 */
                cb.setChecked(false);
                countries.get(i).setSelected(false);
            }

            /* Set onClickListener to checkbox */
            cb.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View view) {
                            checkOnClick(country);
                        }
                    }
            );

        }
    }



    /**
     *  OnClickListener for checkbox. When a checkbox is clicked on, this method sets the checkboxes
     *  status to either checked or unchecked depending on the previous status before the click.
     *  If the checkbox gets checked, this information is added to the database, otherwice it is
     *  deleted from the database.
     *
     *  Every time a checkbox is checked or unchecked, the number of total visited countries is
     *  changed.
     *
     *  @param country  the checkbox which is being clicked on
     *  @see            DBCountry
     *  @see            SQLiteHelper#deleteVisitedCountry(DBUser, DBCountry)
     *  @see            SQLiteHelper#createVisitedCountry(DBUser, DBCountry)
     *  @see            SQLiteHelper#getTotalVisitedCountries(DBUser)
     */
    public void checkOnClick(DBCountry country) {

        /* If the checkbox already is selected before the click */
        if(country.isSelected()) {

            /* Set selected to false */
            country.setSelected(false);

            /* Delete country from the table user_travel because the user has not been there */
            db.deleteVisitedCountry(user, country);
            total.setText("" + db.getTotalVisitedCountries(user));

        }

        /* If the checkbox isn't selected before the click*/
        else {

            /* Set selected to true */
            country.setSelected(true);

            /* Add country to the table user_travel because the user has been there */
            db.createVisitedCountry(user, country);
            total.setText("" + db.getTotalVisitedCountries(user));
        }
    }

    /**
     * Method which creates an underline to the layout. It is used to delimit the title (continent)
     * with its content (country).
     */
    public void createUnderline() {
        View underline = new View(this);
        LinearLayout.LayoutParams underlineMargin = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
        underline.setLayoutParams(underlineMargin);
        underlineMargin.bottomMargin = 30;
        underline.setBackgroundResource(R.color.pink_theme_color);
        ll.addView(underline, underlineMargin);
    }

    /**
     * Method that creates the title for the continents and adds it to the layout.
     *
     * @param title     the title to be created
     */
    public void createTitle(String title) {
        TextView tv = new TextView(this);
        tv.setText(title);
        tv.setPadding(50, 50, 10, 20);
        tv.setTextSize(20);
        ll.addView(tv);
    }


    /*
     * Method for start the settings activity. The activity require a {@link DBUser}.
     *
     * @see SettingsActivity
     */
    public void settings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


}
