package com.example.travelgood.model;

public class Loaidl {
    public int Id;
    public String TenLoaidl;
    public String Hinhanhloaidl;

    public Loaidl(int id, String tenLoaidl, String hinhanhloaidl) {
        Id = id;
        TenLoaidl = tenLoaidl;
        Hinhanhloaidl = hinhanhloaidl;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenLoaidl() {
        return TenLoaidl;
    }

    public void setTenLoaidl(String tenLoaidl) {
        TenLoaidl = tenLoaidl;
    }

    public String getHinhanhloaidl() {
        return Hinhanhloaidl;
    }

    public void setHinhanhloaidl(String hinhanhloaidl) {
        Hinhanhloaidl = hinhanhloaidl;
    }
}
