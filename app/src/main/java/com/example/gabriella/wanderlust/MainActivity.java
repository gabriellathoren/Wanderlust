package com.example.gabriella.wanderlust;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.view.Window;
import android.content.Intent;
import java.util.EventListener;


public class MainActivity extends AppCompatActivity {


    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    //För att logga, vilket är bra när man ska ta fram funktionalitet för att vi ska kunna se att
    //det funkar. Genom denna kan man skriva ut loggutskrifter.
    //Denna består av en static string eftersom den ska vara gemensam för alla objekt. Den är även
    //privat så ingen annan behöver känna till den. Den deklareras även som final eftersom man inte
    //ska kunna röra på den.
    //Denna sätts till MainActivity eftersom det är min klass som jag befinner mig i och därifrån
    // hämtas den information som behövs.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
    Button btn1 = (Button) findViewById(R.id.login_button);
    btn1.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            setContentView(R.layout.activity_start);
        }
    });

    /* Method for log in and show new activity*/
    public void logIn(View view) {
        Intent intent = new Intent(this, StartPage.class);
        startActivity(intent);
        //setContentView(R.layout.activity_start);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
