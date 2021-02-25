package com.company.model;

public class Street {
    public Street(int startIntersection, int endIntersection, String name, int length) {
        this.startIntersection = startIntersection;
        this.endIntersection = endIntersection;
        this.name = name;
        this.length = length;
    }

    public int startIntersection;
    public int endIntersection;
    public String name;
    public int length;
    public Streetlight streetlight;
}
