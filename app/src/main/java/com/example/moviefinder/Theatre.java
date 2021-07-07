package com.example.moviefinder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Theatre {

    private int index;
    private String ID;
    private String name;
    private ArrayList<MovieShow> shows;

    Theatre(int indx, String thID, String thName) {
        index = indx;
        ID = thID;
        name = thName;
        shows = new ArrayList<MovieShow>();
    }

    public int getIndex() {
        return index;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void parseShows(Document doc) {
        NodeList nList = doc.getDocumentElement().getElementsByTagName("Show");
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String title = element.getElementsByTagName("Title").item(0).getTextContent();
                String auditorium = element.getElementsByTagName("TheatreAuditorium").item(0).getTextContent();
                String dateStr = element.getElementsByTagName("dttmShowStart").item(0).getTextContent();
                String theatreName = element.getElementsByTagName("Theatre").item(0).getTextContent();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                try {
                    Date date = df.parse(dateStr);
                    shows.add(new MovieShow(title, auditorium, date, theatreName));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<String> getShowList(Document doc, String date, String fromTime, String toTime, Boolean area) {
        if (fromTime.equals("") | toTime.equals("")){
            fromTime = "00:00:01";
            toTime = "23:59:59";
        }
        ArrayList<String> showStringList = new ArrayList<String>();
        SimpleDateFormat stf = new SimpleDateFormat("dd.MM.yyyy'T'HH:mm:ss");
        parseShows(doc);
        try {
            Date startDate = stf.parse(date +"T"+ fromTime);
            Date toDate = stf.parse(date +"T"+ toTime);
            System.out.println(date +"T"+ fromTime);
            for (MovieShow show : shows) {
                if (show.getDateString().equals(date)&startDate.before(show.getDate())&toDate.after(show.getDate())) {
                    if (area){
                        showStringList.add(show + "\n" + show.getTheatreName().split(",")[0]);
                    } else {
                        showStringList.add(show.toString());
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return showStringList;
    }
}
