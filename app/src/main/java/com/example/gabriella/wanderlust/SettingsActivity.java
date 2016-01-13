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
 * <h1>SettingsActivity</h1>
 *
 *  This class handles the settings activity where the user is able to make changes in their
 *  saved information, delete their account or log out from the application.
 *
 *  @author     Gabriella Thorén
 *  @version    1
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
 *
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
    ImageButton   return_button;
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

        /*
         * Change the button in toolbar to an OK-button which the user clicks on when
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
        firstname     = (EditText)    findViewById(R.id.first_name);
        sirname       = (EditText)    findViewById(R.id.sir_name);
        username      = (EditText)    findViewById(R.id.username);
        deactivate    = (Button)      findViewById(R.id.deactivate_account);
        oldPassword   = (EditText)    findViewById(R.id.old_password);
        newPassword   = (EditText)    findViewById(R.id.new_password);
        newPassword2  = (EditText)    findViewById(R.id.new_password2);
        return_button = (ImageButton) findViewById(R.id.return_button);

        /* Set onClickListener to return button */
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        /* Set EditTexts with data */
        firstname.setText(user.getFirstName());
        sirname  .setText(user.getLastName ());
        username .setText(user.getUsername ());

        /* Set isSelected to false first time, because the button will not have been pushed yet */
        isSelected = false;
        firstClick = true;

    }

    /**
     * OnClickListener for the save button. When the user is ready to change its settings and
     * presses the save button, this method will control the user input. If there is something
     * wrong with the input, the user will get alerted. If the input is correct, the user
     * information will be updated.
     *
     * @see DBUser
     * @see SQLiteHelper#ifUserExists(String)
     * @see DBUser#getUsername()
     * @see DBUser#getUserID()
     * @see DBUser#getPassword()
     * @see #alert(String)
     * @see #update(DBUser)
     */
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

    /**
     * This method updates the user information in the database.
     *
     * @param updatedUser   the new user information, but with the same user id as before
     * @see   SQLiteHelper#updateUser(DBUser)
     */
    public void update(DBUser updatedUser) {

        db.updateUser(updatedUser);

        Toast.makeText(this, "saved", Toast.LENGTH_LONG).show();

        /* Go back to AccountActivity */
        Intent intent = new Intent(this, AccountActivity.class);
        intent.putExtra("user", updatedUser);
        startActivity(intent);
    }

    /**
     * Method which creates alert dialogs with messengers to the user.
     *
     * @param message   the message to the user
     */
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


    /**
     *  Method which is called when user pressed the button for changing password. If that button
     *  is pressed the text fields for password changing is either shown or hided depending on its
     *  previous state. Ff the text fields is shown, the deactivate button must be moved to make
     *  room for the text fields.
     *
     *
     *  @param view     layout
     */
    public void changeButtonHandler(View view) {

        /*
         * Checks if it is the first click, otherwice the user must click twice before the isSelected
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

        /* To open change password */
        if(isSelected) {
            /* Change the state to false */
            isSelected = false;

            /* Make textfields visible */
            oldPassword .setVisibility(View.VISIBLE);
            newPassword .setVisibility(View.VISIBLE);
            newPassword2.setVisibility(View.VISIBLE);

            RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);

            /*
             * Set button for deactivating account bellow the password text fields to make room for
             * the components that is now visible.
             */
            p.addRule(RelativeLayout.BELOW, R.id.new_password2);
            deactivate.setLayoutParams(p);

        }
        /* To close change password */
        else {
            /* Change the state to true */
            isSelected = true;

            /* Make textfield invisible */
            oldPassword .setVisibility(View.INVISIBLE);
            newPassword .setVisibility(View.INVISIBLE);
            newPassword2.setVisibility(View.INVISIBLE);

            RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            /*
             * Set button for deactivating account bellow the password text fields delete the space
             * where the password text fields was.
             */
            p.addRule(RelativeLayout.BELOW, R.id.change_password);
            deactivate.setLayoutParams(p);
        }

    }

    /**
     *  Method for logging out the user. This method is called when the user has pressed the
     *  log out button. The user is then taken to the log in activity.
     *
     *  @param view     layout
     *  @see            MainActivity
     */
    public void logOut(View view) {

        /* AlertDialog to be sure that user wants to delete the travel */
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Log out");
        alertDialogBuilder
                .setMessage("Are you sure you want to log out?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        /* create alert dialog and show it */
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    /**
     * Method for deactivating account which is called when the user has pressed the button for
     * deactivating account. The user gets alerted about the processes to assure that it is what
     * the user really wants to do before deleting the user from the application. When that is done
     * the user gets pulled back to the log in activity.
     *
     * @param view  layout
     * @see         MainActivity
     * @see         SQLiteHelper#deleteUser(DBUser)
     */
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


    /**
     * Override method which is called when the user is trying to return to previous page. If the
     * user has made changes to the travel, an alert dialog will warn the user of his or hers
     * actions to make sure that important changes is not lost before returning to previous activity.
     *
     * @see DBUser
     */

    @Override
    public void onBackPressed() {


        if( (!user.getFirstName().matches(firstname.getText().toString())) ||
            (!user.getLastName() .matches(sirname  .getText().toString())) ||
            (!user.getUsername() .matches(username .getText().toString()))) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            alertDialogBuilder.setTitle("Exit");
            alertDialogBuilder
                    .setMessage("Are you sure you want go back to the previous page? Your changes will not be saved.")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {


                           /* Go back to the StartPage when the new travel has been stored in the database */
                            Intent intent = new Intent(SettingsActivity.this, AccountActivity.class);
                            intent.putExtra("user", user);
                            startActivity(intent);

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

                /* create alert dialog and show it */
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }

        /* Check if user has tried to change password */
        else if( (oldPassword.getVisibility() == View.VISIBLE)  &&
                (!oldPassword.getText().toString().matches("")) &&
                (!newPassword.getText().toString().matches("")) &&
                (!newPassword.getText().toString().matches(""))) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                alertDialogBuilder.setTitle("Exit");
                alertDialogBuilder
                        .setMessage("Are you sure you want go back to the previous page? Your changes will not be saved.")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                               /* Go back to the AccountActivity when the new travel has been stored in the database */
                                Intent intent = new Intent(SettingsActivity.this, AccountActivity.class);
                                intent.putExtra("user", user);
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                /* create alert dialog and show it */
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

        }
        else{
            /* Go back to the AccountActivity when the new travel has been stored in the database */
            Intent intent = new Intent(SettingsActivity.this, AccountActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
    }





}
