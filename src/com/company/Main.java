package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.company.model.Car;
import com.company.model.Intersection;
import com.company.model.Street;
import com.company.model.WaitingCarAtTheAndOfTheStreet;

public class Main {


  private static class Input {
    public List<Street> getStreets() {
      return streets;
    }

    public Input(int simLasts, List<Car> cars, List<Intersection> intersections, List<Street> streets) {
      this.simLasts = simLasts;
      this.cars = cars;
      this.intersections = intersections;
      this.streets = streets;
    }

    public int getSimLasts() {
      return simLasts;
    }

    public void setSimLasts(int simLasts) {
      this.simLasts = simLasts;
    }

    public List<Car> getCars() {
      return cars;
    }

    public void setCars(List<Car> cars) {
      this.cars = cars;
    }

    public List<Intersection> getIntersections() {
      return intersections;
    }

    public void setIntersections(List<Intersection> intersections) {
      this.intersections = intersections;
    }

    int simLasts;
    List<Car> cars;
    List<Intersection> intersections;
    List<Street> streets;
  }

  public static String algorithm(Input input) {
    List<Intersection> allPossibleIntersections = getAllPossibleIntersections(getDifferentIntersections(input.streets));
    /*на каждую секунду узнаем, стоит ли машина в конце улицы, выбираем приоритетные и пропускаем их*/
    List<WaitingCarAtTheAndOfTheStreet> waitingCarsAtTheEndOfStreets = new ArrayList<>();
    for (int i = 0; i < input.simLasts; i++) {

      for (Car car : input.cars) {
        if (car.isCompleted) {
          continue;
        }
        Street endStreet = car.isAtTheEndOfStreet(i);
        if (endStreet != null && waitingCarsAtTheEndOfStreets.stream().noneMatch(x -> x.car.equals(car))) {
          waitingCarsAtTheEndOfStreets.add(new WaitingCarAtTheAndOfTheStreet(car, endStreet, 0));
        }
      }
      if (waitingCarsAtTheEndOfStreets.isEmpty()
      ) {
        continue;
      }
      waitingCarsAtTheEndOfStreets = sortCarsByPriority(waitingCarsAtTheEndOfStreets);
      List<Street> streets = waitingCarsAtTheEndOfStreets.stream().map(x -> x.street).collect(Collectors.toList());
      List<Integer> availableIntersections = getDifferentIntersections(streets);
      while (availableIntersections.size() > 0 && waitingCarsAtTheEndOfStreets.size() > 0) {
        WaitingCarAtTheAndOfTheStreet carStreet = waitingCarsAtTheEndOfStreets.get(0);
        waitingCarsAtTheEndOfStreets.remove(0);
        int currentIntersection = carStreet.street.endIntersection;
        availableIntersections.remove(currentIntersection);
        carStreet.car.doCalculationsWhenPassingIntersection(currentIntersection, carStreet.timeStaying + 1);
        allPossibleIntersections.stream().filter(x->x.id==currentIntersection).findFirst().ifPresent(
            x->x.doCalculationsWhenPassingIntersection(carStreet.street)
        );
      }
      for (WaitingCarAtTheAndOfTheStreet waitingCar :
          waitingCarsAtTheEndOfStreets) {
        waitingCar.timeStaying += 1;
      }
    }

    List<Intersection> intersections = allPossibleIntersections.stream()
        .filter(intersection -> intersection.greenLightAtStreets.size() != 0)
        .collect(Collectors.toList());
    StringBuilder builder = new StringBuilder();
    builder.append(intersections.size()).append("\n");
    intersections.forEach(intersection ->
    {
      builder.append(intersection.getId()).append("\n");
      builder.append(intersection.greenLightAtStreets.size()).append("\n");
      intersection.greenLightAtStreets.forEach((key, value) ->
          builder.append(key).append(" ").append(value).append("\n"));
    });
    return builder.toString();
  }


  public static List<WaitingCarAtTheAndOfTheStreet> sortCarsByPriority(List<WaitingCarAtTheAndOfTheStreet> waitingCarsAtTheEndOfStreets) {
    return waitingCarsAtTheEndOfStreets.stream().sorted(Comparator.comparing(x -> x.car))
        .collect(Collectors.toList());
  }

