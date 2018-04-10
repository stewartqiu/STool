package net.schooldroid.builder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Second extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        String bruh = "1.1.1.1";
        Log.d("NGOPI BLOM?", bruh.substring(0, bruh.lastIndexOf(".")));

    }
}
