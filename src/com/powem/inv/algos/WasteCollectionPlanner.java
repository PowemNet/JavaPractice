//Problem: Optimal Waste Collection Route Planning
//Problem Statement:
//Design an algorithm for optimizing waste collection routes in a city. The city is represented as a grid where each
// cell represents a city block. Some blocks generate waste and need to be visited by a waste collection truck.
// The truck starts from a designated depot and must return to the depot after collecting waste from all required
// blocks. The goal is to minimize the total travel distance of the truck.
//
//Develop a method that processes the city grid, the location of the depot, and the locations of blocks that generate
// waste, and generates an optimized route that minimizes the total travel distance.
//
//The city is represented as a grid with m rows and n columns.
//The depot and waste-generating blocks are specified by their coordinates.
//The truck can move in four directions: up, down, left, and right.
//The algorithm should minimize the total travel distance of the truck.

//Implement the WasteCollectionPlanner class that takes the grid dimensions, the depot location, and the locations
// of waste-generating blocks and generates an optimized collection route.
//
//Function Signature Suggestion:
//public class WasteCollectionPlanner {
//    public List<int[]> planRoute(int m, int n, int[] depot, List<int[]> wasteBlocks);
//}

//TESTS
//import com.powem.inv.algos.WasteCollectionPlanner;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//public class Main {
//  public static void main(String[] args) {
//    {
//      int m = 5, n = 5;
//      int[] depot = {0, 0};
//      List<int[]> wasteBlocks = Arrays.asList(
//              new int[]{1, 2},
//              new int[]{3, 3},
//              new int[]{4, 1}
//      );
//      WasteCollectionPlanner planner = new WasteCollectionPlanner(m, n, depot, wasteBlocks);
//      List<int[]> route = planner.planRoute();
//
//      // TEST
//      assert route.size() == 5;
//      // TEST END
//
//      // TEST
//      assert Arrays.equals(route.get(0), depot);
//      // TEST END
//
//      // TEST
//      assert Arrays.equals(route.get(route.size() - 1), depot);
//      // TEST END
//
//      // TEST
//      Set<int[]> visitedBlocks = new HashSet<>(wasteBlocks);
//      for (int i = 1; i < route.size() - 1; i++) {
//        assert visitedBlocks.contains(route.get(i));
//      }
//      // TEST END
//    }
//
//    {
//      int m = 10, n = 10;
//      int[] depot = {0, 0};
//      List<int[]> wasteBlocks = Arrays.asList(
//              new int[]{1, 2},
//              new int[]{3, 3},
//              new int[]{4, 1},
//              new int[]{6, 6},
//              new int[]{8, 8},
//              new int[]{9, 9}
//      );
//      WasteCollectionPlanner planner = new WasteCollectionPlanner(m, n, depot, wasteBlocks);
//      List<int[]> route = planner.planRoute();
//
//      // TEST
//      assert route.size() == 8;
//      // TEST END
//
//      // TEST
//      assert Arrays.equals(route.get(0), depot);
//      // TEST END
//
//      // TEST
//      assert Arrays.equals(route.get(route.size() - 1), depot);
//      // TEST END
//
//      // TEST
//      Set<int[]> visitedBlocks = new HashSet<>(wasteBlocks);
//      for (int i = 1; i < route.size() - 1; i++) {
//        assert visitedBlocks.contains(route.get(i));
//      }
//      // TEST END
//    }
//
//    {
//      try {
//        new WasteCollectionPlanner(-5, 5, new int[]{0, 0}, Arrays.asList(new int[]{1, 2}));
//
//        //TEST
//        assert false;
//        // TEST END
//
//      } catch (IllegalArgumentException e) {
//        System.out.println("Test Passed: Negative grid dimensions handled correctly.");
//      }
//    }
//
//    {
//      try {
//        new WasteCollectionPlanner(5, 5, null, Arrays.asList(new int[]{1, 2}));
//
//        //TEST
//        assert false;
//        //TEST END
//      } catch (IllegalArgumentException e) {
//        System.out.println("Test Passed: Null depot location handled correctly.");
//      }
//    }
//
//    {
//      try {
//        new WasteCollectionPlanner(5, 5, new int[]{0, 0}, new ArrayList<>());
//        //TEST
//        assert false;
//        //TEST END
//      } catch (IllegalArgumentException e) {
//        System.out.println("Test Passed: Empty waste blocks list handled correctly.");
//      }
//    }
//
//    {
//      int m = 5, n = 5;
//      int[] depot = {0, 0};
//      List<int[]> wasteBlocks = Arrays.asList(new int[]{1, 2});
//      WasteCollectionPlanner planner = new WasteCollectionPlanner(m, n, depot, wasteBlocks);
//      List<int[]> route = planner.planRoute();
//
//      // TEST
//      assert route.size() == 3;
//      // TEST END
//
//      // TEST
//      assert Arrays.equals(route.get(0), depot);
//      // TEST END
//
//      // TEST
//      assert Arrays.equals(route.get(1), new int[]{1, 2});
//      // TEST END
//
//      // TEST
//      assert Arrays.equals(route.get(2), depot);
//      // TEST END
//    }
//  }
//}


