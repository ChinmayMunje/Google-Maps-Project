package com.gtappdevelopers.googlemapsproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button markerBtn, polyLineBtn, polygonBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        markerBtn = findViewById(R.id.idBtnGoogleMapWithMarker);
        polyLineBtn = findViewById(R.id.idBtnGoogleMapWithPolyLine);
        polygonBtn = findViewById(R.id.idBtnGoogleMapWithPolygon);
        markerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                i.putExtra("key", "marker");
                startActivity(i);
            }
        });

        polyLineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                i.putExtra("key", "polyline");
                startActivity(i);
            }
        });

        polygonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                i.putExtra("key", "polygon");
                startActivity(i);
            }
        });

    }

}