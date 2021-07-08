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
    private Boolean area;

    Theatre(int indx, String thID, String thName) {
        index = indx;
        ID = thID;
        name = thName;
        shows = new ArrayList<MovieShow>();
        area = ID.equals("1029") | ID.equals("1014") | ID.equals("1012") | ID.equals("1002") | ID.equals("1021");
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
                String ID = element.getElementsByTagName("ID").item(0).getTextContent();
                String title = element.getElementsByTagName("Title").item(0).getTextContent();
                String auditorium = element.getElementsByTagName("TheatreAuditorium").item(0).getTextContent();
                String dateStr = element.getElementsByTagName("dttmShowStart").item(0).getTextContent();
                String theatreName = element.getElementsByTagName("Theatre").item(0).getTextContent();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                try {
                    Date date = df.parse(dateStr);
                    if (!this.showExists(ID)) {
                        shows.add(new MovieShow(ID, title, auditorium, date, theatreName));

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<String> getShowList(Document doc, String date, String fromTime, String toTime, String title) {
        if (fromTime.equals("") | toTime.equals("")) {
            fromTime = "00:00:00";
            toTime = "23:59:59";
        }
        ArrayList<String> showStringList = new ArrayList<String>();
        SimpleDateFormat stf = new SimpleDateFormat("dd.MM.yyyy'T'HH:mm:ss");
        parseShows(doc);
        try {
            Date startDate = stf.parse(date + "T" + fromTime);
            Date toDate = stf.parse(date + "T" + toTime);
            System.out.println(date + "T" + fromTime);
            for (MovieShow show : shows) {
                if (!(title.equals("") | title.equals("title"))) {
                    if (show.getDateString().equals(date) & show.getTitle().equals(title)) {
                        showStringList.add(show + "\n" + show.getTheatreName());
                    }
                } else {
                    if (show.getDateString().equals(date) & startDate.before(show.getDate()) & toDate.after(show.getDate())) {
                        showStringList.add(show + "\n" + show.getTheatreName());
                    }
                }
            }
            } catch(ParseException e){
                e.printStackTrace();
            }
            return showStringList;
        }

        public boolean isArea () {
            return area;
        }

        public boolean showExists (String id){
            for (MovieShow show : shows) {
                if (show.getId().equals(id)) {
                    return true;
                }
            }
            return false;
        }
    }
