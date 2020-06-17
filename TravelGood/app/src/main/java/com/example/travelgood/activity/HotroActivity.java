package com.example.travelgood.activity;


import android.os.Bundle;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.travelgood.R;

public class HotroActivity extends AppCompatActivity {
    Toolbar toolbarhotro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotro);
        Anhxa();
        ActionToolbar();
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarhotro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarhotro.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbarhotro = findViewById(R.id.toolbarhotro);
    }
}
