package com.esprit.android.ebi3.models;

/**
 * Created by Hassan on 16/03/16.
 */
public class Comment {

    public int id;
    public String corps;
    public User user;

    public Comment(int id, String corps, User user) {
        this.id = id;
        this.corps = corps;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCorps() {
        return corps;
    }

    public void setCorps(String corps) {
        this.corps = corps;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
