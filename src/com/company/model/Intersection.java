package com.company.model;

public class Intersection {

    public Intersection(int id, int greenLight) {
        this.id = id;
        this.greenLight = greenLight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGreenLight() {
        return greenLight;
    }

    public void setGreenLight(int greenLight) {
        this.greenLight = greenLight;
    }

    public int id;
    public int greenLight;
}
