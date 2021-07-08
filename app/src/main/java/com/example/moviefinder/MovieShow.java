package com.example.moviefinder;

/*
@author Sami Sillanpää
LUT Olio.ohjelmointi. Viikkotehtävät Vko 9
 */

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieShow {
    /*
    Class to hold data for individual show.
     */

    private String ID;
    private String title;
    private String auditorium;
    private Date showDate;
    private String theatreName;

    MovieShow(String id, String shTitle, String audit, Date shDate, String thName) {
        ID = id;
        title = shTitle;
        auditorium = audit;
        showDate = shDate;
        theatreName = thName;
    }

    public String getTitle() {
        return title;
    }

    public String getAuditorium() {
        return auditorium;
    }

    public Date getDate() {
        return showDate;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public String getDateString() {
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        return df.format(showDate);
    }

    public String getStartTimeString() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(showDate);
    }

    @Override
    public String toString() {
        return title + " klo: " + this.getStartTimeString() + " " + auditorium;
    }

    public String getId() {
        return ID;
    }
}


