package com.example.moviefinder;

/*
@author Sami Sillanpää
LUT Olio.ohjelmointi. Viikkotehtävät Vko 9
 */

import android.content.Intent;
import android.os.StrictMode;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner thSpinner; //Spinner for selecting theatre
    Spinner dateSpinner; // spinner for selecting date
    FinnKino FK;
    EditText fromTime;
    EditText toTime;
    EditText title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        XMLParse parser = new XMLParse();
        XMLReader theatreAreasReader = new XMLReader("https://www.finnkino.fi/xml/TheatreAreas/");
        XMLReader scheduleDateReader = new XMLReader("https://www.finnkino.fi/xml/ScheduleDates/");
        FK = new FinnKino();
        FK.addTheatres(parser.parseTheatres(theatreAreasReader.getXMLDocument())); // Parse and add Theatres to FK
        FK.addDates(parser.parseDates(scheduleDateReader.getXMLDocument())); // Parse an add available dates to FK
        thSpinner = (Spinner) findViewById(R.id.thspinner);
        thSpinner.setOnItemSelectedListener(this); // Add listener to thSpinner to catch the events from spinner
        ArrayAdapter<String> thAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, FK.listTheatres());
        thSpinner.setAdapter(thAdapter); // set spinner list
        dateSpinner = (Spinner) findViewById(R.id.datespinner);
        dateSpinner.setOnItemSelectedListener(this);  // Add listener to thSpinner to catch the events from spinner
        ArrayAdapter<String> dateAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, FK.listDates());
        dateSpinner.setAdapter(dateAdapter); // set spinner list
        fromTime = (EditText) findViewById(R.id.fromTime);
        toTime = (EditText) findViewById(R.id.toTime);
        title = (EditText) findViewById(R.id.txtTitle);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        /*
        Gets and displays the movies, based on set criteria, every time the selection is changed
         */
        if (thSpinner.getSelectedItemId() != 0) { //skips -> "Valitse alue/teatteri" (Id 0)
            Intent intent = new Intent(this, MainActivityListMovies.class); // Intent for transferring info between views
            String area = FK.getTheatre((int) thSpinner.getSelectedItemId()).getID(); //theatre ID
            String date = dateSpinner.getSelectedItem().toString();
            showURLBuilder showUrl = new showURLBuilder(area, date); // Build the URL for API
//            System.out.println(showUrl.getUrl());
            XMLReader showReader = new XMLReader(showUrl.getUrl());
            intent.putStringArrayListExtra("SHOWLIST",
                    FK.getTheatre((int) thSpinner.getSelectedItemId()).getShowList(showReader.getXMLDocument(),
                            date, fromTime.getText().toString(), toTime.getText().toString(), ""));
            startActivity(intent); // Call the 2nd view
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void searchByTitle(View v) {
        /*
        List movies shows based on selected title
         */
        Intent intent = new Intent(this, MainActivityListMovies.class);
        ArrayList<String> results = new ArrayList<>(); // List for collecting the result and to transfer to 2nd view.
        String date = dateSpinner.getSelectedItem().toString();
        String showTitle = title.getText().toString();
        int theatreID = (int) thSpinner.getSelectedItemId();
        if (theatreID == 0) { // If a theatre (or area) is not selected, loop thru all (skipping areas).
            for (Theatre theatre : FK.getAllTheatres()) {
                if (!theatre.isArea()) { //skip areas
                    showURLBuilder urli = new showURLBuilder(theatre.getID(), date);
                    XMLReader reader = new XMLReader(urli.getUrl());
                    results.addAll(theatre.getShowList(reader.getXMLDocument(), date, "", "", showTitle));
                }
            }
        } else { // If theatre is selected fetch only from selected
            Theatre theatre = FK.getTheatre(theatreID);
            showURLBuilder urli = new showURLBuilder(theatre.getID(), date);
            XMLReader reader = new XMLReader(urli.getUrl());
            results.addAll(theatre.getShowList(reader.getXMLDocument(), date, "", "", showTitle));
        }
        intent.putStringArrayListExtra("SHOWLIST", results);
        startActivity(intent);

//        thSpinner.performItemClick(v, 0, 0);
    }
}