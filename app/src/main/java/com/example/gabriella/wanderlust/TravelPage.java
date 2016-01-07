package com.example.gabriella.wanderlust;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;


/**
 * <h1>TravelPage</h1>
 *
 * This class shows a specific travel item from the list of travel and is used when the user has
 * chosen to see information about a travel. The user has an opportunity to change the information
 * or delete the travel.
 *
 * @author  Gabriella Thorén
 * @version 1
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

public class TravelPage extends AppCompatActivity {

    /* For logging */
    private final static String LOG_TAG = TravelPage.class.getSimpleName();

    /* Current user */
    DBUser user;

    /* TravelID of the travel */
    int travelID;

    /* Current travel */
    DBTravel travel;

    /* Database Helper */
    SQLiteHelper db = new SQLiteHelper(this);

    /* Components */
    ImageButton saveButton;
    EditText    travelTitle;
    DatePicker  datePicker;
    ImageView   wallpaper;
    ImageButton remove;
    Button      delete;

    /* Path to the wallpaper image */
    Bitmap wallpaperBM;
    String picturePath;
    private static int RESULT_LOAD_IMAGE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "TravelPage.java");

        /* Get parameters which where passed through MainActitivy to get the current user */
        travelID = (int)   getIntent().getSerializableExtra("travelID");
        user     = (DBUser)getIntent().getSerializableExtra("user");

        /* Get current travel with the travelID */
        travel = db.getTravel(travelID, user);

        /* Reuse the layout for adding new travels */
        setContentView(R.layout.activity_add);

        /* Initiate components with the related xml-components */
        datePicker  = (DatePicker) findViewById(R.id.travel_date_picker);
        travelTitle = (EditText)   findViewById(R.id.travel_title);
        wallpaper   = (ImageView)  findViewById(R.id.backgroundSetter);
        remove      = (ImageButton)findViewById(R.id.remove_button);
        delete      = (Button)     findViewById(R.id.delete_button);

        /* Make delete button visible */
        delete.setVisibility(View.VISIBLE);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

        /*
         * Change the settings button in toolbar to an OK-button which the user clicks on when
         * done with their input for updating travel.
         */
        saveButton = (ImageButton)findViewById(R.id.settings);
        saveButton.setBackgroundResource(R.drawable.ic_check_box_white_24dp);

        /* Set onClickListener to button */
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        /* Set DatePicker to travel date */
        int year  = travel.getYear();
        int month = travel.getMonth();
        int day   = travel.getDay();
        datePicker.updateDate(year, month, day);

        /* Set title */
        travelTitle.setText(travel.getTitle());

        /* Set background */
        if (travel.getPicturePath() != null) {
            /* If there is a stored background */
            wallpaper.setImageBitmap(travel.getWallpaper());
            picturePath = travel.getPicturePath();

            /* Set visibility on remove image button if there are a image in ImageView */
            remove.setVisibility(View.VISIBLE);
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeImage();
                    }
                });
        }
        else {
            /* If there isn't a stored background */
            Bitmap image = BitmapFactory.decodeResource(this.getResources(), R.drawable.image);
            wallpaper.setImageBitmap(image);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * Method for deleting a specific travel item. This method is called when the user has pressed
     * the delete button. It controls if the user is sure about the action, and if he/she is the
     * travel is deleted and the user is taken back to the StartPage with the list of travels.
     *
     * @see DBTravel
     * @see SQLiteHelper#deleteTravel(DBTravel)
     * @see StartPage
     *
     *
     */
    public void delete() {

        /* AlertDialog to be sure that user wants to delete the travel */
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Remove travel");
        alertDialogBuilder
                .setMessage("Are you sure you want to delete this travel?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        db.deleteTravel(travel);

                        /* Go back to the StartPage when the new travel has been stored in the database */
                        Intent intent = new Intent(TravelPage.this, StartPage.class);
                        intent.putExtra("user", user);
                        startActivity(intent);

                        TravelPage.this.finish();
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
     * Method which removes the image that the user has picked as wallpaper. This method is called
     * when the user has pressed the button for deleting wallpaper.
     *
     * @see DBTravel
     * @see SQLiteHelper#deleteWallpaper(DBTravel)
     *
     */
    public void removeImage() {
        db.deleteWallpaper(travel);

        /* Default ImageView */
        Bitmap image = BitmapFactory.decodeResource(this.getResources(), R.drawable.image);
        wallpaper.setImageBitmap(image);

        picturePath = null;

    }

    /**
     * Method which creates alert dialog pop ups for giving a message to the user.
     *
     * @param message   text with the message to the user
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
     * Method for saving the travel. This method is called on when the user has pressed the save
     * button.
     *
     * @see DBTravel
     * @see StartPage
     * @see SQLiteHelper#updateTravel(DBTravel)
     */
    public void save() {

        /* Check if title is empty and alert user */
        if (travelTitle.getText().toString().matches("")) {
            alert("You did not write a title for this travel");
        }
        /* Check if text is longer then 17 characters */
        else if (travelTitle.getText().toString().length() > 17) {
            alert("The title is too long, maximum number of characters is 17");
        }
        /* If the input is correct */
        else {
            DBTravel updatedTravel;

            /* Get the user's text input */
            String title = travelTitle.getText().toString();
            int year     = datePicker .getYear();
            int month    = datePicker .getMonth();
            int day      = datePicker .getDayOfMonth();

            /* Initiate travel */
            if (picturePath != null) {
                /* If the user selected own image as wallpaper */
                updatedTravel = new DBTravel(travel.getTravelID(), title, year, month, day, picturePath);
            }
            else {
                /* If the user didn't select an image */
                updatedTravel = new DBTravel(travel.getTravelID(), title, year, month, day);
            }

            /* Insert the values in database */
            db.updateTravel(updatedTravel);

            /* Go back to the StartPage when the new travel has been stored in the database */
            Intent intent = new Intent(this, StartPage.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
    }

    /**
     * OnClickHandler for the floating action button in add activity so that the user can choose
     * a wallpaper for the travel, otherwice there will be a default picture. This method opens
     * the user's image gallery and retrieves the chosen image.
     *
     * @param view  the layout
     */
    public void wallpaperHandler(View view) {

        /* Initialize the ImageView with the related xml-component */
        wallpaper = (ImageView) findViewById(R.id.backgroundSetter);

        /* Triggers an intent for Image Gallery which the user can pick a picture */
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        /* Calls on the onActivityResult to get the image */
        startActivityForResult(i, RESULT_LOAD_IMAGE);

    }


    /* The method gets called once an image is selected of the user.  */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /* Checks if the the activity that was triggered was Image Gallery. To check this the method
         * user the integer RESULT_LOAD_IMAGE. */
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            /* Get data from the selected image */
            Uri selectedImage = data.getData();

            /* Set a string with the file path to the image */
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor c = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            c.moveToFirst();

            int columnIndex = c.getColumnIndex(filePathColumn[0]);
            picturePath = c.getString(columnIndex);
            c.close();

            /* Set ImageView to the new image */
            DBTravel t = new DBTravel();
            t.setWallpaper(picturePath);

            /* Catch if the file path is wrong and give an alert message to the user */
            try {
                wallpaperBM = t.getWallpaper(); // Must be done to get a resized Bitmap
                wallpaper.setImageBitmap(wallpaperBM);
            }
            catch(Exception e) {
                alert("There is something wrong with the filepath");
            }

            /* Set visibility on remove image button if there are a image in ImageView */
            remove.setVisibility(View.VISIBLE);

        }
    }
}
