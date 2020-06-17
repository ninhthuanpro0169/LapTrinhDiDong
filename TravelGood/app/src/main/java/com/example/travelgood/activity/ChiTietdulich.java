package com.example.travelgood.activity;

import android.content.Intent;
import android.icu.text.Transliterator;
import android.os.Build;

import android.os.Bundle;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.travelgood.R;
import com.example.travelgood.model.Dulich;
import com.example.travelgood.ultil.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

public class ChiTietdulich extends AppCompatActivity {
    Toolbar toolbarChitiet;
    ImageView imageViewChitiet;
    TextView textViewTen ,textViewDiachi, textViewMota;
    Button btnmap;
    double lat,lng;
    String TenChiTiet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_du_lich);
        Anhxa();
        ActionToolBar();
        GetInformation();

        btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTietdulich.this,MapActivity.class);
                intent.putExtra("ten",TenChiTiet);
                intent.putExtra("toadolat",lat);
                intent.putExtra("toadolng",lng);
                startActivity(intent);
            }
        });
    }

    public void GetInformation() {
        int ID = 0;
        TenChiTiet = "";
        String DiaDiemChiTiet = "";
        String HinhAnhChiTiet = "";
        String MoTaChiTiet = "";
        int IDDulich = 0;
        Dulich dulich = (Dulich) getIntent().getSerializableExtra("thongtindulich");
        ID = dulich.getID();
        lng = dulich.getLng();
        lat = dulich.getLat();
        TenChiTiet =dulich.getTendiadiem();
        DiaDiemChiTiet=dulich.getDiachi();
        HinhAnhChiTiet = dulich.getHinhanhdiadiemdulich();
        MoTaChiTiet = dulich.getMotadiadiem();
        IDDulich = dulich.getIDDulich();
        textViewTen.setText(TenChiTiet);
        textViewDiachi.setText(DiaDiemChiTiet);
        textViewMota.setText(MoTaChiTiet);
        Picasso.with(getApplicationContext()).load(HinhAnhChiTiet)
                .placeholder(R.drawable.load)
                .error(R.drawable.loi)
                .into(imageViewChitiet);

    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarChitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        btnmap = findViewById(R.id.cc);
        toolbarChitiet = findViewById(R.id.toolbarchitietdulich);
        imageViewChitiet = findViewById(R.id.imageviewchitietdulich);
        textViewTen = findViewById(R.id.textviewtendiadiemchitiet);
        textViewDiachi = findViewById(R.id.textviewdiachichitiet);
        textViewMota = findViewById(R.id.textviewmotachitiet);
    }
}
