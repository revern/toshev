package com.example.almaz.test.Model;

/**
 * Created by almaz on 14.11.2015.
 */
public class ClothesSet {
    private String head;
    private String body;
    private String bodyTop;
    private String legs;
    private String footwear;
    private String accessory;

    public ClothesSet(){

    }

    public ClothesSet(String head, String body, String bodyTop, String legs, String footwear, String accessory){
        this.head=head;
        this.body=body;
        this.bodyTop=bodyTop;
        this.legs=legs;
        this.footwear=footwear;
        this.accessory=accessory;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBodyTop() {
        return bodyTop;
    }

    public void setBodyTop(String bodyTop) {
        this.bodyTop = bodyTop;
    }

    public String getLegs() {
        return legs;
    }

    public void setLegs(String legs) {
        this.legs = legs;
    }

    public String getFootwear() {
        return footwear;
    }

    public void setFootwear(String footwear) {
        this.footwear = footwear;
    }

    public String getAccessory() {
        return accessory;
    }

    public void setAccessory(String accessory) {
        this.accessory = accessory;
    }
}
