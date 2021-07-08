package com.example.moviefinder;
/*
@author Sami Sillanpää
LUT Olio.ohjelmointi. Viikkotehtävät Vko 9
*/

import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivityListMovies extends AppCompatActivity {

    ListView listOutputMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list_movies);
        listOutputMovies = (ListView) findViewById(R.id.listOutputMovies);
        Intent intent = getIntent();
        ArrayList<String> shows = intent.getStringArrayListExtra("SHOWLIST");
        ArrayAdapter<String> showAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                shows);
        listOutputMovies.setAdapter(showAdapter);

    }
}