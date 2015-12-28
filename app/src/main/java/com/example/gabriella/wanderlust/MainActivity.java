package com.example.gabriella.wanderlust;

import com.example.gabriella.wanderlust.SQLiteHelper;
import com.example.gabriella.wanderlust.DBCountry;
import com.example.gabriella.wanderlust.DBTravel;
import com.example.gabriella.wanderlust.DBUser;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity  {

    /* Database Helper */
    SQLiteHelper db;

    /* Buttons and other objects in layout */
    Button   loginButton;
    EditText usernameET;
    EditText passwordET;

    final Context context = this;

    /* Current user (needed for further accessing in other classes */
    private static DBUser user;


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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);

        db = new SQLiteHelper(getApplicationContext());

        loginButton = (Button)findViewById(R.id.login_button);
        usernameET  = (EditText)findViewById(R.id.usernameID);
        passwordET  = (EditText)findViewById(R.id.passwordID);

        loginButton.setOnClickListener(


            new View.OnClickListener() {
                public void onClick(View view) {
                    String username = usernameET.getText().toString();
                    String password = passwordET.getText().toString();

                    /* Control if user exists */
                    if (db.ifUserExists(username, password)) {

                        /* A user object must be created so that the information about
                         * the user is the same for the whole application.
                         */
                        user = db.getUser(username);

                        /* If user exist the layout will be set to the start page */
                        Intent intent = new Intent(context, StartPage.class);
                        //intent.putExtra("user", user);
                        startActivity(intent);

                    }
                    else {
                        // If the user doesn't exist an alertbox will inform the user
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

                        dlgAlert.setMessage("The password or username is not correct, please try again!");
                        dlgAlert.setTitle("Wrong input");
                        dlgAlert.setPositiveButton("Try again", null);
                        dlgAlert.setCancelable(false);
                        dlgAlert.create().show();

                        dlgAlert.setPositiveButton("Try again",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                        }
                    }
                }
        );

        db.close();
    }

    /* Returns the current user. Method for other classes to use */
    public static DBUser getCurrentUser() {
        return user;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
