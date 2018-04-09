package net.schooldroid.stool.Juknis;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import net.schooldroid.stool.R;

import java.util.ArrayList;
import java.util.Objects;

public class stJuknis extends AppCompatActivity {

    RecyclerView recyclerView;
    public static ArrayList<ModelJuknis> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.st_juknis);

        initialSetup();

    }


    private void initialSetup(){
        String kategori = arrayList.get(0).kategori;

        Objects.requireNonNull(getSupportActionBar()).setTitle(kategori);

        recyclerView = findViewById(R.id.recyclerViewJuknis);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new JuknisAdapter(recyclerView, arrayList, this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }



}
