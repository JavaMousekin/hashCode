package com.company;

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

    }

    public static Input getData(String fileName) {
        Input input = null;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            List<Integer> firstLine = Stream.of(br.readLine().split(" "))
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());
            List<List<String>> pizzas = new ArrayList<>();
            for (int i = 0; i < firstLine.get(0); i++) {
                ArrayList<String> ingredients = new ArrayList<>(Arrays.asList(br.readLine().split(" ")));
                ingredients.remove(0);
                pizzas.add(ingredients);
            }
            input = new Input();
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

    public static void main(String[] args) {
        String path = "\\";
        List<String> files = Arrays.asList("");
        files.forEach(file -> postData(process(getData(path + file + ".in")), path + file + ".out"));
        postData(process(getData(path + "a_example")), path + "a_example" + ".out");
    }
}
