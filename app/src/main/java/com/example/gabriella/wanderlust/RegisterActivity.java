package com.example.gabriella.wanderlust;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

/**
 * <h1>RegisterActivity</h1>
 *
 * Java class which handles user registrations.
 *
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
public class RegisterActivity extends AppCompatActivity {

    /* Database Helper */
    SQLiteHelper db;

    /* For logging */
    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    /* Set context to this */
    final Context context = this;

    /* New user */
    DBUser user;

    /* Components */
    EditText usernameET;
    EditText passwordET;
    EditText password2ET;
    EditText firstNameET;
    EditText lastNameET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Code for not making window resize when user gives text input */
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        /* Sets view to the layout activity_main */
        setContentView(R.layout.activity_register);

        /* Initialize database helper */
        db = new SQLiteHelper(getApplicationContext());

        /* Sets Button and EditTexts to the related buttons and EditTexts in xml-code */
        usernameET  = (EditText)findViewById(R.id.reg_username);
        passwordET  = (EditText)findViewById(R.id.reg_password);
        password2ET = (EditText)findViewById(R.id.reg_verify_password);
        firstNameET = (EditText)findViewById(R.id.reg_first_name);
        lastNameET  = (EditText)findViewById(R.id.reg_sir_name);
    }

    /**
     * Method which controls the user input and registers the user by store the information in the
     * database.
     *
     * @param view      the layout
     * @see   DBUser
     * @see   SQLiteHelper#ifUserExists(String)
     * @see   SQLiteHelper#createUser(DBUser)
     */
    public void register(View view) {

        /* Get the user's text input */
        String username  = usernameET .getText().toString();
        String password  = passwordET .getText().toString();
        String password2 = password2ET.getText().toString();
        String firstname = firstNameET.getText().toString();
        String lastname  = lastNameET .getText().toString();

        /* Control if there are empty EditTexts */
        if (username.matches("") || password.matches("") || password2.matches("") || firstname.matches("") || lastname.matches("")) {
            alert("Please fill in all text fields");
        }

        /* Control if user exists */
        else if (db.ifUserExists(username)){
            alert("This e-mail is already registered");
        }

        /* Control if the password is correct to minimize the risks for wrong password input */
        else if(!password.matches(password2)) {
            alert("The password's does not match!");
        }

        /* If password isn't long enough */
        else if(password.length() < 8) {
            alert("The password is to short! Please select a password with minimum of 8 characters");
        }

        /* Control if username is an email by matching it with email pattern */
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            alert("The e-mail is invalid!");
        }

        /* If all input is accepted */
        else {
            DBUser user = new DBUser(username, password, firstname, lastname);
            db.createUser(user);

            /* AlertDialog to be sure that user wants to delete the travel */
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            alertDialogBuilder.setTitle("Welcome to Wanderlust");
            alertDialogBuilder
                    .setMessage("You are now registered")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {

                            /* Go back to MainActivity */
                            Intent intent = new Intent(context, MainActivity.class);
                            startActivity(intent);

                            /* Finish to not be able to go back */
                            RegisterActivity.this.finish();

                        }
                    });

            /* create alert dialog and show it */
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    /**
     * Method which alerts user with messengers via AlertDialog.
     *
     * @param message   message for the user
     */
    public void alert(String message) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

        dlgAlert.setMessage(message);
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

    @Override
    protected void onStart() {
        super.onStart();
    }
}
