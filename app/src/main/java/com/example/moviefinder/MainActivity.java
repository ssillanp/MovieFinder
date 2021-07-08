package com.example.moviefinder;

import android.content.Intent;
import android.os.StrictMode;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner thSpinner;
    Spinner dateSpinner;
    ListView showView;
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
        FK.addTheatres(parser.parseTheatres(theatreAreasReader.getXMLDocument()));
        FK.addDates(parser.parseDates(scheduleDateReader.getXMLDocument()));
        thSpinner = (Spinner) findViewById(R.id.thspinner);
        thSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> thAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, FK.listTheatres());
        thSpinner.setAdapter(thAdapter);
        dateSpinner = (Spinner) findViewById(R.id.datespinner);
        dateSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> dateAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, FK.listDates());
        dateSpinner.setAdapter(dateAdapter);
        fromTime = (EditText) findViewById(R.id.fromTime);
        toTime = (EditText) findViewById(R.id.toTime);
        title = (EditText) findViewById(R.id.txtTitle);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        if (thSpinner.getSelectedItemId() != 0) {
            Intent intent = new Intent(this, MainActivityListMovies.class);
            String area = FK.getTheatre((int) thSpinner.getSelectedItemId()).getID();
            String date = dateSpinner.getSelectedItem().toString();
            showURLBuilder showUrl = new showURLBuilder(area, date);
            System.out.println(showUrl.getUrl());
            XMLReader showReader = new XMLReader(showUrl.getUrl());
            intent.putStringArrayListExtra("SHOWLIST",
                    FK.getTheatre((int) thSpinner.getSelectedItemId()).getShowList(showReader.getXMLDocument(),
                            date, fromTime.getText().toString(), toTime.getText().toString(), ""));
            startActivity(intent);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void searchByTitle(View v) {
        System.out.println("CLICK");
        Intent intent = new Intent(this, MainActivityListMovies.class);
        ArrayList<String> results = new ArrayList<>();
        String date = dateSpinner.getSelectedItem().toString();
        String showTitle = title.getText().toString();
        int theatreID = (int) thSpinner.getSelectedItemId();
        if (theatreID == 0) {
            for (Theatre theatre : FK.getAllTheatres()) {
                if (!theatre.isArea()) {
                    showURLBuilder urli = new showURLBuilder(theatre.getID(), date);
                    XMLReader reader = new XMLReader(urli.getUrl());
                    results.addAll(theatre.getShowList(reader.getXMLDocument(), date, "", "", showTitle));
                }
            }
        } else {
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