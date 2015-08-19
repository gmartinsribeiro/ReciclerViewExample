package com.gmartinsribeiro.recyclerviewapp.entity;

import java.util.Date;

/**
 * Created by Gonçalo Martins Ribeiro on 20-08-2015.
 */
public class Item {

    private int id;
    private String title;
    private String subtitle;
    private String body;
    private Date date;

    public Item() {
    }

    public Item(int id, String title, String subtitle, String body, Date date) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.body = body;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
