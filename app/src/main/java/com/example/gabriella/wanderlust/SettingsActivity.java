package com.example.gabriella.wanderlust;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Set;

/**
 *  Class which handles the settings activity where the user is able to make changes in their
 *  saved information, delete their account or log out.
 */
public class SettingsActivity extends AppCompatActivity {

    /* For logging */
    private final static String LOG_TAG = TravelPage.class.getSimpleName();

    /* Current user */
    DBUser user;

    /* Database Helper */
    SQLiteHelper db = new SQLiteHelper(this);

    /* Components */
    RelativeLayout rl;
    ImageButton   saveButton;
    EditText      firstname;
    EditText      sirname;
    EditText      username;
    EditText      oldPassword;
    EditText      newPassword;
    EditText      newPassword2;
    Button        deactivate;

    /* Controls if the button for changing password is selected or not */
    Boolean isSelected;
    Boolean firstClick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "TravelPage.java");

        /* Get parameters which where passed through MainActitivy to get the current user */
        user = (DBUser)getIntent().getSerializableExtra("user");

        /* Reuse the layout for adding new travels */
        setContentView(R.layout.activity_settings);

        /* Change the button in toolbar to an OK-button which the user clicks on when
         * done with their input for updating travel.
         */
        saveButton = (ImageButton)findViewById(R.id.settings);
        saveButton.setBackgroundResource(R.drawable.ic_check_box_white_24dp);
        saveButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        update();
                    }
                }
        );

        /* Initialize components with their respective xml-code */
        firstname    = (EditText) findViewById(R.id.first_name);
        sirname      = (EditText) findViewById(R.id.sir_name);
        username     = (EditText) findViewById(R.id.username);
        deactivate   = (Button)   findViewById(R.id.deactivate_account);
        oldPassword  = (EditText) findViewById(R.id.old_password);
        newPassword  = (EditText) findViewById(R.id.new_password);
        newPassword2 = (EditText) findViewById(R.id.new_password2);

        /* Set EditTexts with data */
        firstname.setText(user.getFirstName());
        sirname  .setText(user.getLastName ());
        username .setText(user.getUsername ());

        /* Set isSelected to false first time, because the button will not have been pushed yet */
        isSelected = false;
        firstClick = true;

    }

    /* OnClickListener for the save button */
    public void update() {

        /* Get user input */
        String fName  = firstname   .getText().toString();
        String sName  = sirname     .getText().toString();
        String uName  = username    .getText().toString();
        String oPass  = oldPassword .getText().toString();
        String nPass  = newPassword .getText().toString();
        String nPass2 = newPassword2.getText().toString();

        /* Check if their are empty obligatory fields */
        if(fName.matches("") || sName.matches("") || uName.matches("")) {
            alert("Please complete all text fields");
        }
        /* Check the user has written a new username, but that one already exists in database */
        else if(!uName.matches(user.getUsername()) && db.ifUserExists(uName)) {
            alert("This e-mail is already registered!");
        }

        /* Control if username is an email by matching it with email pattern */
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(uName).matches()) {
            alert("The e-mail is invalid!");
        }
        /* Check if the user wants to change password. */
        else if(oldPassword.getVisibility() == View.VISIBLE) {

            /* If all password fields are empty, nothing will happen because we presuppose that the
             * user never wanted to change password and did not press that button on purpose.
             */
            if(!oPass.matches("") || !nPass.matches("") || !nPass2.matches("")) {

                if(!oPass.matches(user.getPassword())) {
                    alert("The old password is not correct!");
                }
                else if(oPass.length() < 8 || nPass.length() < 8 || nPass2.length() < 8) {
                    alert("The password must be longer then 8 characters");
                }
                else if(!nPass.equals(nPass2)) {
                    alert("The new password does not match");
                }
                /* If everything worked, this will set the new password in user-object */
                else {
                    user.setPassword(nPass);
                    update(new DBUser(user.getUserID(), uName, user.getPassword(), fName, sName));
                }
            }
            else{
                update(new DBUser(user.getUserID(), uName, user.getPassword(), fName, sName));
            }
        }
        /* If the input was correct */
        else {
            update(new DBUser(user.getUserID(), uName, user.getPassword(), fName, sName));
        }
    }

    public void update(DBUser updatedUser) {

        db.updateUser(updatedUser);

        Toast.makeText(this, "saved", Toast.LENGTH_LONG).show();

        /* Go back to AccountActivity */
        Intent intent = new Intent(this, AccountActivity.class);
        intent.putExtra("user", updatedUser);
        startActivity(intent);
    }

    public void alert(String message) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

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


    /* Method which is called when user pressed the button for changing password */
    public void changeButtonHandler(View view) {

        /* Checks if it is the first click, otherwice the user must click twice before the isSelected
         * becomes true.
         */
        if(firstClick) {
            isSelected = true;
            firstClick = false;
        }

        /* Create new EditTexts for the user to write the new password in */
        oldPassword  = (EditText) findViewById(R.id.old_password);
        newPassword  = (EditText) findViewById(R.id.new_password);
        newPassword2 = (EditText) findViewById(R.id.new_password2);

        if(isSelected) {
            isSelected = false;
            oldPassword .setVisibility(View.VISIBLE);
            newPassword .setVisibility(View.VISIBLE);
            newPassword2.setVisibility(View.VISIBLE);

            RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);

            p.addRule(RelativeLayout.BELOW, R.id.new_password2);
            deactivate.setLayoutParams(p);

        }
        else {
            isSelected = true;
            oldPassword .setVisibility(View.INVISIBLE);
            newPassword .setVisibility(View.INVISIBLE);
            newPassword2.setVisibility(View.INVISIBLE);

            RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            p.addRule(RelativeLayout.BELOW, R.id.change_password);
            deactivate.setLayoutParams(p);
        }

    }

    /* Method for logging out */
    public void logOut(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /* Method for deactivating account */
    public void deactivateAccount(View view) {
        /* AlertDialog to be sure that user wants to delete the travel */
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Delete account");
        alertDialogBuilder
                .setMessage("Are you sure you want to delete your account?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        db.deleteUser(user);

                        /* Go back to the StartPage when the new travel has been stored in the database */
                        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                        startActivity(intent);

                        SettingsActivity.this.finish();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });

        /* create alert dialog and show it */
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}