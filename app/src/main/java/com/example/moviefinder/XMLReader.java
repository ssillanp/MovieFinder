package com.example.moviefinder;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class XMLReader {
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
        return document;
    }
}
