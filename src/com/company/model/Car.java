package com.company.model;

import java.util.List;
import java.util.stream.Collectors;

public class Car {
    public Car(List<Street> streets) {
        this.streets = streets;
    }

    public List<Street> streets;
    public int priority;

}
