/**
 * Created by Gabriella on 2015-11-30.
 */

package com.example.gabriella.wanderlust;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.ListActivity;
import android.widget.ArrayAdapter;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.AdapterView;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class StartPage extends AppCompatActivity {

    TextView title;
    TextView days;
    ImageView background;
    private List<Travel> travels;
    private RecyclerView rv;

    private final static String LOG_TAG = StartPage.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

         super.onCreate(savedInstanceState);
         Log.d(LOG_TAG, "");

         /* Set view to activity_start */
         setContentView(R.layout.activity_start);

         /* Create RecyclerView for listing travels */
         RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
         rv.setHasFixedSize(true);

         /* Create LayoutManager and Adapter which RecyclerView require */
         LinearLayoutManager llm = new LinearLayoutManager(this);
         llm.setOrientation(LinearLayoutManager.VERTICAL);
         rv.setLayoutManager(llm);
         RVAdapter ra = new RVAdapter(createList(3)); /* Create list with the amount of objects the user has */
         rv.setAdapter(ra);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        /* Adds items to the actionbar if it is present */
        getMenuInflater().inflate(R.menu.my, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Travel> createList(int size) {

        List<Travel> result = new ArrayList<Travel>();
        for (int i=1; i <= size; i++) {
            Travel t = new Travel();
            t.title = "Paris";
            t.days = "3";
            t.background = R.drawable.wallpaper;

            result.add(t);

        }

        return result;
    }


    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(travels);
        rv.setAdapter(adapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    /* Method that shows new activity after clicking cardview */
    public void travelLayout(View view) {
        Intent intent = new Intent(this, TravelPage.class);
        startActivity(intent);
        //setContentView(R.layout.activity_start);
    }


}
