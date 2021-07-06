package com.example.moviefinder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class Theatre {

    private int index;
    private String ID;
    private String name;
    private ArrayList<String> shows;

    Theatre(int indx, String thID, String thName) {
        index = indx;
        ID = thID;
        name = thName;
        shows = new ArrayList<String>();
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

    public ArrayList<String> parseShows(Document doc){
        shows.clear();
        NodeList nList = doc.getDocumentElement().getElementsByTagName("Show");
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                System.out.println(name + ":" + element.getElementsByTagName("Title").item(0).getTextContent() +
                        " - " + element.getElementsByTagName("dttmShowStart").item(0).getTextContent());
                shows.add(element.getElementsByTagName("Title").item(0).getTextContent() +
                        " - " + element.getElementsByTagName("dttmShowStart").item(0).getTextContent());
            }
        }
        return shows;
    }
}
