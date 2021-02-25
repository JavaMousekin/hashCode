package com.company.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Intersection {

    public Intersection(int id) {
        this.id = id;
        this.greenLightAtStreets = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int id;
    public Map<String, Integer> greenLightAtStreets;

    public void doCalculationsWhenPassingIntersection(Street currentStreet) {
        if (greenLightAtStreets.containsKey(currentStreet.name)) {
            int light = greenLightAtStreets.get(currentStreet.name);
            greenLightAtStreets.put(currentStreet.name, light + 1);
        } else {
            greenLightAtStreets.put(currentStreet.name, 1);
        }

    }

    public List<Street> getInStreets() {
        return new ArrayList<>();
    }

    public List<Street> getOutStreets() {
        return new ArrayList<>();
    }
}
