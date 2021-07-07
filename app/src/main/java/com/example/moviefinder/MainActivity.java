package com.example.moviefinder;

import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner thSpinner;
    Spinner dateSpinner;
    ListView showView;
    FinnKino FK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        XMLReader theatreAreasReader = new XMLReader("https://www.finnkino.fi/xml/TheatreAreas/");
        XMLReader scheduleDateReader = new XMLReader("https://www.finnkino.fi/xml/ScheduleDates/");
        FK = new FinnKino(theatreAreasReader.getXMLDocument(), scheduleDateReader.getXMLDocument());
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
        showView = (ListView) findViewById(R.id.showList);



    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (thSpinner.getSelectedItemId() > 0) {
            String area = FK.getTheatre((int) thSpinner.getSelectedItemId()).getID();
            String date = dateSpinner.getSelectedItem().toString();
            String showUrl = "https://www.finnkino.fi/xml/Schedule/?area=" + area +
                    "&dt=" + date + "&nrOfDays=1";
            System.out.println(showUrl);
            XMLReader showReader = new XMLReader(showUrl);
            if (area.equals("1029") | area.equals("1014") | area.equals("1012") | area.equals("1002") | area.equals("1021")){
                ArrayAdapter<String> showAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        FK.getTheatre((int) thSpinner.getSelectedItemId()).getShowList(showReader.getXMLDocument(), date, true));
                showView.setAdapter(showAdapter);
            } else {
                ArrayAdapter<String> showAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        FK.getTheatre((int) thSpinner.getSelectedItemId()).getShowList(showReader.getXMLDocument(), date));
                showView.setAdapter(showAdapter);
            }


        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}