package net.schooldroid.stool;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.schooldroid.stool.Juknis.ModelJuknis;
import net.schooldroid.stool.Juknis.stJuknis;
import net.schooldroid.stool.WebView.WebView_Opener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.sufficientlysecure.htmltextview.HtmlTextView;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


public class STool {





    public static Map<String,String> getNpsnScrap(String npsn) {
        String baseUrl = "http://referensi.data.kemdikbud.go.id/tabs.php?npsn=";
        String urlToScrap = baseUrl + npsn;
        try {
            Document document = Jsoup.connect(urlToScrap).get();
            Elements elements = document.select("#tabs-1 table tbody tr td:first-child table tbody tr td:nth-child(4)"); //#box-table-a tbody tr:first-child td
            Elements elements1 = document.select("#tabs-6 table tbody tr:nth-child(4) td:nth-child(4)");

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
            dict.put("email",elements1.get(0).text());

            return dict;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }



    public static String changeContentToHtml(String content) {
        String change = "";
        String[] pisah = content.split(Pattern.quote("^"));

        for (int i = 0 ; i < pisah.length ; i++){
            if (i % 2 == 1) {
                pisah[i] = "<ol><li>" + pisah[i].replace("~","</li><li>").replace("`","</li><br><li>") + "</li></ol>";
            }

            change = change + pisah[i];
        }

        return change;


        //String change = "<ol><li>" + content.replace("~","</li><li>").replace("`","</li><br><li>") + "</li></ol>";
    }



    // TODO JUKNIS

    public static void showJuknis(Context context, ArrayList <ModelJuknis> juknisArrayList, String kategori) {

        ArrayList<ModelJuknis> array = new ArrayList<>();
        ArrayList<ModelJuknis> arraySub = new ArrayList<>();

        for (ModelJuknis item : juknisArrayList) {
            if (item.kategori.equals(kategori)) {
                array.add(item);
            } else if (item.kategori.startsWith(kategori+">")){
                arraySub.add(item);
            }
        }

        Collections.sort(array, ModelJuknis.Sort);
        stJuknis.arrayList = array;
        stJuknis.arrayListSub = arraySub;

        context.startActivity(new Intent(context, stJuknis.class));
    }

    public static void newJuknisToArray(ArrayList<ModelJuknis> juknisArrayList, String kategori, int urut, String header, String content , Class<?> linkToActivity){
        juknisArrayList.add(new ModelJuknis(kategori,header,content,String.valueOf(urut),linkToActivity));
    }

    public static void newJuknisToArray(ArrayList<ModelJuknis> juknisArrayList, String kategori, int urut, String header, String content , Class<?> linkToActivity, @RawRes @DrawableRes @Nullable Integer imageResourceId, int imageWidth , int imageHeight){
        juknisArrayList.add(new ModelJuknis(kategori,header,content,String.valueOf(urut),linkToActivity,imageResourceId,imageWidth, imageHeight));
    }


    public static void newJuknisToArray(ArrayList<ModelJuknis> juknisArrayList, String kategori, String urut, String header, String content , Class<?> linkToActivity){
        juknisArrayList.add(new ModelJuknis(kategori,header,content,urut,linkToActivity));
    }

    public static void newJuknisToArray(ArrayList<ModelJuknis> juknisArrayList, String kategori, String urut, String header, String content , Class<?> linkToActivity, @RawRes @DrawableRes @Nullable Integer imageResourceId, int imageWidth , int imageHeight){
        juknisArrayList.add(new ModelJuknis(kategori,header,content,urut,linkToActivity,imageResourceId,imageWidth, imageHeight));
    }







    // TODO MESSAGE BOX

    public static void msgBox(Activity activity , String judul, String isiHtml, String tombol1, String tombol2 ,String tombol3){

        View customView =  activity.getLayoutInflater().inflate(R.layout.custom_msgbox,null);
        TextView tvJudul = customView.findViewById(R.id.title_msgbox);
        HtmlTextView tvIsi = customView.findViewById(R.id.content_msgbox);
        Button btn1 = customView.findViewById(R.id.btn1_msgbox);
        Button btn2 = customView.findViewById(R.id.btn2_msgbox);
        Button btn3 = customView.findViewById(R.id.btn3_msgbox);

        tvJudul.setText(judul);
        tvIsi.setHtml(STool.changeContentToHtml(isiHtml));

        btn1.setVisibility(View.VISIBLE);
        btn2.setVisibility(View.VISIBLE);
        btn3.setVisibility(View.VISIBLE);

        btn1.setText(tombol1);
        btn2.setText(tombol2);
        btn3.setText(tombol3);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        AlertDialog builder = new AlertDialog.Builder(activity)
                .setView(customView)
                .create();

        builder.show();
    }


    public static void msgBox(Activity activity , String judul, String isiHtml, String tombol1, String tombol2){

        View customView =  activity.getLayoutInflater().inflate(R.layout.custom_msgbox,null);
        TextView tvJudul = customView.findViewById(R.id.title_msgbox);
        HtmlTextView tvIsi = customView.findViewById(R.id.content_msgbox);
        Button btn1 = customView.findViewById(R.id.btn1_msgbox);
        Button btn2 = customView.findViewById(R.id.btn2_msgbox);
        Button btn3 = customView.findViewById(R.id.btn3_msgbox);

        tvJudul.setText(judul);
        tvIsi.setHtml(STool.changeContentToHtml(isiHtml));

        btn1.setVisibility(View.VISIBLE);
        btn2.setVisibility(View.VISIBLE);
        btn3.setVisibility(View.GONE);

        btn1.setText(tombol1);
        btn2.setText(tombol2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        AlertDialog builder = new AlertDialog.Builder(activity)
                .setView(customView)
                .create();

        builder.show();
    }

    public static void msgBox(Activity activity , String judul, String isiHtml, String tombol1){

        View customView =  activity.getLayoutInflater().inflate(R.layout.custom_msgbox,null);
        TextView tvJudul = customView.findViewById(R.id.title_msgbox);
        HtmlTextView tvIsi = customView.findViewById(R.id.content_msgbox);
        Button btn1 = customView.findViewById(R.id.btn1_msgbox);
        Button btn2 = customView.findViewById(R.id.btn2_msgbox);
        Button btn3 = customView.findViewById(R.id.btn3_msgbox);

        tvJudul.setText(judul);
        tvIsi.setHtml(STool.changeContentToHtml(isiHtml));

        btn1.setVisibility(View.GONE);
        btn2.setVisibility(View.VISIBLE);
        btn3.setVisibility(View.GONE);

        btn2.setText(tombol1);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        AlertDialog builder = new AlertDialog.Builder(activity)
                .setView(customView)
                .create();

        builder.show();
    }







    // TODO WEB VIEW OPEN
    public static void openWebView(Context context, String url, String actionBarTitle) {
        WebView_Opener.url = url;
        WebView_Opener.title = actionBarTitle;
        context.startActivity(new Intent(context,WebView_Opener.class));
    }






    // TODO UTILS

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

        String result = android.provider.Settings.Secure.getString(context.getContentResolver(), "bluetooth_address");

        if (result != null) {
            return result;
        }

        return "";
    }




//    public static String changeRomawiKelas(String kelas) {
//        String result = kelas.toUpperCase().replace("XV","15").replace("XIV","14").replace("XIII","13")
//                .replace("XII","12").replace("XI","11").replace("X","10")
//                .replace("IX","9").replace("VIII","8").replace("VII","7").replace("VI","6").replace("V","5")
//                .replace("IV","4").replace("III","3").replace("II","2").replace("I","1").replace(" ","");
//
//        return result;
//    }





}