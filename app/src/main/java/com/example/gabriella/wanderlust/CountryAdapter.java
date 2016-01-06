package com.example.gabriella.wanderlust;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gabriella on 2016-01-06.
 */
public class CountryAdapter extends ArrayAdapter<DBCountry> {

    private List<DBCountry> africa;
    private List<DBCountry> asia;
    private List<DBCountry> europe;
    private List<DBCountry> northAmerica;
    private List<DBCountry> oceania;
    private List<DBCountry> southAmerica;

    Context context;

    /* Constructor which takes the context, the xml textview and list of countries to show and
     * sets it in the lists.
     */
    public CountryAdapter(Context context, List<DBCountry> countryList) {

        super(context, R.layout.country_list, countryList);
        this.context = context;
        this.africa  = countryList;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.country_list, parent, false);

        CheckBox cb   = (CheckBox) convertView.findViewById(R.id.checkbox_travel);

        cb.setText(africa.get(position).getCountry());

        if(africa.get(position).isSelected()) {
            cb.setChecked(true);
        }
        else {
            cb.setChecked(false);
        }

        return convertView;
    }
}
