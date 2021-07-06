package com.example.moviefinder;

import java.util.ArrayList;

public class Theatre {

    private String ID;
    private String name;
    private ArrayList<String> shows;

    Theatre(String thID, String thName) {
        ID = thID;
        name = thName;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }
}
