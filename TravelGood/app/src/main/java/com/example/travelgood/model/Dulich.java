package com.example.travelgood.model;
import java.io.Serializable;

public class Dulich implements Serializable {
    public int ID;
    public String Tendiadiem;
    public String Diachi;
    public String Hinhanhdiadiemdulich;
    public String Motadiadiem;
    public int IDDulich;
    public double Lat;
    public double Lng;

    public Dulich(int id, String tendiadiem, String diachi, String hinhanhdiadiemdulich, String motadiadiem, int idulich,double lat,double lng) {
        ID = id ;
        Tendiadiem = tendiadiem;
        Diachi = diachi;
        Hinhanhdiadiemdulich = hinhanhdiadiemdulich;
        Motadiadiem = motadiadiem;
        IDDulich = idulich;
        Lat = lat;
        Lng = lng;
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
    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLng() {
        return Lng;
    }

    public void setLng(double lng) {
        Lng = lng;
    }
}
