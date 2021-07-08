package com.example.moviefinder;

/*
@author Sami Sillanpää
LUT Olio.ohjelmointi. Viikkotehtävät Vko 9
 */

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class XMLReader {
    /*
    Class to read from URL.
     */
    private Document document;
    private String URL;

    XMLReader(String url) {
        URL = url;
        document = readFromUrl();
    }

    private Document readFromUrl(){
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(URL);
            doc.getDocumentElement().normalize();
            return doc;

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Document getXMLDocument(){

        // returns an XML Document.
        return document;
    }
}
