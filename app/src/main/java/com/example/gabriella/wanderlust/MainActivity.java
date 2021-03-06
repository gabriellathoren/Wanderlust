package com.example.gabriella.wanderlust;

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

/**
 * <h1>MainActivity</h1>
 *
 * This class displays the first activity of the application, which is the log in activity. In this
 * activity the user can log in on its account or register as an user.
 *
 * @author  Gabriella Thorén
 * @version 1
 *
 *
 * Wanderlust - the application that keeps track of your travels.
 * Copyright (C) 2016 Gabriella Thorén
 * <p>
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

public class MainActivity extends AppCompatActivity  {

    /* Database Helper */
    SQLiteHelper db;

    /* Buttons and other objects in layout */
    Button   loginButton;
    EditText usernameET;
    EditText passwordET;
    Button   register;

    /* Set context to this */
    final Context context = this;

    /* Current user */
    DBUser user;

    /* For logging */
    private final static String LOG_TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        /* Code for not making window resize when user gives text input */
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        /* Sets view to the layout activity_main */
        setContentView(R.layout.activity_main);

        /* Initialize database helper */
        db = new SQLiteHelper(getApplicationContext());

        /* Sets Button and EditTexts to the related buttons and EditTexts in xml-code */
        loginButton = (Button)  findViewById(R.id.login_button);
        usernameET  = (EditText)findViewById(R.id.usernameID);
        passwordET  = (EditText)findViewById(R.id.passwordID);
        register    = (Button)  findViewById(R.id.registerButton);

        /* OnClickListeners */
        loginButton.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    logIn();
                }
            }
        );

        register.setOnClickListener (
            new View.OnClickListener() {
                public void onClick(View view) {
                    register();
                }
            }
        );

        /* Close database */
        db.close();
    }

    /**
     * Method which is called on when the users does not remember their log in information and
     * has pressed the represented button. This function does not work!
     *
     * @param view  the layout
     */
    public void forgotLogin(View view) {
        alert("Unfortunately, this function does not work yet!");
    }

    /**
     * Method for alerting the user with information with an pop up.
     *
     * @param message   The message to the user
     */
    public void alert(String message) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

        dlgAlert.setMessage(message);
        dlgAlert.setTitle("Oops");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(false);
        dlgAlert.create().show();

        dlgAlert.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
    }

    /**
     * If the user wants to register and pressed the register button, the RegisterActivity will start.
     *
     * @see RegisterActivity
     */
    public void register() {
        Intent intent = new Intent(context, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * If the user tries to log in by pressing the log in button, their input will be controlled.
     * When the input is accepted the next activity (StartPage) will start.
     *
     * @see StartPage
     * @see SQLiteHelper#ifUserExists(String, String)
     * @see SQLiteHelper#getUser(String)
     * @see #alert(String)
     */
    public void logIn() {

        /* Get the user's text input */
        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();

        /* Control if user exists */
        if (db.ifUserExists(username, password)) {

            /*
             * A user object must be created so that the information about
             * the user is the same for the whole application.
             */
            user = db.getUser(username);

            Intent intent = new Intent(context, StartPage.class);
            intent.putExtra("user", user);

            /* If user exist the layout will be set to the start page */
            startActivity(intent);
            finish();

        }
        else {
            /* If the user doesn't exist an alertbox will inform the user */
            alert("The password or username is not correct, please try again!");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    /**
     * Override method that is called on when user presses the return button. The method is empty
     * because the user is not allowed to return to previous activity from the log in activity. This
     * to prevent users from logging out from their account and returning without logging in.      *
     */
    @Override
    public void onBackPressed() {}

}
