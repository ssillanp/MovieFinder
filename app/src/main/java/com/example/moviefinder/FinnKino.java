package com.example.moviefinder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FinnKino {

    private ArrayList<Theatre> theatres;
    private ArrayList<Date> schDates;

    FinnKino(Document docTheatres, Document docSchDates) {
        /*
        Constructor for Class FinnKino
        Parses from XML Documents the theatres and Available scheduled dates
         */
        theatres = new ArrayList<Theatre>();  // New arrayList to keep the Theatres
//        System.out.println("Root Element:" + docTheatres.getDocumentElement().getNodeName());
        NodeList nList = docTheatres.getDocumentElement().getElementsByTagName("TheatreArea");
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                theatres.add(new Theatre(i, element.getElementsByTagName("ID").item(0).getTextContent(),
                        element.getElementsByTagName("Name").item(0).getTextContent()));
            }
        }
        System.out.println("HH");
        schDates = new ArrayList<Date>(); // New ArrayList to hold the available schedule dates
//        System.out.println("Root Element:" + docSchDates.getDocumentElement().getNodeName());
        nList = docSchDates.getDocumentElement().getElementsByTagName("dateTime");
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String dateStr = element.getTextContent();
//                System.out.println(dateStr);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                try {
                    Date schDay = df.parse(dateStr);
                    schDates.add(schDay);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    public Theatre getTheatre(int index) {
        for (Theatre theatre : theatres) {
            if (theatre.getIndex() == index) {
                return theatre;
            }
        }
        return null;
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

    public ArrayList<String> searchByTitle(String title){
        ArrayList<String> listOfMovies = new ArrayList<String>();

    }


}
