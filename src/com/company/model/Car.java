package com.company.model;

import java.util.List;
import java.util.stream.Collectors;

public class Car {
    public Car(List<Street> streets) {
        this.streets = streets;
    }

    public List<Street> streets;

    public List<Intersection> getCarIntersections()
    {
        return streets.stream().map(x->x.endIntersection).collect(Collectors.toList());
    }
}
