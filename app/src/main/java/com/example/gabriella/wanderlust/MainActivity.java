package com.example.gabriella.wanderlust;

import com.example.gabriella.wanderlust.SQLiteHelper;
import com.example.gabriella.wanderlust.DBCountry;
import com.example.gabriella.wanderlust.DBTravel;
import com.example.gabriella.wanderlust.DBUser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    /* Database Helper */
    SQLiteHelper db;

    /* Buttons and other objects in layout */
    Button loginButton;
    EditText usernameET;
    EditText passwordET;


    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    //För att logga, vilket är bra när man ska ta fram funktionalitet för att vi ska kunna se att
    //det funkar. Genom denna kan man skriva ut loggutskrifter.
    //Denna består av en static string eftersom den ska vara gemensam för alla objekt. Den är även
    //privat så ingen annan behöver känna till den. Den deklareras även som final eftersom man inte
    //ska kunna röra på den.
    //Denna sätts till MainActivity eftersom det är min klass som jag befinner mig i och därifrån
    // hämtas den information som behövs.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new SQLiteHelper(getApplicationContext());

        loginButton = (Button)findViewById(R.id.login_button);
        usernameET  = (EditText)findViewById(R.id.usernameID);
        passwordET  = (EditText)findViewById(R.id.passwordID);

        loginButton.setOnClickListener(


                new View.OnClickListener() {
                    public void onClick(View view) {
                         /* Set values in table country */
                        db.setCountries();

                        /* Add one example user */
                        DBUser exUser = new DBUser("gu@mail.com",  "wanderlust", "Gabriella", "Thoren");
                        db.createUser(exUser);

                        String username = usernameET.getText().toString();
                        String password = passwordET.getText().toString();

                        /* Control if user exists */
                        if (db.ifUserExists(username, password)) {
                            setContentView(R.layout.activity_start);
                        }
                        else {

                        }
                    }
                }
        );

        db.close();
    }

    /*
    Button btn1 = (Button) findViewById(R.id.login_button);
    btn1.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            setContentView(R.layout.activity_start);
        }
    });

    /* Method for log in and show new activity
    public void logIn(View view) {
        Intent intent = new Intent(this, StartPage.class);
        startActivity(intent);
        //setContentView(R.layout.activity_start);
    }
    */

    @Override
    protected void onStart() {
        super.onStart();
    }

}
