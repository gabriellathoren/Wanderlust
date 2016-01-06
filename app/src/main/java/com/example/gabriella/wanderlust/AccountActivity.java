package com.example.gabriella.wanderlust;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Class which handles the users account where the user is able to pick the countries he or she
 * has visited, and a total of visited countries will be shown.
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

        db = new SQLiteHelper(this);

        /* Get parameters which where passed through MainActitivy to get the current user */
        user = (DBUser)getIntent().getSerializableExtra("user");

        /* Set layout */
        setContentView(R.layout.activity_account);

        /* Change the icon for user account to settings */
        settings = (ImageButton)findViewById(R.id.settings);
        settings.setBackgroundResource(R.drawable.ic_settings_white_24dp);

        /* Set total of visited countries */
        total = (TextView) findViewById(R.id.total_countries);
        total.setText("" + db.getTotalVisitedCountries(user));

        /* Get LinearLayout to fill it with checkboxes */
        ll = (LinearLayout) findViewById(R.id.linear_country_layout);

        /* Store all countries in database */
        db.setCountries();

        /* Get lists with all countries per continent */
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

    /* See if checkbox is selected or not */
    public boolean controlCheckBoxes(DBCountry checkbox) {

        List <DBCountry> visited = db.getVisitedCountries(user);

        for (int i=0; i<visited.size(); i++) {

            if (checkbox.getCountry().equals(visited.get(i).getCountry())) {
                return true;
            }
        }

        return false;

    }

    /* Method which creates and places checkboxes in layout */
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

            /* Add checkbox to view */
            ll.addView(cb, params);

            final DBCountry country = countries.get(i);

            /* Control if checkbox is selected or not */
            if (controlCheckBoxes(country)) {
                cb.setChecked(true);
                countries.get(i).setSelected(true);
            }
            else {
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



    /* OnClickListener for checkbox */
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

    /* Method that creates an underline to the layout */
    public void createUnderline() {
        View underline = new View(this);
        LinearLayout.LayoutParams underlineMargin = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
        underline.setLayoutParams(underlineMargin);
        underlineMargin.bottomMargin = 30;
        underline.setBackgroundResource(R.color.pink_theme_color);
        ll.addView(underline, underlineMargin);
    }

    /* Method that creates the title for the continents and adds it to the layout */
    public void createTitle(String title) {
        TextView tv = new TextView(this);
        tv.setText(title);
        tv.setPadding(50, 50, 10, 20);
        tv.setTextSize(20);
        ll.addView(tv);
    }



    @Override
    protected void onStart() {
        super.onStart();
    }


}
