package com.example.moviefinder;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieShow {

    private String title;
    private String auditorium;
    private Date showDate;

    MovieShow(String shTitle, String audit, Date shDate) {
        title = shTitle;
        auditorium = audit;
        showDate = shDate;
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

    public String getDateString() {
        SimpleDateFormat df = new SimpleDateFormat("dd:MM:yyyy");
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
}


