package com.example.gabriella.wanderlust;

/**
 * Created by Gabriella on 2015-12-23.
 *
 * A model class that handles the table user
 */
public class DBUser {

    int user_ID;
    String username;
    String password;
    String first_name;
    String last_name;

    /* Constructors */
    public DBUser(){}

    public DBUser(int user_ID, String username, String password, String first_name, String last_name) {
        this.user_ID    = user_ID;
        this.username   = username;
        this.password   = password;
        this.first_name = first_name;
        this.last_name  = last_name;
    }

    /* Setters */
    public void setUser_ID(int id) {
        this.user_ID = id;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }


    /* Getters */
    public int getUser_ID() {
        return this.user_ID;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getFirst_name() {
        return this.first_name;
    }

    public String getLast_name() {
        return this.last_name;
    }


}
