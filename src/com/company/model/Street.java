package com.company.model;

public class Street {

    public int startIntersection;
    public int endIntersection;
    public String name;
    public int length;

    public Street(int startIntersection, int endIntersection, String name, int length, Streetlight streetlight) {
        this.startIntersection = startIntersection;
        this.endIntersection = endIntersection;
        this.name = name;
        this.length = length;
        this.streetlight = streetlight;
    }

    public int getStartIntersection() {
        return startIntersection;
    }

    public void setStartIntersection(int startIntersection) {
        this.startIntersection = startIntersection;
    }

    public int getEndIntersection() {
        return endIntersection;
    }

    public void setEndIntersection(int endIntersection) {
        this.endIntersection = endIntersection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Streetlight streetlight;
}
