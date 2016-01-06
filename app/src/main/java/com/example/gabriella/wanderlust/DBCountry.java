package com.example.gabriella.wanderlust;

/**
 * Created by Gabriella on 2015-12-23.
 *
 * A model class that handles the table user
 *
 */
public class DBCountry {

    private String country;
    private String continent;
    private boolean selected = false;


    /* Constructors */
    public DBCountry(){}

    public DBCountry(String country, String continent){
        this.country   = country;
        this.continent = continent;
    }


    /* Setters */
    public void setCountry(String country) {
        this.country = country;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    /* Getters */
    public String getCountry() {
        return this.country;
    }

    public String getContinent() {
        return this.continent;
    }

    public boolean isSelected() {
        return selected;
    }


}
