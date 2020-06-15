package com.example.travelgood.model;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class Dulich implements Serializable {
    public int ID;
    public String Tendiadiem;
    public String Diachi;
    public String Hinhanhdiadiemdulich;
    public String Motadiadiem;
    public int IDDulich;

    public Dulich(int id, String tendiadiem, String diachi, String hinhanhdiadiemdulich, String motadiadiem, int idulich) {
        ID = id ;
        Tendiadiem = tendiadiem;
        Diachi = diachi;
        Hinhanhdiadiemdulich = hinhanhdiadiemdulich;
        Motadiadiem = motadiadiem;
        IDDulich = idulich;
    }

    public int getID(){ return ID;}

    public void setID(int id) { this.ID = id; }

    public String getTendiadiem() {
        return Tendiadiem;
    }

    public void setTendiadiem(String tendiadiem) {
        Tendiadiem = tendiadiem;
    }

    public String getDiachi() {
        return Diachi;
    }

    public void setDiachi(String diachi) {
        Diachi = diachi;
    }

    public String getHinhanhdiadiemdulich() {
        return Hinhanhdiadiemdulich;
    }

    public void setHinhanhdiadiemdulich(String hinhanhdiadiemdulich) {
        Hinhanhdiadiemdulich = hinhanhdiadiemdulich;
    }

    public String getMotadiadiem() {
        return Motadiadiem;
    }

    public void setMotadiadiem(String motadiadiem) {
        Motadiadiem = motadiadiem;
    }

    public int getIDDulich() {
        return IDDulich;
    }

    public void setIDDulich(int iddulich) {
        this.IDDulich = iddulich ;
    }
}
