package net.schooldroid.builder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import net.schooldroid.stool.STool;

public class MainActivity extends AppCompatActivity implements STool.locAccessListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        Log.d("location","YEEEE BERHASIL!!!!!!!");
    }
}
