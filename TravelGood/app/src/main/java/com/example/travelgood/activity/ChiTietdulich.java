package com.example.travelgood.activity;

import android.icu.text.Transliterator;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelgood.R;
import com.example.travelgood.model.Dulich;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.Objects;

public class ChiTietdulich extends AppCompatActivity {
    Toolbar toolbarChitiet;
    ImageView imageViewChitiet;
    TextView textViewTen ,textViewDiachi, textViewMota;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_du_lich);
        Anhxa();
        ActionToolBar();
        GetInformation();
    }

    public void GetInformation() {
        int ID = 0;
        String TenChiTiet = "";
        String DiaDiemChiTiet = "";
        String HinhAnhChiTiet = "";
        String MoTaChiTiet = "";
        int IDDulich = 0;
        Dulich dulich = (Dulich) getIntent().getSerializableExtra("thongtindulich");
        ID = dulich.getID();
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
        toolbarChitiet = findViewById(R.id.toolbarchitietdulich);
        imageViewChitiet = findViewById(R.id.imageviewchitietdulich);
        textViewTen = findViewById(R.id.textviewtendiadiemchitiet);
        textViewDiachi = findViewById(R.id.textviewdiachichitiet);
        textViewMota = findViewById(R.id.textviewmotachitiet);
    }
}
