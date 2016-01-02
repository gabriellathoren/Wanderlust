/**
 * Created by Gabriella on 2015-11-30.
 */

package com.example.gabriella.wanderlust;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import java.util.ArrayList;

import java.util.List;


public class StartPage extends AppCompatActivity {

    /* Layout components */
    private List<DBTravel> travels;
    private RecyclerView rv;
    private RVAdapter ra;

    /* Current user */
    DBUser user;

    /* Database Helper */
    SQLiteHelper db = new SQLiteHelper(this);

    /* Set context to this */
    final Context context = this;

    /* For logging */
    private final static String LOG_TAG = StartPage.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "StartPage");

        /* Get parameters which where passed through MainActitivy to get the current user */
        user = (DBUser)getIntent().getSerializableExtra("user");

        /* Set view to activity_start */
        setContentView(R.layout.activity_start);

        /* Create RecyclerView for listing the user's travels */
        rv = (RecyclerView)findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        /* Create LayoutManager and Adapter which RecyclerView require */
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        travels = db.getTravels(user, context); /* Create list with the amount of objects the user has */
        ra = new RVAdapter(travels, context);
        rv.setAdapter(ra);

    }

    /* Sets view to AddActivity when user presses the Floating Action Button for adding travels */
    public void addHandler(View v) {
        Intent intent = new Intent(this, AddActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Adds items to the actionbar if it is present */
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    /* Method is called when an item in the action bar is selected. */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    /* Method that shows new activity after clicking cardview
    public void travelLayout(View view) {
        Intent intent = new Intent(this, TravelPage.class);
        startActivity(intent);
    }*/
}
