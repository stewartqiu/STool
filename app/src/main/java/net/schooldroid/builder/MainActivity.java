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
        STool.newJuknisToArray(arrayList,"PengumumanUn",1,"1. Kirim Data Siswa (Excel)","Format excel yang diimport harus sesuai dengan format yang telah ditentukan", Second.class);
        STool.newJuknisToArray(arrayList, "PengumumanUn",2,"2. Cetak PIN Siswa","Berikut adalah listnya : ^PIN bisa dicetak dan dibagikan ke siswa dan orang tua.~Siswa dan orang tua bisa menginstall aplikasi SISWA dan ORANG TUA dari Schooldroid~NPSN, KELAS, dan PIN dibutuhkan saat masuk aplikasi SISWA / ORANG TUA.^",null);
        STool.newJuknisToArray(arrayList, "PengumumanUn",3,"3. Cetak PIN Siswa","^PIN bisa dicetak dan dibagikan ke siswa dan orang tua.`Siswa dan orang tua bisa menginstall aplikasi SISWA dan ORANG TUA dari Schooldroid`NPSN, KELAS, dan PIN dibutuhkan saat masuk aplikasi SISWA / ORANG TUA.^",null);

        STool.showJuknis(this, arrayList, "PengumumanUn");
    }

//    public static String kategoriUn = "Pengumuman UN";
//
//    public ArrayList<ModelJuknis> getJuknisPengumumanUn(){
//
//        ArrayList<ModelJuknis> array = new ArrayList<>();
//
//        array.add(new ModelJuknis(kategoriUn,"1. Kirim Data Siswa (Excel)", "Format excel yang diimport harus sesuai dengan format yang telah ditentukan", 1));
//        array.add(new ModelJuknis(kategoriUn,"2. Cetak PIN Siswa", "PIN bisa dicetak dan dibagikan ke siswa dan orang tua.~Siswa dan orang tua bisa menginstall aplikasi SISWA dan ORANG TUA dari Schooldroid~NPSN, KELAS, dan PIN dibutuhkan saat masuk aplikasi SISWA / ORANG TUA.", 2));
//        array.add(new ModelJuknis(kategoriUn,"3. Simulasi Pengmuman", "Anda dapat melakukan simulasi pengumuman, untuk memastikan pesan sampai pada siswa / orang tua.", 3));
//        array.add(new ModelJuknis(kategoriUn,"4. Kirim Data UN per Jurusan", "Format excel yang diimport harus sesuai dengan format yang telah ditentukan.~Kolom STATUS:<ul>" +
//                "<li> [K] Kirim, untuk siswa yang telah menyelesaikan administrasi dan keuangan, dapat langsung menerima pengumuman pada saat tombol \"Umumkan Sekarang\" ditekan.</li>" +
//                "<li> [T] Tunda, untuk siswa yang belum menyelesaikan administrasi dan keuangan, akan mendapat informasi kelulusan setelah diijinkan pada Form \"Pengaturan Lanjutan\".</li>" +
//                "</ul>", 4));
//
//        Collections.sort(array,ModelJuknis.Sort);
//
//        return array;
//    }


}
