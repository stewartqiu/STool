package net.schooldroid.stool.Juknis;

import android.app.Activity;
import android.util.Log;

import org.jsoup.helper.StringUtil;

import java.util.Comparator;
import java.util.regex.Pattern;

public class ModelJuknis {
    public String kategori;
    public String header;
    public String content;
    public int urut;
    public Class<?> linkToActivity;

    public ModelJuknis(){

    }

    public ModelJuknis (String kategori, String header, String content, int urut, Class<?> linkToActivity) {
        this.kategori = kategori;
        this.header = header;
        this.content = changeContentToHtml(content);
        this.urut = urut;
        this.linkToActivity = linkToActivity;
    }

    public String changeContentToHtml(String content) {


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


    public static Comparator<ModelJuknis> Sort = new Comparator<ModelJuknis>() {
        @Override
        public int compare(ModelJuknis o1, ModelJuknis o2) {
            String urut1 = String.valueOf(o1.urut);
            String urut2 = String.valueOf(o2.urut);

            return urut1.compareTo(urut2);
        }
    };

}