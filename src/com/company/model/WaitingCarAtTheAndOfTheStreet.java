package com.company.model;

public class WaitingCarAtTheAndOfTheStreet {
    public Car car;
    public Street street;
    public int timeStaying;

    public WaitingCarAtTheAndOfTheStreet(Car car, Street street, int timeStaying) {
        this.car = car;
        this.street = street;
        this.timeStaying = timeStaying;
    }

    public Car getCar() {
        return car;
    }
}
