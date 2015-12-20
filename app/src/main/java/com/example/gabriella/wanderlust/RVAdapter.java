package com.example.gabriella.wanderlust;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Gabriella on 2015-12-19.
 *
 *
 * Adapter that follows the view holder design pattern and reuses the card view layout.
 *
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.TravelViewHolder> {

    private List<Travel> travels;

    /* Constructor to the custom adapter that handle the data that the RecyclerView displays */
    public RVAdapter(List<Travel> travels){
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
        Travel t = travels.get(i);
        travelViewHolder.title.setText(t.title);
        travelViewHolder.days.setText(t.days);
        travelViewHolder.background.setImageResource(t.background);
    }

    /* This method is called when the custom ViewHolder needs to be initialized. Each item of the
     * RecyclerView should get used. This is done by inflating the layout using LayoutInflater,
     * passing the output to the constructor of the custom ViewHolder
     */
    @Override
    public TravelViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,
                viewGroup, false);
        return new TravelViewHolder(v);
    }

    /* Create the adapter */
    public static class TravelViewHolder extends RecyclerView.ViewHolder {

        protected CardView cv;
        protected TextView title;
        protected TextView days;
        protected ImageView background;

        TravelViewHolder(View itemView) {
            super(itemView);
            cv    = (CardView)itemView.findViewById(R.id.card_view);
            title = (TextView)itemView.findViewById(R.id.title);
            days  = (TextView)itemView.findViewById(R.id.days);
            background = (ImageView)itemView.findViewById(R.id.background);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
