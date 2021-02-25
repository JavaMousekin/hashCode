package com.company.model;

import java.util.List;
import java.util.stream.Collectors;

public class Car {
    public List<Street> getStreets() {
        return streets;
    }

    public void setStreets(List<Street> streets) {
        this.streets = streets;
    }

    public Car(List<Street> streets) {
        this.streets = streets;
    }

    public List<Street> streets;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int priority;
    public int amountOfIntersections= streets.size()-1;
    public int totalAmountOFStreets= streets.size();

}
