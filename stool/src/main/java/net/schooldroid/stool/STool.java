package net.schooldroid.stool;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.Display;

import net.schooldroid.stool.Juknis.ModelJuknis;
import net.schooldroid.stool.Juknis.stJuknis;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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


    public static void showJuknis(Context context, ArrayList <ModelJuknis> juknisArrayList, String kategori) {

        // ambil yang hanya kategori

        ArrayList<ModelJuknis> array = new ArrayList<>();

        for (ModelJuknis item : juknisArrayList) {
            if (item.kategori.equals(kategori)) {
                array.add(item);
            }
        }

        Collections.sort(array,ModelJuknis.Sort);
        stJuknis.arrayList =  array;
        context.startActivity(new Intent(context, stJuknis.class));
    }

    public static void newJuknisToArray(ArrayList<ModelJuknis> juknisArrayList, String kategori, int urut, String header, String content , Class<?> linkToActivity){
        juknisArrayList.add(new ModelJuknis(kategori,header,content,urut,linkToActivity));
    }



    public static String getWifiMacAddress() {
        try {
            String interfaceName = "wlan0";
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (!intf.getName().equalsIgnoreCase(interfaceName)){
                    continue;
                }

                byte[] mac = intf.getHardwareAddress();
                if (mac==null){
                    return "";
                }

                StringBuilder buf = new StringBuilder();
                for (byte aMac : mac) {
                    buf.append(String.format("%02X:", aMac));
                }
                if (buf.length()>0) {
                    buf.deleteCharAt(buf.length() - 1);
                }
                return buf.toString();
            }
        } catch (Exception ex) { }
        return "";
    }



    public static String getBtMacAddress(Context context){
        return android.provider.Settings.Secure.getString(context.getContentResolver(), "bluetooth_address");
    }



}