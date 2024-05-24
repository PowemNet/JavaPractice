package com.powem.inv.algos;
//Day 8: Path Finding in a City Road Network
//Objective:
//Develop a system that utilizes graph algorithms to find the shortest path between points
// in a city's road network, considering conditions such as road closures and traffic updates.
//
//Scenario:
//You are tasked with creating a navigation system for a city that includes dynamic updates
// such as road closures and traffic jams. The system should provide users with the shortest
// path between two points in the city, adjusting routes based on real-time data.
//
//Challenge:
//Implement the CityNavigationSystem class using graph structures to represent the city's
// road network. The system should be able to add roads, update road statuses (open or closed),
// and compute the shortest path between two points using Dijkstra's algorithm.
//
//Helper Method Requirements:
//
//addRoad(String startPoint, String endPoint, int distance): Adds a bidirectional road between
//two points with a specified distance.
//
//updateRoadStatus(String startPoint, String endPoint, boolean isOpen): Updates the status of
//a road (open or closed).
//
//List<String> findShortestPath(String startPoint, String endPoint): Finds the shortest path
//between two points considering only open roads.


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class CityNavigationSystem {
  private static class Road {
    String endPoint;
    int distance;
    boolean isOpen;

    Road(String endPoint, int distance, boolean isOpen) {
      if(distance <= 0 ) {
        throw new IllegalArgumentException("invalid distance");
      }
      this.endPoint = endPoint;
      this.distance = distance;
      this.isOpen = isOpen;
    }
  }

  private Map<String, List<Road>> cityMap;

  public CityNavigationSystem() {
    cityMap = new HashMap<>();
  }

  public void addRoad(String startPoint, String endPoint, int distance) {
    cityMap.computeIfAbsent(startPoint, k -> new ArrayList<>()).add(new Road(endPoint, distance, true));
    cityMap.computeIfAbsent(endPoint, k -> new ArrayList<>()).add(new Road(startPoint, distance, true));
  }

  public void updateRoadStatus(String startPoint, String endPoint, boolean isOpen) {
    if (cityMap.containsKey(startPoint)) {
      cityMap.get(startPoint).stream()
          .filter(road -> road.endPoint.equals(endPoint))
          .forEach(road -> road.isOpen = isOpen);
    }
    if (cityMap.containsKey(endPoint)) {
      cityMap.get(endPoint).stream()
          .filter(road -> road.endPoint.equals(startPoint))
          .forEach(road -> road.isOpen = isOpen);
    }
  }

  public List<String> findShortestPath(String startPoint, String endPoint) {
    if (!cityMap.containsKey(startPoint) || !cityMap.containsKey(endPoint)) {
      throw new IllegalArgumentException("Start or end point does not exist in the map");
    }

    Map<String, Integer> distances = new HashMap<>();
    Map<String, String> previous = new HashMap<>();
    Set<String> visited = new HashSet<>();
    PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

    distances.put(startPoint, 0);
    queue.add(startPoint);

    while (!queue.isEmpty()) {
      String current = queue.poll();
      if (current.equals(endPoint)) {
        break;
      }
      visited.add(current);

      for (Road road : cityMap.getOrDefault(current, Collections.emptyList())) {
        if (!road.isOpen || visited.contains(road.endPoint)) {
          continue;
        }
        int newDist = distances.getOrDefault(current, Integer.MAX_VALUE) + road.distance;
        if (newDist < distances.getOrDefault(road.endPoint, Integer.MAX_VALUE)) {
          distances.put(road.endPoint, newDist);
          previous.put(road.endPoint, current);
          queue.add(road.endPoint);
        }
      }
    }

    if (!previous.containsKey(endPoint)) {
      throw new IllegalArgumentException("No valid path found");
    }

    List<String> path = new ArrayList<>();
    for (String at = endPoint; at != null; at = previous.get(at)) {
      path.add(at);
    }
    Collections.reverse(path);
    return path;
  }
}


//import java.util.Arrays;
//import java.util.List;
//
//public class Main {
//  public static void main(String[] args) {
//    CityNavigationSystem navigationSystem = new CityNavigationSystem();
//
//    //TEST
//    navigationSystem.addRoad("A", "B", 5);
//    navigationSystem.addRoad("B", "C", 10);
//    navigationSystem.addRoad("A", "C", 15);
//
//    navigationSystem.updateRoadStatus("A", "C", false);
//    List<String> path = navigationSystem.findShortestPath("A", "C");
//    assert path.equals(Arrays.asList("A", "B", "C"));
//    //TEST_END
//
//    //TEST
//    try {
//      navigationSystem.updateRoadStatus("B", "C", false);
//      navigationSystem.findShortestPath("A", "C");
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    try {
//      navigationSystem.findShortestPath("X", "Y");
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    try {
//      navigationSystem.addRoad("C", "D", -1);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    navigationSystem.updateRoadStatus("B", "C", true);
//    List<String> path2 = navigationSystem.findShortestPath("A", "C");
//    assert path2.equals(Arrays.asList("A", "B", "C"));
//    //TEST_END
//  }
//}