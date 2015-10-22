package com.example.almaz.test;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRcView_1;
    RecyclerView mRcView_2;
    RecyclerView mRcView_3;
    RecyclerView mRcView_4;
    RecyclerView mRcView_5;
    RecyclerView mRcView_6;
    List<File> sketches1;
    List<File> sketches2;
    List<File> sketches3;
    List<File> sketches4;
    List<File> sketches5;
    List<File> sketches6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRcView_1= (RecyclerView) findViewById(R.id.rcView_1);
        mRcView_2= (RecyclerView) findViewById(R.id.rcView_2);
        mRcView_3= (RecyclerView) findViewById(R.id.rcView_3);
        mRcView_4= (RecyclerView) findViewById(R.id.rcView_4);
        mRcView_5= (RecyclerView) findViewById(R.id.rcView_5);
        mRcView_6= (RecyclerView) findViewById(R.id.rcView_6);
        sketches1=imageReader(Environment.getExternalStorageDirectory());
        sketches2=imageReader(Environment.getExternalStorageDirectory());
        sketches3=imageReader(Environment.getExternalStorageDirectory());
        sketches4=imageReader(Environment.getExternalStorageDirectory());
        sketches5=imageReader(Environment.getExternalStorageDirectory());
        sketches6=imageReader(Environment.getExternalStorageDirectory());
        setRecyclerAdapter(mRcView_1, sketches1);
        setRecyclerAdapter(mRcView_2, sketches2);
        setRecyclerAdapter(mRcView_3, sketches3);
        setRecyclerAdapter(mRcView_4, sketches4);
        setRecyclerAdapter(mRcView_5, sketches5);
        setRecyclerAdapter(mRcView_6, sketches6);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void setRecyclerAdapter(RecyclerView recyclerView, List list){
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getApplicationContext(), list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);
    }

    ArrayList<File> imageReader(File root){
        ArrayList<File> a = new ArrayList<>();
        File[] files = root.listFiles();
        try {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    a.addAll(imageReader(files[i]));
                } else {
                    if (files[i].getName().endsWith(".jpg") || files[i].getName().endsWith(".jpeg") || files[i].getName().endsWith(".png") || files[i].getName().endsWith(".gif")) {
                        a.add(files[i]);
                    }
                }

            }
        }catch (Exception e){
            Log.d("EXEPTION:", e.getStackTrace().toString());
        }
        return a;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
