package com.berat.ogrencibilgisistemm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.berat.ogrencibilgisistemm.databinding.ActivityMainBinding;
import com.berat.ogrencibilgisistemm.ogrenci.OgrenciActivity;
import com.berat.ogrencibilgisistemm.ogretmen.OgretmenActivity;
import com.berat.ogrencibilgisistemm.yonetim.YonetimActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Intent intent;
    private Cursor cursor;
    private SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // veri tabanını açar
        sqLiteDatabase=this.openOrCreateDatabase("Sistem", MODE_PRIVATE,null);

              /* sqLiteDatabase.execSQL("DELETE FROM kullanicilar");
                sqLiteDatabase.execSQL("DELETE FROM ogrenciler");
                sqLiteDatabase.execSQL("DELETE FROM dersler");
                 sqLiteDatabase.execSQL("DELETE FROM dersprogrami");
                sqLiteDatabase.execSQL("DELETE FROM ogretmenler");
                sqLiteDatabase.execSQL("DELETE FROM sinavsonuclari");
                sqLiteDatabase.execSQL("DELETE FROM devamsizlikKayitlari");
                */
    }


    public void giris(View view){
        String ad=binding.kullanCAdi.getText().toString();
        String sifre=binding.kullanCSifre.getText().toString();
        String kullaniciAd="";
        String kullaniciSifre="";
        String kullaniciTipi="";
        try {
            // kullanicilar tablosundan kullaniciAdi,kullaniciTipi ve kullaniciSifre çeker
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM kullanicilar WHERE kullaniciAdi=? AND kullaniciSifre=?"
                    , new String[]{ad, sifre});
            while (cursor.moveToNext()) {
                kullaniciAd = cursor.getString(1);
                kullaniciSifre = cursor.getString(2);
                kullaniciTipi = cursor.getString(3);
            }
            cursor.close();
            // girilen kullanici adı ve şifre yi kontrol eder
            if (kullaniciAd.equals(ad) && kullaniciSifre.equals(kullaniciSifre) && !kullaniciAd.equals("")) {
                // öğretmen girişi
                if (kullaniciTipi.equals("Öğretmen")) {

                    String ortId="";
                    cursor=sqLiteDatabase.rawQuery("SELECT kullaniciID FROM kullanicilar WHERE kullaniciAdi=? ",
                            new String[]{ad});
                    while (cursor.moveToNext()){
                        ortId=cursor.getString(0);
                    }
                    intent=new Intent(this,OgretmenActivity.class);
                    intent.putExtra("intentOrtID",ortId);
                    startActivity(intent);

                    Toast.makeText(this, "Öğretmen Girisi Yapıldı", Toast.LENGTH_LONG).show();

                }
                // öğrenci girişi
                else if (kullaniciTipi.equals("Öğrenci")) {
                    String ogrId="";
                    cursor=sqLiteDatabase.rawQuery("SELECT kullaniciID FROM kullanicilar WHERE kullaniciAdi=?",new String[]{ad});
                    while (cursor.moveToNext()){
                        ogrId=cursor.getString(0);
                    }
                    cursor.close();
                    intent=new Intent(this, OgrenciActivity.class);
                    intent.putExtra("intentOgrID",ogrId);
                    startActivity(intent);
                    Toast.makeText(this, "Öğrenci Girisi Yapıldı", Toast.LENGTH_LONG).show();

                }
                else {
                    Toast.makeText(this, "Kullanici Tipi Hatalı", Toast.LENGTH_LONG).show();
                }

            }
            // admin girişi
            else if (ad.equals("admin") && sifre.equals("admin")) {

                intent=new Intent(this,YonetimActivity.class);
                startActivity(intent);
                Toast.makeText(this,"Yönetim Girisi Yapıldı",Toast.LENGTH_LONG).show();

            }else {
                Toast.makeText(this,"Kullanici/Sifre Hatalı",Toast.LENGTH_LONG).show();
            }
            cursor.close();
        }catch (Exception e){
            e.fillInStackTrace();
        }

    }
}