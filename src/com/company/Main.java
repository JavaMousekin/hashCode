package com.company;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static class Input {
        Map<Integer, Integer> teamsCounter;
        List<List<String>> pizzaMenu;

        public Input(Map<Integer, Integer> teamsCounter, List<List<String>> pizzaMenu) {
            this.teamsCounter = teamsCounter;
            this.pizzaMenu = pizzaMenu;
        }
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
            input = new Input(new HashMap<Integer, Integer>() {{
                put(2, firstLine.get(1));
                put(3, firstLine.get(2));
                put(4, firstLine.get(3));
            }}, pizzas);
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
        AtomicInteger counter = new AtomicInteger();
        for (int a : new int[]{2, 3, 4}) {
            if (a > input.pizzaMenu.size()) {
                continue;
            }
            if (input.teamsCounter.get(a) > 0 && a == input.pizzaMenu.size()) {
                builder.append(counter.incrementAndGet()).append("\n").append(a).append(" ");
                for (int i = 0; i < input.pizzaMenu.size(); i++) {
                    builder.append(i).append(" ");
                }
                return builder.toString();
            }
        }

        StringBuilder result = new StringBuilder();
        if (4 < input.pizzaMenu.size()) {
            Map<String, Boolean> uniqueIngredients = new HashMap<>();
            input.pizzaMenu.forEach(pizza -> pizza.forEach(ingredient -> uniqueIngredients.put(ingredient, false)));

            List<String> pizzaList = new ArrayList<>();
            for (int allowedMatch = 0; allowedMatch < Collections.max(input.pizzaMenu.stream()
                    .map(List::size)
                    .collect(Collectors.toList())); allowedMatch++) {
                for (int i = 0; i < input.pizzaMenu.size(); i++) {
                    List<String> pizza = input.pizzaMenu.get(i);
                    if (pizza.stream().filter(uniqueIngredients::get).count() == allowedMatch
                            && pizza.size() > allowedMatch) {
                        pizza.forEach(ingredient -> uniqueIngredients.put(ingredient, true));
                        pizzaList.add(String.valueOf(i));
                    }
                    if (input.teamsCounter.get(4) > 0 && pizzaList.size() == 4) {
                        result.append("4").append(" ").append(String.join(" ", pizzaList)).append("\n");
                        input.teamsCounter.put(4, input.teamsCounter.get(4) - 1);
                        pizzaList = new ArrayList<>();
                    }
                }
            }

        }

        return result.toString().split("\n").length + "\n" + result.toString();
    }

    public static void main(String[] args) {
        String path = "C:\\Users\\agris\\Downloads\\Telegram Desktop\\hashCodeTrain\\hashCodeTrain\\src\\com\\company\\";
        List<String> files = Arrays.asList("b_little_bit_of_everything", "c_many_ingredients", "d_many_pizzas", "e_many_teams");
        files.forEach(file -> postData(process(getData(path + file + ".in")), path + file + ".out"));
        postData(process(getData(path + "a_example")), path + "a_example" + ".out");
    }
}
