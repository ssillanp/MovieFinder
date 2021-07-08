package com.example.moviefinder;


public class showURLBuilder {

    private String baseUrl;
    private String area = "";
    private String date = "";
    private String nrOfDays;

    showURLBuilder(String ar, String dt){
        baseUrl = "https://www.finnkino.fi/xml/Schedule/?";
        area = "area=" + ar;
        date = "&dt=" + dt;
        nrOfDays = "&nrOfDays=1";
    }

    public String getUrl(){
        return baseUrl + area + date + nrOfDays;
    }

    public void setArea(String ar){
        area = "area=" + ar;
    }

    public void setDate(String dt){
        area = "&dt=" + dt;
    }


}
