package com.company.model;

import java.util.ArrayList;
import java.util.List;

public class Intersection {

    public Intersection(int id) {
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int id;


    public List<Street> getInStreets()
    {
        return new ArrayList<>();
    }

    public List<Street> getOutStreets()
    {
        return new ArrayList<>();
    }
}
