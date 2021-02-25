package com.company.model;

import java.util.List;
import java.util.stream.Collectors;

public class Car {
    public List<Street> streets;
    public int priority;

    public List<Intersection> getCarIntersections()
    {
        return streets.stream().map(x->x.endIntersection).collect(Collectors.toList());
    }
}
