package com.example.gabriella.wanderlust;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *  Adapter that follows the view holder design pattern and reuses the card view layout (item.xml).
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.TravelViewHolder> {

    private List<DBTravel> travels;

    /* Constructor to the custom adapter that handle the data that the RecyclerView displays */
    public RVAdapter(List<DBTravel> travels){
        this.travels = travels;
    }

    /* Return the number of items present in the data. */
    @Override
    public int getItemCount() {
        return travels.size();
    }

    /* Specifies the contents of each item of the RecyclerView. Sets the values of the title, days,
     * and background fields of the CardView.
     */
    @Override
    public void onBindViewHolder(TravelViewHolder travelViewHolder, int i) {
        DBTravel t = travels.get(i);

        /* Set title */
        travelViewHolder.title.setText(t.getTitle());

        /* Today's date */
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = dateFormat.format(date);

        /* Travel date */
        String travelDate = t.getYear() + "-" + t.getMonth() + "-" + t.getDay();

        try {
            Date date1 = dateFormat.parse(today);
            Date date2 = dateFormat.parse(travelDate);
            long diff  = date2.getTime() - date1.getTime();
            long days  = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            travelViewHolder.days.setText(String.valueOf(days));
            travelViewHolder.background.setImageBitmap(t.getWallpaper());
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
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

            cv         = (CardView)itemView.findViewById(R.id.card_view);
            title      = (TextView)itemView.findViewById(R.id.title);
            days       = (TextView)itemView.findViewById(R.id.days);
            background = (ImageView)itemView.findViewById(R.id.background);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
