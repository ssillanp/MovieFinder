package com.example.moviefinder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class XMLParse {


    public ArrayList<Theatre> parseTheatres(Document doc){
        ArrayList<Theatre> theatres = new ArrayList<>();
        NodeList nList = doc.getDocumentElement().getElementsByTagName("TheatreArea");
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                theatres.add(new Theatre(i, element.getElementsByTagName("ID").item(0).getTextContent(),
                        element.getElementsByTagName("Name").item(0).getTextContent()));
            }
        }
        return theatres;
    }

    public ArrayList<Date> parseDates(Document doc){
        ArrayList<Date> schDates = new ArrayList<>();
        NodeList nList = doc.getDocumentElement().getElementsByTagName("dateTime");
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
        return schDates;
    }
}
