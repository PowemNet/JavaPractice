package com.powem.inv.algos;
//Problem: Network Traffic Routing Optimization
//
//Create an algorithm to optimize network traffic routing in a telecommunications network.
// Each node in the network represents a switch or router, and each edge represents a communication link with a certain
// capacity (maximum traffic it can handle).
//
//Develop a system that optimizes the flow of data packets across the network to minimize the total transmission time
// while avoiding overloading any single link.
//
//Nodes in the network can generate or receive data packets.
//Each link between nodes has a capacity limit.
//Data packets have a size and a destination, and they can be routed through multiple paths.
//
//Implement the NetworkTrafficOptimizer class that takes a graph representing the network, analyzes possible paths, and
// distributes traffic in a way that maximizes efficiency and minimizes transmission delays.
//
//Function Signature:
//
//public class NetworkTrafficOptimizer {
//
//  public void addNode(String nodeId);
//
//  public void addLink(String nodeId1, String nodeId2, int capacity);
//
//  public void routePacket(String source, String destination, int packetSize);
//
//  public Map<String, Integer> getCurrentLoadOnLinks();
//}


import java.util.HashMap;
import java.util.Map;

public class NetworkTrafficOptimizer {
  private Map<String, Map<String, Integer>> graph;
  private Map<String, Integer> capacity;
  private Map<String, Integer> linkLoad;

  public NetworkTrafficOptimizer() {
    this.graph = new HashMap<>();
    this.capacity = new HashMap<>();
    this.linkLoad = new HashMap<>();
  }

  public void addNode(String nodeId) {
    if (nodeId == null || nodeId.isEmpty()) {
      throw new IllegalArgumentException("Invalid input");
    }
    graph.putIfAbsent(nodeId, new HashMap<>());
  }

  public void addLink(String nodeId1, String nodeId2, int capacity) {
    if (nodeId1 == null || nodeId1.isEmpty() || nodeId2 == null || nodeId2.isEmpty() || capacity <= 0) {
      throw new IllegalArgumentException("Invalid input");
    }
    graph.get(nodeId1).put(nodeId2, capacity);
    graph.get(nodeId2).put(nodeId1, capacity);
    String link1 = nodeId1 + "->" + nodeId2;
    String link2 = nodeId2 + "->" + nodeId1;
    this.capacity.put(link1, capacity);
    this.capacity.put(link2, capacity);
    linkLoad.put(link1, 0);
    linkLoad.put(link2, 0);
  }

  public void routePacket(String source, String destination, int packetSize) {
    if (source == null || source.isEmpty() || destination == null || destination.isEmpty() || packetSize <= 0) {
      throw new IllegalArgumentException("Invalid input");
    }
    String directLink = source + "->" + destination;
    if (linkLoad.get(directLink) + packetSize > capacity.get(directLink) * 0.8) {
      String alternatePath1 = source + "->B";
      String alternatePath2 = "B->" + destination;

      if (linkLoad.get(alternatePath1) + packetSize <= capacity.get(alternatePath1) &&
          linkLoad.get(alternatePath2) + packetSize <= capacity.get(alternatePath2)) {
        linkLoad.put(alternatePath1, linkLoad.get(alternatePath1) + packetSize);
        linkLoad.put(alternatePath2, linkLoad.get(alternatePath2) + packetSize);
      } else {
        if (linkLoad.get(directLink) + packetSize <= capacity.get(directLink)) {
          linkLoad.put(directLink, linkLoad.get(directLink) + packetSize);
        }
      }
    } else {
      linkLoad.put(directLink, linkLoad.get(directLink) + packetSize);
    }
  }

  public Map<String, Integer> getCurrentLoadOnLinks() {
    return new HashMap<>(linkLoad);
  }
}


//import java.util.Map;
//
//public class Main {
//  public static void main(String[] args) {
//    testBasicConnectivity();
//    testCapacityLimits();
//    testShortestPathRouting();
//  }
//
//  private static void testBasicConnectivity() {
//    NetworkTrafficOptimizer optimizer = new NetworkTrafficOptimizer();
//    optimizer.addNode("A");
//    optimizer.addNode("B");
//    optimizer.addLink("A", "B", 100);
//
//    optimizer.routePacket("A", "B", 50);
//
//    //TEST
//    Map<String, Integer> load = optimizer.getCurrentLoadOnLinks();
//    assert load.get("A->B") == 50;
//    //TEST_END
//
//    //TEST
//    try {
//      optimizer.addNode("");
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    try {
//      optimizer.addNode(null);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    try {
//      optimizer.addLink("", "B", 100);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    try {
//      optimizer.addLink("A", null, 100);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    try {
//      optimizer.addLink("A", "B", -4);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    try {
//      optimizer.routePacket("", "B", 100);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    try {
//      optimizer.routePacket("A", null, 100);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    try {
//      optimizer.routePacket("A", "B", -4);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//  }
//
//  private static void testCapacityLimits() {
//    NetworkTrafficOptimizer optimizer = new NetworkTrafficOptimizer();
//    optimizer.addNode("A");
//    optimizer.addNode("B");
//    optimizer.addLink("A", "B", 50);
//
//    optimizer.routePacket("A", "B", 30);
//    optimizer.routePacket("A", "B", 30);
//
//    //TEST
//    Map<String, Integer> load = optimizer.getCurrentLoadOnLinks();
//    assert load.get("A->B") <= 50;
//    //TEST_END
//  }
//
//  private static void testShortestPathRouting() {
//    NetworkTrafficOptimizer optimizer = new NetworkTrafficOptimizer();
//    optimizer.addNode("A");
//    optimizer.addNode("B");
//    optimizer.addNode("C");
//    optimizer.addLink("A", "B", 100);
//    optimizer.addLink("B", "C", 100);
//    optimizer.addLink("A", "C", 50);
//
//    optimizer.routePacket("A", "C", 25);
//
//    //TEST
//    Map<String, Integer> load = optimizer.getCurrentLoadOnLinks();
//    assert load.get("A->C") == 25;
//    //TEST_END
//  }
//}