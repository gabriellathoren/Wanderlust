package com.example.gabriella.wanderlust;

/**
 *
 * <h1>DBCountry</h1>
 *
 * A model class that handles the objects for the table country in the database. This class contains
 * information about countries.
 *
 * @author  Gabriella Thorén
 * @version 1
 *
 */
public class DBCountry {

    private String country;
    private String continent;
    private boolean selected = false;


    /**
     * Constructor without parameters for the DBCountry class.
     */
    public DBCountry(){}

    /**
     * Constructor with two parameters for the DBCountry class which sets the country and continent
     * of the object.
     *
     * @param country       the country name
     * @param continent     the name of the continent
     */
    public DBCountry(String country, String continent){
        this.country   = country;
        this.continent = continent;
    }


    /**
     * Setter for country.
     *
     * @param country   the country name
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Setter for the object which describes if the object is selected by the user as one of their
     * previous travel destinations or not.
     *
     * @param selected  boolean which describes if the country has been selected by the user or not
     */

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    /**
     * Getter which returns the object's country name.
     *
     * @return String   returns the name of the country
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * Getter which returns the object's continent.
     *
     * @return String   returns the name of the continent which the country is located
     */
    public String getContinent() {
        return this.continent;
    }

    /**
     * Getter which returns the object's state. Either the country has been selected of the user
     * as one of its visited countries, or it has not.
     *
     * @return boolean  {@code true} if the country has been selected
     *                  {@code false} if it has not been selected
     */
    public boolean isSelected() {
        return selected;
    }


}
