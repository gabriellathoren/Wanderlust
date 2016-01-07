package com.example.gabriella.wanderlust;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageButton;

import java.util.List;

/**
 *  <h1>StartPage</h1>
 *
 *  This class handles the start page of the application which displays a list of travels that
 *  the user has created. This class contains the whole layout for the travels that is listed in a
 *  CardView and RecyclerView and is connected to an adapter which fills the rows with the
 *  correct data and row layout.
 *
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


public class StartPage extends AppCompatActivity {

    /* Layout components */
    List<DBTravel> travels;
    RecyclerView   rv;
    RVAdapter      ra;
    ImageButton    account;

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
        ra = new RVAdapter(travels, context, user);
        rv.setAdapter(ra);

        /* OnClickListener to user account */
        account = (ImageButton)findViewById(R.id.settings);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                account();
            }
        });


    }

    /**
     *  This method is called when the user pressed the account button and starts the activity for
     *  showing the user her/his personal account.
     *
     *  @see AccountActivity
     *  @see DBUser
     */
    public void account() {
        Intent intent = new Intent(this, AccountActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    /**
     *  Sets view to AddActivity when user presses the Floating Action Button for adding travels.
     *
     *  @see AddActivity
     *  @see DBUser
     */
    public void addHandler(View v) {
        Intent intent = new Intent(this, AddActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    /**
     * This menu initiating the menu's xml-file.
     *
     * @param  menu     the menu in java code
     * @return Boolean  returns <code>true</code> if the menu is initiated
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Adds items to the actionbar */
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    /**
     *  Method is called when an item is selected and returns the selected option.
     *
     *  @param  item     the item that has gotten selected
     *  @return Boolean  returns <code>true</code> if the specific option is selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