package com.powem.inv.algos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class WasteCollectionPlanner {
    private int m, n;
    private int[] depot;
    private List<int[]> wasteBlocks;

    public WasteCollectionPlanner(int m, int n, int[] depot, List<int[]> wasteBlocks) {
        if (m <= 0 || n <= 0) {
            throw new IllegalArgumentException("Grid dimensions must be greater than 0");
        }
        if (depot == null || depot.length != 2) {
            throw new IllegalArgumentException("Depot location must be a valid coordinate");
        }
        if (wasteBlocks == null || wasteBlocks.isEmpty()) {
            throw new IllegalArgumentException("Waste blocks list cannot be null or empty");
        }
        this.m = m;
        this.n = n;
        this.depot = depot;
        this.wasteBlocks = wasteBlocks;
    }

    public List<int[]> planRoute() {
        List<int[]> allNodes = new ArrayList<>(wasteBlocks);
        allNodes.add(depot);

        Map<int[], Map<int[], Integer>> shortestPaths = new HashMap<>();
        for (int[] node : allNodes) {
            shortestPaths.put(node, dijkstra(node, allNodes));
        }

        List<int[]> route = new ArrayList<>();
        route.add(depot);

        Set<int[]> visited = new HashSet<>();
        visited.add(depot);
        int[] current = depot;

        while (visited.size() <= wasteBlocks.size()) {
            int[] nextNode = null;
            int minDistance = Integer.MAX_VALUE;

            for (int[] node : wasteBlocks) {
                if (!visited.contains(node)) {
                    int distance = shortestPaths.get(current).get(node);
                    if (distance < minDistance) {
                        minDistance = distance;
                        nextNode = node;
                    }
                }
            }

            route.add(nextNode);
            visited.add(nextNode);
            current = nextNode;
        }

        route.add(depot);

        return route;
    }

    private Map<int[], Integer> dijkstra(int[] start, List<int[]> allNodes) {
        Map<int[], Integer> distances = new HashMap<>();
        for (int[] node : allNodes) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(start, 0);

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        pq.add(start);

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int currentDistance = distances.get(current);

            for (int[] neighbor : getNeighbors(current, allNodes)) {
                int distance = currentDistance + manhattanDistance(current, neighbor);
                if (distance < distances.get(neighbor)) {
                    distances.put(neighbor, distance);
                    pq.add(neighbor);
                }
            }
        }

        return distances;
    }

    private List<int[]> getNeighbors(int[] node, List<int[]> allNodes) {
        List<int[]> neighbors = new ArrayList<>();
        for (int[] potentialNeighbor : allNodes) {
            if (!Arrays.equals(node, potentialNeighbor)) {
                neighbors.add(potentialNeighbor);
            }
        }
        return neighbors;
    }

    private int manhattanDistance(int[] a, int[] b) {
        return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]);
    }
}
