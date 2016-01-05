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
 * Java class which handles user registrations.
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

    public void register(View view) {

        /* Get the user's text input */
        String username  = usernameET .getText().toString();
        String password  = passwordET .getText().toString();
        String password2 = password2ET.getText().toString();
        String firstname = firstNameET.getText().toString();
        String lastname  = lastNameET .getText().toString();

        /* Control if there are empty EditTexts */
        if (username.matches("") || password.matches("") || password2.matches("") || firstname.matches("") || lastname.matches("")) {

            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

            dlgAlert.setMessage("Please fill in all text fields");
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

        /* Control if user exists */
        else if (db.ifUserExists(username)){

            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

            dlgAlert.setMessage("This e-mail is already registered");
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

        /* Control if the password is correct to minimize the risks for wrong password input */
        else if(!password.matches(password2)) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

            dlgAlert.setMessage("The password's does not match!");
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

        /* If password isn't long enough */
        else if(password.length() < 8) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

            dlgAlert.setMessage("The password is to short! Please select a password with minimum of 8 characters");
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

        /* Control if username is an email by matching it with email pattern */
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

            dlgAlert.setMessage("The e-mail is invalid!");
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

        /* If all input is accepted */
        else {
            DBUser user = new DBUser(username, password, firstname, lastname);
            db.createUser(user);

            /* Go back to MainActivity */
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);

            /* Finish to not be able to go back */
            finish();
        }
    }
}
