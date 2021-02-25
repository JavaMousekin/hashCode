package com.company.model;

public class Street {
    public Street(Intersection startIntersection, Intersection endIntersection, String name, int length) {
        this.startIntersection = startIntersection;
        this.endIntersection = endIntersection;
        this.name = name;
        this.length = length;
    }

    public Intersection startIntersection;
    public Intersection endIntersection;
    public String name;
    public int length;
}
