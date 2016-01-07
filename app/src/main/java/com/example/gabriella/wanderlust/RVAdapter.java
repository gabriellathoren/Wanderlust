package com.example.gabriella.wanderlust;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;


/**
 * <h1>RVAdapter</h1>
 *
 *  Adapter that follows the view holder design pattern and reuses the card view layout (item.xml).
 *  The class is used for displaying the user's coming travels in a CardView/RecycleView.
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

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.TravelViewHolder> implements Serializable{

    /* List of travels */
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


    /**
     * Specifies the contents of each item of the RecyclerView. Sets the values of the title, days,
     * and background fields of the CardView.
     *
     * @param     travelViewHolder  the holder of the CardView and its components
     * @param     position          the position of the item in the list of DBTravel, which is shown
     *                              in the layout
     * @exception OutOfMemoryError
     * @see       DBTravel
     * @see       DBTravel#getTravelID()
     * @see       DBUser
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

        /* Days left to the travel */
        long diff = thatDay.getTimeInMillis() - today.getTimeInMillis();
        long days = diff / (24 * 60 * 60 * 1000);

        /* Set text with the total amount of days left to the travel */
        travelViewHolder.days.setText(String.valueOf(days));
        try {
            /* Set the image of the travel in the layout */
            travelViewHolder.background.setImageBitmap(t.getWallpaper());
        }
        catch (OutOfMemoryError e) {}

        /* OnClickListener to every item (travel) in the list. If one is pressed, the TravelPage
         * is called on which shows the information of the specific travel.
         */
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

    /**
     * This method is called when the ViewHolder needs to be initialized. The method sets the layout
     * to the item layout, which is the layout for every row in the CardView, to the activity.
     *
     * @param viewGroup  the view group of the layout
     * @param i          the position of the item (travel) in the list
     */
    @Override
    public TravelViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new TravelViewHolder(v);
    }

    /**
     *  The method creates an adapter and initialize the components of the card view.
     */
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
        }
    }

    /**
     * Connects to the RecyclerView
     *
     * @param recyclerView      the recycler view which the travel list will be displayed in
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
