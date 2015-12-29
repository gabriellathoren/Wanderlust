package com.example.gabriella.wanderlust;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;

/**
 * This class handles the activity for adding travels.
 */

public class AddActivity extends AppCompatActivity {

    /* Current user */
    DBUser user;

    /* New travel object */
    DBTravel travel;

    /* Database Helper */
    SQLiteHelper db = new SQLiteHelper(this);

    /* For logging */
    private final static String LOG_TAG = StartPage.class.getSimpleName();

    /* Components */
    ImageButton imageButton;
    EditText    travelTitle;
    DatePicker  datePicker;
    ImageView   wallpaper;

    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "Add Activity");

        /* Set layout to activity_add */
        setContentView(R.layout.activity_add);

        /* Initialize database helper */
        db = new SQLiteHelper(getApplicationContext());

        /* Get parameters which where passed through StartPage to get the current user */
        user = (DBUser)getIntent().getSerializableExtra("user");

        /* Change the settings button in toolbar to an OK-button which the user clicks on when
         * done with their input for adding travel
         */
        imageButton = (ImageButton)findViewById(R.id.settings);
        imageButton.setBackgroundResource(R.drawable.ic_check_box_white_24dp);

        imageButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {

                        /* Get the user's text input */
                        String title = travelTitle.getText().toString();

                        datePicker = (DatePicker) findViewById(R.id.travel_date_picker);
                        String  travelDate = "" + datePicker.getYear() + "-" + datePicker.getMonth() + "-" + datePicker.getDayOfMonth();

                    }
                }
        );
    }


    /* OnClickHanlder for the floating action button in activity_add so that the user can choose
     * a wallpaper for the travel, otherwice there will be an standard picture.
     */
    public void wallpaperHandler(View view) {

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

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            /* Set imageview to the new image */
            wallpaper.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
