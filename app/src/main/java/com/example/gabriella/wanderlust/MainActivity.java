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

    /* If the user have forgotten their login. This function does not work! */
    public void forgotLogin(View view) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

        dlgAlert.setMessage("Unfortunately, this function does not work yet!");
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

    /* If the user wants to register the RegisterActivity will start */
    public void register() {
        Intent intent = new Intent(context, RegisterActivity.class);
        startActivity(intent);
    }

    /* If the user tries to log in their input will be controlled. When the input is accepted
     * a new activity will start.
     */
    public void logIn() {
        /* Get the user's text input */
        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();

        /* Control if user exists */
        if (db.ifUserExists(username, password)) {

            /* A user object must be created so that the information about
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

    @Override
    protected void onStart() {
        super.onStart();
    }

}
