package com.example.travelgood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.travelgood.R;
import com.example.travelgood.model.Dulich;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    double lat,lng;
    String ten;
    Toolbar toolbarmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
       ten = intent.getStringExtra("ten");
       lat = intent.getDoubleExtra("toadolat",0);
       lng = intent.getDoubleExtra("toadolng",0);
        setContentView(R.layout.activity1_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Anhxa();
        ActionToolbar();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
            LatLng Diadiem = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions().position(Diadiem).title(ten));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Diadiem, 7));
        }

    private void ActionToolbar() {
        setSupportActionBar(toolbarmap);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarmap.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbarmap = findViewById(R.id.toolbarmap);
    }
    }
