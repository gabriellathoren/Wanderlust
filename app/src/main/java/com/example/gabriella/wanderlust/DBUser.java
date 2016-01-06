package com.example.gabriella.wanderlust;

import java.io.Serializable;

/**
 * Created by Gabriella on 2015-12-23.
 *
 * A model class that handles the table user
 */
public class DBUser implements Serializable {

    int userID;
    String username;
    String password;
    String first_name;
    String last_name;

    private static final long serialVersionUID = 465497646;


    /* Constructors */
    public DBUser(){}

    public DBUser(String username, String password, String first_name, String last_name) {
        this.username   = username;
        this.password   = password;
        this.first_name = first_name;
        this.last_name  = last_name;
    }

    public DBUser(int id, String username, String password, String first_name, String last_name) {
        this.userID     = id;
        this.username   = username;
        this.password   = password;
        this.first_name = first_name;
        this.last_name  = last_name;
    }

    /* Setters */
    public void setUserID(int id) {
        this.userID = id;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }


    /* Getters */
    public int getUserID() {
        return this.userID;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getFirstName() {
        return this.first_name;
    }

    public String getLastName() {
        return this.last_name;
    }


}
