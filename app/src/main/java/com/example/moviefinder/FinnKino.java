package com.example.moviefinder;

/*
@author Sami Sillanpää
LUT Olio.ohjelmointi. Viikkotehtävät Vko 9
 */

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FinnKino {
    /*
    Class to hold Theatres and available show dates.
     */

    private ArrayList<Theatre> theatres;
    private ArrayList<Date> schDates;

    FinnKino() {
        /*
        Constructor for Class FinnKino
         */
        theatres = new ArrayList<Theatre>();  // New arrayList to keep the Theatres
        schDates = new ArrayList<Date>(); // New ArrayList to hold the available schedule dates

    }

    public void addTheatres(ArrayList<Theatre> th){
        theatres.addAll(th);
    }

    public void addDates(ArrayList<Date> dt){
        schDates.addAll(dt);
    }


    public Theatre getTheatre(int index) { // Index number (not ID) in XML output
        for (Theatre theatre : theatres) {
            if (theatre.getIndex() == index) {
                return theatre;
            }
        }
        return null;
    }


    public ArrayList<Theatre> getAllTheatres(){
        return theatres;
    }

    public ArrayList<String> listTheatres() {
        ArrayList<String> theatreList = new ArrayList<String>();
        for (Theatre theatre : theatres) {
//            System.out.println(theatre.getName());
            theatreList.add(theatre.getName());
        }
        return theatreList;
    }

    public ArrayList<String> listDates() {
        ArrayList<String> dateList = new ArrayList<String>();
        SimpleDateFormat df2 = new SimpleDateFormat("dd.MM.yyyy");
        for (Date date : schDates) {
//            System.out.println(df2.format(date));
            dateList.add(df2.format(date));
        }
        return dateList;
    }


}
