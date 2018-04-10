package net.schooldroid.builder;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import net.schooldroid.stool.Juknis.ModelJuknis;
import net.schooldroid.stool.STool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements STool.locAccessListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Log.d("MacAddress",STool.getWifiMacAddress());
//        Log.d("Bluetooth_Address",STool.getBtMacAddress(this));



        Button button = findViewById(R.id.mybutt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                STool.handleLocationPermission(MainActivity.this, MainActivity.this);
            }
        });

    }



    @Override
    public void onSuccess() {


//        new async().execute();
//
//        SGps.reqHighGps(this);
//
//        final SGps sgps = new SGps(MainActivity.this);
//        sgps.on(new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                Log.d("GPS DEBUG", "" + location.getLatitude() + "," + location.getLongitude() );
//                sgps.off();
//            }
//        });


        testJuknis();

    }

    @SuppressLint("StaticFieldLeak")
    private class async extends AsyncTask<Void,Void,Void> {

        Map<String,String> result;
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected Void doInBackground(Void... voids) {
            result =  STool.getNpsnScrap("10496495");
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Sek...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            if (result!=null) {
                Log.d("Hehehe", String.valueOf(result.entrySet()));
            }
        }
    }


    private void testJuknis(){

        // CONTENT PAKAI PEMISAH ~ DAN `
        // ~ TIDAK ADA SPASI ANTAR LIST
        // ` TADA SPASI ANTAR LIST

        ArrayList<ModelJuknis> arrayList = new ArrayList<>();
        STool.newJuknisToArray(arrayList,"Pengumuman","1","1. Kirim Data Siswa (Excel) $o$r","Format excel yang diimport harus sesuai dengan format yang telah ditentukan", Second.class, R.drawable.ic_12, 500, 150);
        STool.newJuknisToArray(arrayList,"Pengumuman","2","2. Cetak PIN Siswa","Berikut adalah listnya : ^PIN bisa dicetak dan dibagikan ke siswa dan orang tua.~Siswa dan orang tua bisa menginstall aplikasi SISWA dan ORANG TUA dari Schooldroid~NPSN, KELAS, dan PIN dibutuhkan saat masuk aplikasi SISWA / ORANG TUA.^", null);
        STool.newJuknisToArray(arrayList,"Pengumuman","3","3. Cetak PIN Siswa","^PIN bisa dicetak dan dibagikan ke siswa dan orang tua.`Siswa dan orang tua bisa menginstall aplikasi SISWA dan ORANG TUA dari Schooldroid`NPSN, KELAS, dan PIN dibutuhkan saat masuk aplikasi SISWA / ORANG TUA.^", null);
        STool.newJuknisToArray(arrayList,"Pengumuman>UN","2.1","1. Header Pengumuman UN", "Content Saja",null);
        STool.newJuknisToArray(arrayList,"Pengumuman>UN","2.2","2. Header Pengumuman UN", "Content Saja",null);
        STool.newJuknisToArray(arrayList,"Pengumuman>UN","2.3","3. Header Pengumuman UN", "Content Saja",null);
        STool.newJuknisToArray(arrayList,"Pengumuman>UN>Spontan","2.3.1","1. Header Pengumuman UN Spontan", "",null);
        STool.newJuknisToArray(arrayList,"Pengumuman>UN>Spontan>Uhuy","2.3.1.1","1. Header Pengumuman UN Spontan Uhuy", "Content Saja",null);
        STool.newJuknisToArray(arrayList,"Pengumuman>Info","3.1","1. Header Pengumuman Info", "Content Saja",null);

        // Open now and highlight

        STool.showJuknis(this, arrayList, "Pengumuman",1);
    }


}
