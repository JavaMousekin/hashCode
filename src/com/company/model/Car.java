package com.company.model;

import com.company.Main;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.company.Main.getDifferentIntersections;

public class Car implements Comparable<Car>{
    public Car(List<Street> streets) {
        this.streets = streets;
        this.amountOfIntersections = streets.size()-1;
        this.totalAmountOFStreets = streets.size();
        intersectionsTimeCrossing = new HashMap<>();
        initializeIntersectionLength();
    }

    public List<Street> getStreets() {
        return streets;
    }

    public List<Street> streets;
    public Map<Integer, Integer> intersectionsTimeCrossing;
    public int priority;
    public int amountOfIntersections;
    public int totalAmountOFStreets;
    public boolean isCompleted = false;

    /*возвращает улицу, в конце которой стоит в данную секунду. если находится в середине, возвращает нулл*/
    public Street isAtTheEndOfStreet(int currentSecond) {
        int leftSeconds = currentSecond;
        for (int i = 0; i < streets.size() && leftSeconds > 0; i++) {
            Street street = streets.get(i);
            leftSeconds = leftSeconds - street.length;
            if (currentSecond - intersectionsTimeCrossing.get(street.endIntersection) == 0) {
                return street;
            }
        }
        return null;
    }

    public void initializeIntersectionLength()
    {
        for (Street street: streets)
        {
            intersectionsTimeCrossing.put(street.endIntersection, Integer.MAX_VALUE);
        }
    }

    public void doCalculationsWhenPassingIntersection(int intersection, int secondsPassing) {
        intersectionsTimeCrossing.put(intersection, secondsPassing);
        if (isBeforeLastIntersection(intersection)) {
            isCompleted = true;
        }
    }

    public boolean isBeforeLastIntersection(int intersection)
    {
        List<Integer> allIntersectons= Main.getDifferentIntersections(this.streets);
        if(allIntersectons.size()>2) {
            if (allIntersectons.contains(intersection) && allIntersectons.indexOf(intersection) == allIntersectons.size() - 2) {
                return true;
            }
        }
        if(allIntersectons.size()==2 && allIntersectons.indexOf(intersection)==1){
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Car o) {
        return Integer.compare(priority, o.priority);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(streets, car.streets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streets);
    }
}
