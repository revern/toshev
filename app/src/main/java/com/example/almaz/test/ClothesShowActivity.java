package com.example.almaz.test;

import android.database.Cursor;
import android.net.Uri;
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

import com.example.almaz.test.Model.Clothes;
import com.example.almaz.test.Model.ClothesSet;
import com.example.almaz.test.Model.Forecast;
import com.example.almaz.test.Model.Weather;
import com.example.almaz.test.openweatherapi.ForecastService;
import com.example.almaz.test.openweatherapi.WeatherRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClothesShowActivity extends AppCompatActivity {
    public static final String EXTRA_STYLE = "EXTRA STYLE";

    final Uri CLOTHES_URI = Uri
            .parse("content://almaz.example.com.test/contacts");
    final String CLOTHES_NAME = "name";
    final String CLOTHES_LAYOUT = "layout";
    final String CLOTHES_TEMPERATURE_COEFFICIENT = "temperature_coefficient";
    final String CLOTHES_STYLE_OFFICIAL = "style_official";
    final String CLOTHES_STYLE_REGULAR = "style_regular";
    final String CLOTHES_STYLE_SPORT = "style_sport";
    final String CLOTHES_STYLE_EVENING = "style_evening";

    private Weather weather;
    private String style;
    private Cursor cursor;

    RecyclerView mRcView_1;
    RecyclerView mRcView_2;
    RecyclerView mRcView_3;
    RecyclerView mRcView_4;
    RecyclerView mRcView_5;
    RecyclerView mRcView_6;

    List<String> headUris;
    List<String> bodyUris;
    List<String> bodyTopUris;
    List<String> legsUris;
    List<String> footwearUris;
    List<String> accessoryUris;

    List<Clothes> headStyledClothes;
    List<Clothes> bodyStyledClothes;
    List<Clothes> bodyTopStyledClothes;
    List<Clothes> legsStyledClothes;
    List<Clothes> footwearStyledClothes;
    List<Clothes> accessoryStyledClothes;

    List<File> sketches1;
    List<File> sketches2;
    List<File> sketches3;
    List<File> sketches4;
    List<File> sketches5;
    List<File> sketches6;

    private SpiceManager spiceManager = new SpiceManager(ForecastService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO: add locations
        double currentLatitude = 55.7679;
        double currentLongitude = 49.1631;

        processLocation(currentLatitude, currentLongitude);

        mRcView_1= (RecyclerView) findViewById(R.id.rcView_1);
        mRcView_2= (RecyclerView) findViewById(R.id.rcView_2);
        mRcView_3= (RecyclerView) findViewById(R.id.rcView_3);
        mRcView_4= (RecyclerView) findViewById(R.id.rcView_4);
        mRcView_5= (RecyclerView) findViewById(R.id.rcView_5);
        mRcView_6= (RecyclerView) findViewById(R.id.rcView_6);

        style = getIntent().getStringExtra(EXTRA_STYLE);

        weather = new Weather();
        cursor = getContentResolver().query(CLOTHES_URI, null, null,
                null, null);
        cursor.moveToFirst();
        clothesSet();

        sketches1=imageReader(Environment.getExternalStorageDirectory());
        sketches2=imageReader(Environment.getExternalStorageDirectory());
        sketches3=imageReader(Environment.getExternalStorageDirectory());
        sketches4=imageReader(Environment.getExternalStorageDirectory());
        sketches5=imageReader(Environment.getExternalStorageDirectory());
        sketches6=imageReader(Environment.getExternalStorageDirectory());

        headUris = new ArrayList<>();
        bodyUris = new ArrayList<>();
        bodyTopUris = new ArrayList<>();
        legsUris = new ArrayList<>();
        footwearUris = new ArrayList<>();
        accessoryUris = new ArrayList<>();

        setRecyclerAdapter(mRcView_1, sketches1);
        setRecyclerAdapter(mRcView_2, sketches2);
        setRecyclerAdapter(mRcView_3, sketches3);
        setRecyclerAdapter(mRcView_4, sketches4);
        setRecyclerAdapter(mRcView_5, sketches5);
        setRecyclerAdapter(mRcView_6, sketches6);


    }
    @Override
    protected void onStart() {
        super.onStart();
        spiceManager.start(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        spiceManager.shouldStop();
    }

    protected SpiceManager getSpiceManager() {
        return spiceManager;
    }

    public void processLocation(double currentLatitude, double currentLongitude) {

        WeatherRequest request = new WeatherRequest(currentLongitude, currentLatitude);
        getSpiceManager().execute(request, new RequestListener<Forecast>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {

            }

            @Override
            public void onRequestSuccess(Forecast currentForecast) {
                // TODO: here you can get all your weather parametrs
                Log.d("TAG", currentForecast.name);
            }
        });
    }


    public ClothesSet clothesSet(){
        cursor.moveToFirst();
        switch (style) {


            case "official":
                for(int i=0;i<cursor.getCount();i++){
                    if(cursor.getString(4).equals("1")){
                        addStyledClothes();
                    }
                    cursor.moveToNext();
                }
                break;
            case "regular":
                for(int i=0;i<cursor.getCount();i++){
                    if(cursor.getString(5).equals("1")){
                        addStyledClothes();
                    }
                    cursor.moveToNext();
                }
                break;
            case "sport":
                for(int i=0;i<cursor.getCount();i++){
                    if(cursor.getString(6).equals("1")){
                        addStyledClothes();
                    }
                    cursor.moveToNext();
                }
                break;
            case "evening":
                for(int i=0;i<cursor.getCount();i++){
                    if(cursor.getString(7).equals("1")){
                        addStyledClothes();
                    }
                    cursor.moveToNext();
                }
                break;
        }
        ClothesSet clothesSet = new ClothesSet();
        return clothesSet;
    }

    public void addStyledClothes(){
        boolean b4=false;
        boolean b5=false;
        boolean b6=false;
        boolean b7=false;
        boolean b8=false;
        boolean b9=false;
        if(cursor.getString(4).equals("1")){ b4 = true; }
        if(cursor.getString(5).equals("1")){ b5 = true; }
        if(cursor.getString(6).equals("1")){ b6 = true; }
        if(cursor.getString(7).equals("1")){ b7 = true; }
        if(cursor.getString(8).equals("1")){ b8 = true; }
        if(cursor.getString(9).equals("1")){ b9 = true; }
        switch (cursor.getString(2)) {
            case "head":
                headUris.add(cursor.getString(1));
                headStyledClothes.add(new Clothes(cursor.getString(1), cursor.getString(2), cursor.getInt(3), b4, b5, b6, b7, b8, b9));
                break;
            case "body":
                bodyUris.add(cursor.getString(1));
                bodyStyledClothes.add(new Clothes(cursor.getString(1), cursor.getString(2), cursor.getInt(3), b4, b5, b6, b7, b8, b9));
                break;
            case "bodyTop":
                bodyTopUris.add(cursor.getString(1));
                bodyTopStyledClothes.add(new Clothes(cursor.getString(1), cursor.getString(2), cursor.getInt(3), b4, b5, b6, b7, b8, b9));
                break;
            case "legs":
                legsUris.add(cursor.getString(1));
                legsStyledClothes.add(new Clothes(cursor.getString(1), cursor.getString(2), cursor.getInt(3), b4, b5, b6, b7, b8, b9));
                break;
            case "footwear":
                footwearUris.add(cursor.getString(1));
                footwearStyledClothes.add(new Clothes(cursor.getString(1), cursor.getString(2), cursor.getInt(3), b4, b5, b6, b7, b8, b9));
                break;
            case "accessory":
                accessoryUris.add(cursor.getString(1));
                accessoryStyledClothes.add(new Clothes(cursor.getString(1), cursor.getString(2), cursor.getInt(3), b4, b5, b6, b7, b8, b9));
                break;
        }
    }

    public ClothesSet createClothesSet(){
        ClothesSet clothesSet = new ClothesSet();
        //accessory adding
        if(weather.getWeather().equals("sky is clear") && weather.getTemperature() >= 0){
            //add sunglasses
        }else if(weather.getTemperature()>=0 && weather.getWeather().equals("Rain")){
            //add umbrella
        }else if(weather.getTemperature()<0){
            //add scarf+gloves
        }

        //head adding
        if(weather.getTemperature()<0){
            //add a cap
        }

        //body and bodyTop adding
        int minFault = 99;
        Clothes bestBody = bodyStyledClothes.get(0);
        Clothes bestBodyTop = bodyTopStyledClothes.get(0);
        for(int i = 0; i < bodyStyledClothes.size(); i++){
            for(int j=0; j <bodyTopStyledClothes.size(); j++){
                int fault = bodyStyledClothes.get(i).getTemperatureCoefficient()+bodyTopStyledClothes.get(j).getTemperatureCoefficient()+weather.getTemperature()-25;
                fault = Math.abs(fault);
                if(fault<minFault) {
                    minFault=fault;
                    bestBody=bodyStyledClothes.get(i);
                    bestBodyTop=bodyTopStyledClothes.get(j);
                }
            }
        }
        clothesSet.setBody(bestBody);
        clothesSet.setBodyTop(bestBodyTop);

        //legs adding
        minFault = 99;
        Clothes  bestLegs = legsStyledClothes.get(0);
        for(int i=0;i<legsStyledClothes.size();i++){
            if(legsStyledClothes.get(i).getTemperatureCoefficient()<minFault){
                minFault = legsStyledClothes.get(i).getTemperatureCoefficient();
                bestLegs = legsStyledClothes.get(i);
            }
        }
        clothesSet.setLegs(bestLegs);

        //
        minFault = 99;
        Clothes  bestFootwear = footwearStyledClothes.get(0);
        for(int i=0;i<footwearStyledClothes.size();i++){
            if(footwearStyledClothes.get(i).getTemperatureCoefficient()<minFault){
                minFault = footwearStyledClothes.get(i).getTemperatureCoefficient();
                bestFootwear = footwearStyledClothes.get(i);
            }
        }
        clothesSet.setFootwear(bestFootwear);

        return clothesSet;
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

}
