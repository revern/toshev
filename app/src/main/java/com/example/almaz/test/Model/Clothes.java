package com.example.almaz.test.Model;

/**
 * Created by almaz on 14.11.2015.
 */
public class Clothes {
    private String name;
    private String layout;
    private int temperatureCoefficient;
    private boolean windproof;
    private boolean rainCover;
    private boolean style_official;
    private boolean style_regular;
    private boolean style_sport;
    private boolean style_evening;

    public Clothes(){

    }

    public Clothes(String name, String layout, int temperatureCoefficient, boolean windproof, boolean rainCover, boolean style_official, boolean style_regular, boolean style_sport,  boolean style_evening){
        this.name=name;
        this.layout=layout;
        this.temperatureCoefficient=temperatureCoefficient;
        this.windproof=windproof;
        this.rainCover=rainCover;
        this.style_official=style_official;
        this.style_regular=style_regular;
        this.style_sport=style_sport;
        this.style_evening=style_evening;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public int getTemperatureCoefficient() {
        return temperatureCoefficient;
    }

    public void setTemperatureCoefficient(int temperatureCoefficient) {
        this.temperatureCoefficient = temperatureCoefficient;
    }

    public boolean isWindproof() {
        return windproof;
    }

    public void setWindproof(boolean windproof) {
        this.windproof = windproof;
    }

    public boolean isRainCover() {
        return rainCover;
    }

    public void setRainCover(boolean rainCover) {
        this.rainCover = rainCover;
    }

    public boolean isStyle_official() {
        return style_official;
    }

    public void setStyle_official(boolean style_official) {
        this.style_official = style_official;
    }

    public boolean isStyle_regular() {
        return style_regular;
    }

    public void setStyle_regular(boolean style_regular) {
        this.style_regular = style_regular;
    }

    public boolean isStyle_sport() {
        return style_sport;
    }

    public void setStyle_sport(boolean style_sport) {
        this.style_sport = style_sport;
    }

    public boolean isStyle_evening() {
        return style_evening;
    }

    public void setStyle_evening(boolean style_evening) {
        this.style_evening = style_evening;
    }
}
