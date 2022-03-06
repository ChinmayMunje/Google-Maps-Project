package com.gtappdevelopers.googlemapsproject;

import android.content.res.Resources;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String key = "";
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        key = getIntent().getStringExtra("key");
        searchView = findViewById(R.id.idSearchView);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = searchView.getQuery().toString();
                List<Address> addresses = null;
                if(location!=null || !location.equals("")){
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try {
                        addresses = geocoder.getFromLocationName(location,1);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    Address address = addresses.get(0);
                    LatLng markerLatLng = new LatLng(address.getLatitude(),address.getLongitude());
                    displayMarker(markerLatLng,location);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style));

            if (!success) {
                Log.e("TAG", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("TAG", "Can't find style. Error: ", e);
        }

        if (key.equals("marker")) {
            searchView.setVisibility(View.VISIBLE);
        } else if (key.equals("polyline")) {
            displayPolyLine(googleMap);
        } else {
            drawPolygon(googleMap);
        }
    }

    private void drawPolygon(GoogleMap googleMap) {
        LatLng delhi = new LatLng(28.644800, 77.216721);
        LatLng jaipur = new LatLng(26.922070,75.778885);
        LatLng agra = new LatLng(27.176670, 78.008072);

        PolygonOptions polygonOptions = new PolygonOptions().add(jaipur, agra, delhi).clickable(true);
        Polygon polygon = googleMap.addPolygon(polygonOptions);
        polygon.setStrokeColor(Color.BLUE);
        polygon.setFillColor(Color.RED);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(delhi, 7));
    }

    private void displayMarker(LatLng position,String location) {
        mMap.addMarker(new MarkerOptions().position(position).title("Marker in "+location));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position,7));
    }

    private void displayPolyLine(GoogleMap googleMap) {
        LatLng delhi = new LatLng(28.644800, 77.216721);
        LatLng jaipur = new LatLng(26.922070,75.778885);
        LatLng agra = new LatLng(27.176670, 78.008072);

        mMap.addPolyline((new PolylineOptions()).add(delhi, jaipur, agra, delhi).
                width(5)
                .color(Color.RED)
                .geodesic(true));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(delhi, 7));
    }
}