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
    public static int openUrut;
    public static ArrayList<ModelJuknis> arrayList;
    public static ArrayList<ModelJuknis> arrayListSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.st_juknis);

        initialSetup();
    }


    private void initialSetup(){
        String kategori = arrayList.get(0).kategori;

        Objects.requireNonNull(getSupportActionBar()).setTitle(kategori);


        handleSubJuknis();

        recyclerView = findViewById(R.id.recyclerViewJuknis);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new JuknisAdapter(recyclerView, arrayList, arrayListSub, this, openUrut));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void handleSubJuknis(){
        if (arrayListSub != null && !arrayListSub.isEmpty()) {

            for (ModelJuknis subJuknis : arrayListSub) {
                int lastIndexOfDot = subJuknis.urut.lastIndexOf(".");
                String urutParent = subJuknis.urut.substring(0, lastIndexOfDot);

                String kategoriSub = subJuknis.kategori;

                for (ModelJuknis parentJuknis : arrayList) {
                    if (parentJuknis.urut.equals(urutParent)){
                        parentJuknis.kategoriSubJuknis = kategoriSub;
                    }
                }

            }
        }
    }



}
