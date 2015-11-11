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

public class MainActivity extends AppCompatActivity {

    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    //För att logga, vilket är bra när man ska ta fram funktionalitet för att vi ska kunna se att
    //det funkar. Genom denna kan man skriva ut loggutskrifter.
    //Denna består av en static string eftersom den ska vara gemensam för alla objekt. Den är även
    //privat så ingen annan behöver känna till den. Den deklareras även som final eftersom man inte
    //ska kunna röra på den.
    //Denna sätts till MainActivity eftersom det är min klass som jag befinner mig i och därifrån
    // hämtas den information som behövs.

    private int colIndex;
    //Detta är en instansvariabel, dessa sätts alltid till 0 automatiskt och därför behöver inte vi
    //sätta den till 0.


    private static final String COL_INDEX_KEY = "ColorIndex";
    //En nyckel för att kunna använda SharedPreferences. Vad den har för värde spelar absoult ingen
    // roll eftersom det bara är COL_INDEX_KEY-namnet som används. Det skulle gå att enbart använda
    // "ColorIndex" i resten av koden men då kommer det inte ske några varningar om jag skulle
    // skriva något fel eftersom det är en int. Om jag däremot använder variabeln COL_INDEX_KEY
    // kommer jag få felmeddelanden.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected	void onStart() { //Används när jag startar upp appen
        super.onStart();
        
        colIndex = getButtonColorIndex();
        //hämtar colIndex för att senare kunna sätta bakgrundsfärgen


        Log.d(LOG_TAG, "   index: " + colIndex);

        setButtonColor();
        //sätter färgen, men då går den på colIndex vilket innebär att jag måste
        // sätta färgen själv. Därför används colIndex=getButtonColorIndex();
    }



    public void setButtonColor() {
        Button b = (Button) findViewById(R.id.nok_button);
        // Button är en knapp. B är en referens till en knapp.
        // Denna knapp refererar till nok_button och kopplas till den för att kunna ändra dess färg


        RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainLayout);
        //Hämtar en relativ layout. findViewByID ska hitta en vy genom att ta emot parametrarna
        //R(resources).id.idnamnet



        //För att ändra färger på knappen:
        switch (colIndex) {
            case 1:
                b.setBackgroundColor(getResources().getColor(R.color.red));
                break;
            case 2:
                b.setBackgroundColor(getResources().getColor(R.color.schneebly));
               /*      Button bt = new Button(this); //lägger till en ny knapp
                bt.setText("Ozzy Osbonn"); //knappnamnet
                layout.addView(bt);
                */
                break;
            default:
                b.setBackgroundColor(getResources().getColor(R.color.annoy));
                colIndex = 0;
        }
        saveButtonColorIndex();
        colIndex++;
    }

    public void saveButtonColorIndex() {
        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(COL_INDEX_KEY, colIndex);
        editor.commit();
    }

    public int getButtonColorIndex() {
        SharedPreferences settings =
                getPreferences(Context.MODE_PRIVATE);
        Log.d(LOG_TAG, "   buttonColorIndex: " + colIndex);
        int idx = settings.getInt(COL_INDEX_KEY, 0);
        return idx;
    }

    public void notOkHandler(View v) {  //hanterar vad som ska hända när man trycker på NOK-knappen.
                                        // Parametrarna är en view som är samlingsklassen som tar
                                        // emot allting som syns på en skärm

        Log.d(LOG_TAG, "Not ok button clicked index: " + colIndex + "  saved: " + getButtonColorIndex());
        //Log är ett interface som finns i Android och .d står för debug. Parametrarna består av en
        //tagg och ett meddelande. Denna metod anropas när användaren har klickat på knappen.


        setButtonColor(); //anropar funktionen som byter färg på knappen
    }
}
