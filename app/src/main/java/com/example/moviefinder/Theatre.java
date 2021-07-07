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
                System.out.println(name + ":" + element.getElementsByTagName("Title").item(0).getTextContent() +
                        " - " + element.getElementsByTagName("dttmShowStart").item(0).getTextContent());
                String title = element.getElementsByTagName("Title").item(0).getTextContent();
                String auditorium = element.getElementsByTagName("TheatreAuditorium").item(0).getTextContent();
                String dateStr = element.getElementsByTagName("dttmShowStart").item(0).getTextContent();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                try {
                    Date date = df.parse(dateStr);
                    shows.add(new MovieShow(title, auditorium, date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<String> getShowList(Document doc) {
        ArrayList<String> showStringList = new ArrayList<String>();
        parseShows(doc);
            for (MovieShow show:shows){
                showStringList.add(show.toString());
        }
        return showStringList;
    }
}
