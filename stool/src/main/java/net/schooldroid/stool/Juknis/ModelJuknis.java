package net.schooldroid.stool.Juknis;

import java.util.Comparator;

public class ModelJuknis {
    public String kategori;
    public String header;
    public String content;
    public int urut;

    public ModelJuknis(){

    }

    public ModelJuknis (String kategori, String header, String content, int urut) {
        this.kategori = kategori;
        this.header = header;
        this.content = changeContentToHtml(content);
        this.urut = urut;
    }


    public String changeContentToHtml(String content) {
        String change = "<ol><li>" + content.replace("~","</li><li>").replace("`","</li><br><li>") + "</li></ol>";
        return change;
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