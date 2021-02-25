package com.company.model;

import java.util.List;
import java.util.Map;

public class Car implements Comparable<Car>{
    public Car(List<Street> streets) {
        this.streets = streets;
    }

    public List<Street> streets;
    public Map<Integer, Integer> intersectionsTimeCrossing;
    public int priority;
    public int amountOfIntersections= streets.size()-1;
    public int totalAmountOFStreets= streets.size();
    public boolean isCompleted = false;

    /*возвращает улицу, в конце которой стоит в данную секунду. если находится в середине, возвращает нулл*/
    public Street isAtTheEndOfStreet(int currentSecond)
    {
        int leftSeconds = currentSecond;
        for (int i = 0; i < streets.size() && leftSeconds>0; i++) {
            Street street = streets.get(i);
            leftSeconds = leftSeconds-street.length;
            if(currentSecond-street.streetlight.greenLight==0)
            {
                return street;
            }
        }
       return null;
    }

    public void doCalculationsWhenPassingIntersection()
    {

    }

    @Override
    public int compareTo(Car o) {
        return Integer.compare(priority, o.priority);
    }
}
