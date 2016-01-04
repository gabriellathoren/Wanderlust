package com.example.gabriella.wanderlust;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.opengl.GLES10;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.microedition.khronos.opengles.GL10;

/**
 *  Adapter that follows the view holder design pattern and reuses the card view layout (item.xml).
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.TravelViewHolder> implements Serializable{

    private List<DBTravel> travels;

    /* Return the number of items present in the data. */
    @Override
    public int getItemCount() {
        return travels.size();
    }

    /* Context, is needed for starting new activity */
    private final Context context;

    /* Current user */
    private DBUser user;


    /* Constructor to the custom adapter that handle the data that the RecyclerView displays */
    public RVAdapter(List<DBTravel> travels, Context context, DBUser user){
        this.travels = travels;
        this.context = context;
        this.user    = user;
    }


    /* Specifies the contents of each item of the RecyclerView. Sets the values of the title, days,
     * and background fields of the CardView.
     */
    @Override
    public void onBindViewHolder(TravelViewHolder travelViewHolder, int position) {
        final DBTravel t = travels.get(position);

        /* Set title */
        travelViewHolder.title.setText(t.getTitle());

        /* Travel date */
        Calendar thatDay = Calendar.getInstance();
        thatDay.set(Calendar.DAY_OF_MONTH, t.getDay());
        thatDay.set(Calendar.MONTH,        t.getMonth()); // 0-11 so 1 less
        thatDay.set(Calendar.YEAR,         t.getYear());

        /* Today's date */
        Calendar today = Calendar.getInstance();

        long diff = thatDay.getTimeInMillis() - today.getTimeInMillis();
        long days = diff / (24 * 60 * 60 * 1000);

        travelViewHolder.days.setText(String.valueOf(days));
        travelViewHolder.background.setImageBitmap(t.getResizedWallpaper());

        travelViewHolder.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TravelPage.class);
                intent.putExtra("travelID", t.getTravelID());
                intent.putExtra("user", user);
                context.startActivity(intent);
            }
        });

    }

    /* This method is called when the custom ViewHolder needs to be initialized. Each item of the
     * RecyclerView should get used. This is done by inflating the layout using LayoutInflater,
     * passing the output to the constructor of the custom ViewHolder
     */
    @Override
    public TravelViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new TravelViewHolder(v);
    }

    /* Create the adapter */
    public static class TravelViewHolder extends RecyclerView.ViewHolder {

        protected CardView  cv;
        protected TextView  title;
        protected TextView  days;
        protected ImageView background;

        TravelViewHolder(View itemView) {
            super(itemView);

            cv         = (CardView) itemView.findViewById(R.id.card_view);
            title      = (TextView) itemView.findViewById(R.id.title);
            days       = (TextView) itemView.findViewById(R.id.days);
            background = (ImageView)itemView.findViewById(R.id.background);
            background.setImageAlpha(80); // Opacity of background
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
