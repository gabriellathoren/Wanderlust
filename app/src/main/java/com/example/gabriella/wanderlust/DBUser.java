package com.example.gabriella.wanderlust;

import java.io.Serializable;

/**
 * <h1>DBUser</h1>
 *
 * A model class that handles the objects for the table user in the database. This class contains
 * information about the user
 *
 * @author  Gabriella Thorén
 * @version 1
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
public class DBUser implements Serializable {

    private int    userID;
    private String username;
    private String password;
    private String first_name;
    private String last_name;


    /**
     * Constructor without parameters
     */
    public DBUser(){}

    /**
     * Constructor with four parameters.
     *
     * @param username      the user's username
     * @param password      the user's password
     * @param first_name    the user's first name
     * @param last_name     the user's sir name
     */
    public DBUser(String username, String password, String first_name, String last_name) {
        this.username   = username;
        this.password   = password;
        this.first_name = first_name;
        this.last_name  = last_name;
    }

    /**
     * Constructor with five parameters.
     *
     * @param id            the user's id
     * @param username      the user's username
     * @param password      the user's password
     * @param first_name    the user's first name
     * @param last_name     the user's sir name
     */
    public DBUser(int id, String username, String password, String first_name, String last_name) {
        this.userID     = id;
        this.username   = username;
        this.password   = password;
        this.first_name = first_name;
        this.last_name  = last_name;
    }

    /**
     *  Setter of the user's id
     *
     *  @param id   the user's id
     */
    public void setUserID(int id) {
        this.userID = id;
    }

    /**
     * Setter of the user's username
     *
     * @param username  the user's username
     */
    public void setUsername(String username){
        this.username = username;
    }

    /**
     * Setter of the user's password.
     *
     * @param password  the user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Setter of the user's first name.
     *
     * @param first_name    the user's first name
     */
    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    /**
     * Setter of the user's sir name.
     *
     * @param last_name     the user's sir name
     */
    public void setLastName(String last_name) {
        this.last_name = last_name;
    }


    /**
     * Getter of the user's id.
     *
     * @return int     the user's id
     */
    public int getUserID() {
        return this.userID;
    }

    /**
     * Getter of the user's username.
     *
     * @return String   the user's username
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * Getter of the user's password.
     *
     * @return String   the user's password.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Getter of the user's first name.
     *
     * @return String   the user's first name
     */
    public String getFirstName() {
        return this.first_name;
    }

    /**
     * Getter of the user's last name.
     *
     * @return String   the user's last name
     */
    public String getLastName() {
        return this.last_name;
    }


}