  public static Input getData(String fileName) {
    Input input = null;
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      List<Integer> firstLine = Stream.of(br.readLine().split(" "))
          .map(Integer::valueOf)
          .collect(Collectors.toList());
      List<Street> streets = new ArrayList<>();
      List<Intersection> intersections = new ArrayList<>();
      for (int i=0; i < firstLine.get(2); i++)
      {
        ArrayList<String> values = new ArrayList<>(Arrays.asList(br.readLine().split(" ")));
        if (!intersections.stream()
            .map(Intersection::getId)
            .collect(Collectors.toList()).contains(Integer.parseInt(values.get(0))))
        {
          intersections.add(new Intersection(Integer.parseInt(values.get(0))));
        }
        streets.add(new Street(Integer.parseInt(values.get(0)), Integer.parseInt(values.get(1)),
            values.get(2),
            Integer.parseInt(values.get(3)), null));
      }
      List<Car> cars = new ArrayList<>();
      for (int i=0; i < firstLine.get(3); i++)
      {
        ArrayList<String> values = new ArrayList<>(Arrays.asList(br.readLine().split(" ")));
        cars.add(new Car(streets.stream()
            .filter(street -> values.contains(street.getName()))
            .collect(Collectors.toList())));
      }
      input = new Input(firstLine.get(0), cars, intersections, streets);
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

  public static Map<Car, Integer> getPath(Input input) {
    Map<Car, Integer> path = new HashMap<>();
    input.getCars().forEach(car -> path.put(car, car.getStreets().size() - 1 + car.getStreets().stream()
        .map(Street::getLength)
        .reduce(0, Integer::sum)));
    return path;
  }
  //чем меньше интеджер тем выше приоритет
    public static SortedSet<Map.Entry<Car, Integer>> getPriority(Input input) {
        Map<Car, Integer> path = getPath(input);
        SortedSet<Map.Entry<Car,Integer>> sortedEntries = new TreeSet<>((e1, e2) ->
        {
          int res = e1.getValue().compareTo(e2.getValue());
          return res != 0 ? res : 1;
        });
        sortedEntries.addAll(path.entrySet());
        return sortedEntries;
    }

  private static String getShortestCarWays(Input input) {
    Map<Car, Integer> path = getPath(input);
    Set<Integer> intersections = new HashSet<>();

    path.entrySet().stream()
        .filter(carIntegerEntry -> carIntegerEntry.getValue() <= input.getSimLasts())
        .forEach(carIntegerEntry -> {
          carIntegerEntry.getKey().getStreets().forEach(street -> intersections.add(street.endIntersection));
        });
    StringBuilder builder = new StringBuilder();
    builder.append(intersections.size()).append("\n");
    intersections.forEach(intersection ->
        builder.append(intersection).append("\n")
            .append(input.getStreets().stream()
                .filter(street -> street.getEndIntersection() == intersection)
                .count()).append("\n")
            .append(input.getStreets().stream()
                .filter(street -> street.getEndIntersection() == intersection)
                .map(street -> street.getName() + " 1")
                .collect(Collectors.joining("\n"))).append("\n")
    );
    System.out.println(builder.toString());
    return builder.toString();
  }

  private static String getLoadedWays(Input input) {
    Map<String, Integer> streetLoad = new HashMap<>();
    input.getCars().forEach(car -> car.getStreets().forEach(street ->
    {
      int load = Optional.ofNullable(streetLoad.get(street.getName())).orElse(0);
      streetLoad.put(street.getName(), load+1);
    }));

    Map<Integer, Map<String, Integer>> intersectionStreets = new HashMap<>();
    input.getStreets()
        .forEach(street ->
        {
          int intersectionId = street.getEndIntersection();
          Map<String, Integer> interStreets = streetLoad.entrySet().stream()
              .filter(str -> str.getKey().equals(street.getName()))
              .collect(Collectors.toMap(Map.Entry::getKey,
                  Map.Entry::getValue));
          if (Optional.ofNullable(intersectionStreets.get(intersectionId)).orElse(new HashMap<>()).isEmpty())
          {
            intersectionStreets.put(intersectionId, interStreets);
          } else
          {
            intersectionStreets.get(intersectionId).putAll(interStreets);
          }
        });

    return buildUpStringResult(intersectionStreets);
  }

  private static String turnOnByLength(Input input) {

    Map<Integer, Map<String, Integer>> intersectionStreets = new HashMap<>();
    input.getStreets()
        .forEach(street ->
        {
          Map<String, Integer> interStreets = new HashMap<>();
          interStreets.put(street.getName(), street.getLength());
          int intersectionId = street.getEndIntersection();
          if (Optional.ofNullable(intersectionStreets.get(intersectionId)).orElse(new HashMap<>()).isEmpty())
          {
            intersectionStreets.put(intersectionId, interStreets);
          } else
          {
            intersectionStreets.get(intersectionId).putAll(interStreets);
          }
        });

    return buildUpStringResult(intersectionStreets);
  }

  private static String getLoadedShortWays(Input input) {
    Map<String, Integer> streetLoad = new HashMap<>();
    input.getCars().forEach(car -> car.getStreets().forEach(street ->
    {
      int load = Optional.ofNullable(streetLoad.get(street.getName())).orElse(0);
      streetLoad.put(street.getName(), load+1);
    }));

    Map<Integer, Map<String, Integer>> intersectionStreets = new HashMap<>();
    input.getStreets()
        .forEach(street ->
        {
          int intersectionId = street.getEndIntersection();
          Map<String, Integer> interStreets = streetLoad.entrySet().stream()
              .filter(str -> str.getKey().equals(street.getName()))
              .collect(Collectors.toMap(Map.Entry::getKey,
                  Map.Entry::getValue));
          if (Optional.ofNullable(intersectionStreets.get(intersectionId)).orElse(new HashMap<>()).isEmpty())
          {
            intersectionStreets.put(intersectionId, interStreets);
          } else
          {
            intersectionStreets.get(intersectionId).putAll(interStreets);
          }
        });
    intersectionStreets.forEach((id, street) -> {
      AtomicInteger shortestStreet = new AtomicInteger();
      street.forEach((key, value) ->
      {
        int streetLength = input.getStreets().stream().filter(someStreet -> someStreet.getName().equals(key))
            .map(Street::getLength).findFirst().orElse(0);
        if (shortestStreet.get() == 0 || (streetLength < shortestStreet.get() && streetLength != 0))
        {
          shortestStreet.set(streetLength);
        }
      });
      street.entrySet().stream().filter(stringIntegerEntry -> shortestStreet.get() == stringIntegerEntry.getValue()).findFirst().get();
    )
    });

    return buildUpStringResult(intersectionStreets);
  }

