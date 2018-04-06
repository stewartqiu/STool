package net.schooldroid.stool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class STool {

    public interface locAccessListener{
        void onSuccess();
    }


    public static void handleLocationPermission(Activity activity, STool.locAccessListener listener){
        boolean isAllow = checkLocationPermission(activity);
        if (!isAllow) {
            ReqLocationActivity.listener = listener;
            activity.startActivity(new Intent(activity, ReqLocationActivity.class));
        } else {
            listener.onSuccess();
        }
    }

    public static boolean checkLocationPermission(Context context) {
        return Build.VERSION.SDK_INT < 23 || ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public static Map<String,String> getNpsnScrap(String npsn) {
        String baseUrl = "http://referensi.data.kemdikbud.go.id/tabs.php?npsn=";
        String urlToScrap = baseUrl + npsn;
        try {
            Document document = Jsoup.connect(urlToScrap).get();
            Elements elements = document.select("#tabs-1 table tbody tr td:first-child table tbody tr td:nth-child(4)"); //#box-table-a tbody tr:first-child td

            Map<String,String> dict = new HashMap<>();

            dict.put("nama", elements.get(0).text());
            dict.put("npsn",elements.get(1).text());
            dict.put("alamat",elements.get(2).text());
            dict.put("kode_pos",elements.get(3).text());
            dict.put("kel",elements.get(4).text());
            dict.put("kec",elements.get(5).text());
            dict.put("kab",elements.get(6).text());
            dict.put("prov",elements.get(7).text());
            dict.put("status",elements.get(8).text());
            dict.put("waktu",elements.get(9).text());
            dict.put("jenjang",elements.get(10).text());

            return dict;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }



}