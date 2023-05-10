package com.berat.ogrencibilgisistemm.modul;

public class Ogretmenler {
    public String ogrenciId;
    public String ogrenciAd;
    public String ogrenciSoyad;
    public String ogrenciTel;
    public String ogrenciEposta;
    public String dogumTarihi;
    public Ogretmenler(String ogrenciId,String ogrenciAd,String ogrenciSoyad,String ogrenciTel,String ogrenciEposta,String dogumTarihi){
        this.ogrenciId=ogrenciId;
        this.ogrenciAd=ogrenciAd;
        this.ogrenciSoyad=ogrenciSoyad;
        this.ogrenciTel=ogrenciTel;
        this.ogrenciEposta=ogrenciEposta;
        this.dogumTarihi=dogumTarihi;
    }
}
