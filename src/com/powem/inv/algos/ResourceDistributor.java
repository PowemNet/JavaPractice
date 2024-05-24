package com.powem.inv.algos;
//Problem: Optimal Resource Distribution in a Network
//Problem Statement:
//Designing an algorithm to optimally distribute resources across a network of nodes,
//ensuring that each node maintains a minimum required resource level. The network's connectivity and
//each node's initial resources vary.
//
//The system should have a class ResourceDistributor balance resource distribution across a connected graph in a way that minimizes
//the total movement of resources while ensuring that no node falls below a specified minimum resource level.
//
//Details:
//
//Nodes represent different stations or entities that require resources to function.
//
//Edges represent possible paths for resource transfer.
//
//Each node has a current resource level and a minimum required resource level.
//
//Resources can be moved between directly connected nodes.
//
//required function signature:
//public class ResourceDistributor {
//
//  public void addNode(int nodeId, int currentResources, int minRequiredResources);
//
//  public void addEdge(int nodeId1, int nodeId2);
//
//  public void distributeResources();
//
//  public Map<Integer, Integer> getNodeResourceLevels();
//}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class ResourceDistributor {
  private Map<Integer, Node> nodes;
  private Map<Integer, List<Integer>> graph;

  public ResourceDistributor() {
    nodes = new HashMap<>();
    graph = new HashMap<>();
  }

  public void addNode(int nodeId, int currentResources, int minRequiredResources) {
    if (nodeId < 1 || currentResources < 1 || minRequiredResources < 1) {
      throw new IllegalArgumentException("invalid input");
    }
    nodes.put(nodeId, new Node(currentResources, minRequiredResources));
    graph.put(nodeId, new ArrayList<>());
  }

  public void addEdge(int nodeId1, int nodeId2) {
    if (nodeId1 < 1 || nodeId2 < 1) {
      throw new IllegalArgumentException("invalid input");
    }
    graph.get(nodeId1).add(nodeId2);
    graph.get(nodeId2).add(nodeId1);
  }

  public void distributeResources() {
    AtomicBoolean needsAdjustment = new AtomicBoolean(false);
    do {
      needsAdjustment.set(false);
      Map<Integer, Integer> allocations = new HashMap<>();

      for (Map.Entry<Integer, List<Integer>> entry : graph.entrySet()) {
        int nodeId = entry.getKey();
        Node currentNode = nodes.get(nodeId);
        int excess = currentNode.currentResources - currentNode.minRequiredResources;

        if (excess > 0) {
          List<Integer> neighbors = entry.getValue();
          int totalNeeded = 0;

          for (int neighborId : neighbors) {
            Node neighbor = nodes.get(neighborId);
            int needed = Math.max(0, neighbor.minRequiredResources - neighbor.currentResources);
            totalNeeded += needed;
          }

          for (int neighborId : neighbors) {
            Node neighbor = nodes.get(neighborId);
            if (totalNeeded > 0) {
              int needed = Math.max(0, neighbor.minRequiredResources - neighbor.currentResources);
              int give = (int) ((double) needed / totalNeeded * excess);

              allocations.put(neighborId, allocations.getOrDefault(neighborId, 0) + give);
              currentNode.currentResources -= give;
            }
          }
        }
      }

      allocations.forEach((id, allocation) -> {
        Node node = nodes.get(id);
        node.currentResources += allocation;
        if (node.currentResources >= node.minRequiredResources) {
          needsAdjustment.set(true);
        }
      });

    } while (needsAdjustment.get());
  }


  public Map<Integer, Integer> getNodeResourceLevels() {
    Map<Integer, Integer> resourceLevels = new HashMap<>();
    for (Map.Entry<Integer, Node> entry : nodes.entrySet()) {
      resourceLevels.put(entry.getKey(), entry.getValue().currentResources);
    }
    return resourceLevels;
  }

  static class Node {
    int currentResources;
    int minRequiredResources;

    Node(int currentResources, int minRequiredResources) {
      this.currentResources = currentResources;
      this.minRequiredResources = minRequiredResources;
    }
  }
}


//public class Main {
//  public static void main(String[] args) {
//    ResourceDistributor distributor = new ResourceDistributor();
//
//    //TEST
//    distributor.addNode(1, 100, 80);
//    distributor.addNode(2, 30, 50);
//    distributor.addEdge(1, 2);
//    distributor.distributeResources();
//
//    assert distributor.getNodeResourceLevels().get(1) >= 80;
//    //TEST_END
//
//    //TEST
//    try {
//      distributor.addEdge(1, -2);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    try {
//      distributor.addEdge(1, 0);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    assert distributor.getNodeResourceLevels().get(2) >= 50;
//    //TEST_END
//
//    //TEST
//    distributor.addNode(3, 60, 60);
//    distributor.addEdge(2, 3);
//    distributor.distributeResources();
//    assert distributor.getNodeResourceLevels().get(3) == 60;
//    //TEST_END
//
//    //TEST
//    distributor.addNode(4, 40, 30);
//    distributor.addNode(5, 50, 50);
//    distributor.addEdge(4, 5);
//    distributor.distributeResources();
//
//    assert distributor.getNodeResourceLevels().get(4) >= 30;
//    //TEST_END
//
//    //TEST
//    assert distributor.getNodeResourceLevels().get(5) == 50;
//    //TEST_END
//
//    //TEST
//    try {
//      distributor.addNode(6, -10, 20);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    try {
//      distributor.addNode(0, 10, 20);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    try {
//      distributor.addNode(1, 1, 0);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST: Check distribution after adding edges between multiple nodes
//    distributor.addNode(7, 25, 25);
//    distributor.addEdge(5, 7);
//    distributor.distributeResources();
//    assert distributor.getNodeResourceLevels().get(7) == 25;
//    //TEST_END
//  }
//}