  private static String buildUpStringResult(Map<Integer, Map<String, Integer>> intersectionStreets)
  {
    StringBuilder builder = new StringBuilder();
    builder.append(intersectionStreets.size()).append("\n");
    intersectionStreets.forEach((id, streets) ->
    {
      if (!streets.isEmpty())
      {
        builder.append(id).append("\n").append(streets.size()).append("\n").append(
            streets.entrySet().stream().map(street -> street.getKey() + " " + street.getValue())
                .collect(Collectors.joining("\n"))).append("\n");
      }
    });
    System.out.println(builder.toString());
    return builder.toString();
  }

  public static List<Street> incomingStreets(int intersection, Input input) {
    List<Street> incomingStreets = new ArrayList<>();
    input.getStreets().forEach(street -> {
          if (street.startIntersection == intersection) {
            incomingStreets.add(street);
          }
        }
    );
    return incomingStreets;
  }
  public List<Street> outcomingStreets(int intersection, Input input) {
    List<Street> outcomingStreets = new ArrayList<>();
    input.streets.forEach(street -> {
          if (street.endIntersection == intersection) {
            outcomingStreets.add(street);
          }
        }
    );
    return outcomingStreets;
  }

  public boolean multipleEnd(Street firstStreet, Street secondStreet) {
    if (firstStreet.getEndIntersection() == secondStreet.getEndIntersection()) {
      return true;
    }
    return false;
  }

  public static List<Integer> getDifferentIntersections(List<Street> streets){
    List<Integer> intersectionsList = new ArrayList<>();
    for (Street street: streets) {
      int concurrency;
      int finalEndIntersectionNumber = street.endIntersection;
      concurrency = (int) streets.stream().filter(currStreet -> currStreet.getEndIntersection() == finalEndIntersectionNumber).count();
      if ((concurrency > 1) && !intersectionsList.contains((Integer) finalEndIntersectionNumber)) {
        intersectionsList.add((Integer)finalEndIntersectionNumber);
      }
    }
    return intersectionsList;
  }
  public static List<Intersection> getAllPossibleIntersections(List<Integer> intersectionsNumbers){
    List<Intersection> intersections = new ArrayList<>();

    for (int i = 0; i < intersectionsNumbers.size(); i++) {
      intersections.add(new Intersection(intersectionsNumbers.get(i)));
    }

    return intersections;
  }

  public static void main(String[] args) {
    String path = "/home/NIX/holovko/projects/hashCode/src/com/company/";
    List<String> files = Arrays.asList("a", "b", "c", "d", "e", "f");
    //files.forEach(file -> postData(getShortestCarWays(getData(path + file + ".txt")), path + file + ".scw.out"));
    //files.forEach(file -> postData(getLoadedWays(getData(path + file + ".txt")), path + file + ".load.out"));
    files.forEach(file -> postData(turnOnByLength(getData(path + file + ".txt")), path + file + ".length.out"));
  }
}
