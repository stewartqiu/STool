package net.schooldroid.builder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import net.schooldroid.stool.SGps;
import net.schooldroid.stool.STool;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements STool.locAccessListener {


    FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = LocationServices.getFusedLocationProviderClient(this);

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


        new async().execute();

        SGps.reqHighGps(this);

        final SGps sgps = new SGps(MainActivity.this);
        sgps.on(new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("GPS DEBUG", "" + location.getLatitude() + "," + location.getLongitude() );
                sgps.off();
            }
        });


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location!=null) {
                    Log.d("NEW GPS", location.toString());
                }
            }
        });


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


}
