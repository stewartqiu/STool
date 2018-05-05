package net.schooldroid.builder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.schooldroid.stool.STool;


public class Second extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        STool.openWebView(this, "http://darmayudha.com/en/category/news-id/","Web Sekolah");

    }


}
