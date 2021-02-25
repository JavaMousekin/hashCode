package com.company;


import com.company.model.Car;
import com.company.model.Intersection;
import com.company.model.Street;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static class Input {
        public Input(int simLasts, List<Car> cars, List<Intersection> intersections, List<Street> streets) {
            this.simLasts = simLasts;
            this.cars = cars;
            this.intersections = intersections;
            this.streets = streets;
        }

        int simLasts;
        List<Car> cars;
        List<Intersection> intersections;
        List<Street> streets;
    }

    public static Input getData(String fileName) {
        Input input = null;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            List<Integer> firstLine = Stream.of(br.readLine().split(" "))
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());
            List<Street> streets = new ArrayList<>();
            for (int i=0; i < firstLine.get(2); i++)
            {
                ArrayList<String> values = new ArrayList<>(Arrays.asList(br.readLine().split(" ")));
                streets.add(new Street(
                        new Intersection(Integer.parseInt(values.get(0)), 0),
                        new Intersection(Integer.parseInt(values.get(1)), 0),
                        values.get(2),
                        Integer.parseInt(values.get(3))));
            }
            List<Car> cars = new ArrayList<>();
            for (int i=0; i < firstLine.get(3); i++)
            {
                ArrayList<String> values = new ArrayList<>(Arrays.asList(br.readLine().split(" ")));
                cars.add(new Car(streets.stream().filter(street -> values.contains(street.get)).collect(Collectors.toList())));
            }
            List<List<String>> otherLines = new ArrayList<>();
            for (int i = 0; i < firstLine.get(0); i++) {
                ArrayList<String> values = new ArrayList<>(Arrays.asList(br.readLine().split(" ")));
                values.remove(0);
                otherLines.add(values);
            }
            //input = new Input();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }

    public static void postData(String output, String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String process(Input input) {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

    public List<Street> incomingStreets(int intersection, Input input) {
        List<Street> incomingStreets = new ArrayList<>();
        input.streets.stream().forEach(street -> {
                    if (street.startIntersection == intersection) {
                        incomingStreets.add(street);
                    }
                }
        );
        return incomingStreets;
    }
    public List<Street> outcomingStreets(int intersection, Input input) {
        List<Street> outcomingStreets = new ArrayList<>();
        input.streets.stream().forEach(street -> {
                    if (street.endIntersection == intersection) {
                        outcomingStreets.add(street);
                    }
                }
        );
        return outcomingStreets;
    }
    
    public static void main(String[] args) {
        String path = "\\";
        List<String> files = Arrays.asList("");
        files.forEach(file -> postData(process(getData(path + file + ".in")), path + file + ".out"));
        postData(process(getData(path + "a_example")), path + "a_example" + ".out");
    }
}
