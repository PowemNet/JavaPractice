//Problem: Network Packet Routing Optimization
//Problem Statement:
//Develop a system to optimize packet routing in a large-scale computer network. The system should
//handle real-time updates on network conditions, dynamically adjust routing paths to avoid
//congestion, and ensure the shortest possible delivery times for packets.
//
//Objective:
//Create a system that efficiently manages and optimizes packet routing in a large-scale network,
//ensuring minimal delivery times and avoiding congestion.
//
//The network is represented as a graph where nodes represent routers and edges represent network
//links with weights indicating latency.
//
//Real-time network conditions include updates on latency and congestion levels.
//The system should support adding and removing routers and links, updating real-time network
//conditions, and finding optimal paths for packet delivery.
//Implement advanced algorithms such as Dijkstra’s algorithm for shortest path and A* algorithm for
//efficient routing.
//
//Function Signature:
//public class NetworkRouter {
//    public void addRouter(String routerId);
//    public void addLink(String fromRouter, String toRouter, int latency);
//    public void updateNetworkCondition(String fromRouter, String toRouter, int newLatency);
//    public List<String> findOptimalPath(String fromRouter, String toRouter);
//}

package com.powem.inv.algos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class NetworkRouter {
    private static class Link {
        String from;
        String to;
        int latency;

        Link(String from, String to, int latency) {
            this.from = from;
            this.to = to;
            this.latency = latency;
        }
    }

    private final Map<String, List<Link>> network = new HashMap<>();
    private final Map<String, Integer> distances = new HashMap<>();
    private final Map<String, String> previousNodes = new HashMap<>();

    public void addRouter(String routerId) {
        if (routerId == null || routerId.isEmpty()) {
            throw new IllegalArgumentException("Invalid router ID");
        }
        network.putIfAbsent(routerId, new ArrayList<>());
    }

    public void addLink(String fromRouter, String toRouter, int latency) {
        if (fromRouter == null || fromRouter.isEmpty() ||
            toRouter == null || toRouter.isEmpty() || latency <= 0) {
            throw new IllegalArgumentException("Invalid link data");
        }
        network.get(fromRouter).add(new Link(fromRouter, toRouter, latency));
    }

    public void updateNetworkCondition(String fromRouter, String toRouter, int newLatency) {
        if (fromRouter == null || fromRouter.isEmpty() ||
            toRouter == null || toRouter.isEmpty() || newLatency <= 0) {
            throw new IllegalArgumentException("Invalid network condition data");
        }
        List<Link> links = network.get(fromRouter);
        for (Link link : links) {
            if (link.to.equals(toRouter)) {
                link.latency = newLatency;
                return;
            }
        }
        throw new IllegalArgumentException("Link not found");
    }

    public List<String> findOptimalPath(String fromRouter, String toRouter) {
        if (fromRouter == null || fromRouter.isEmpty() ||
            toRouter == null || toRouter.isEmpty()) {
            throw new IllegalArgumentException("Invalid router IDs");
        }
        return findPath(fromRouter, toRouter);
    }

    private List<String> findPath(String start, String end) {
        distances.clear();
        previousNodes.clear();

        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        Set<String> visited = new HashSet<>();

        distances.put(start, 0);
        pq.add(start);

        while (!pq.isEmpty()) {
            String current = pq.poll();
          if (!visited.add(current)) {
            continue;
          }

          if (current.equals(end)) {
            break;
          }

            for (Link link : network.get(current)) {
              if (visited.contains(link.to)) {
                continue;
              }

                int newDist = distances.get(current) + link.latency;
                if (newDist < distances.getOrDefault(link.to, Integer.MAX_VALUE)) {
                    distances.put(link.to, newDist);
                    previousNodes.put(link.to, current);
                    pq.add(link.to);
                }
            }
        }

        List<String> path = new LinkedList<>();
        for (String at = end; at != null; at = previousNodes.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }
}

//TESTS
//import com.powem.inv.algos.NetworkRouter;
//import java.util.Arrays;
//import java.util.List;
//
//public class Main {
//  public static void main(String[] args) {
//    NetworkRouter router = new NetworkRouter();
//
//    // TEST
//    router.addRouter("RouterA");
//    router.addRouter("RouterB");
//    assert router.findOptimalPath("RouterA", "RouterB") != null;
//    // TEST_END
//
//    // TEST
//    try {
//      router.addRouter("");
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      router.addRouter(null);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    router.addLink("RouterA", "RouterB", 10);
//    assert router.findOptimalPath("RouterA", "RouterB").equals(Arrays.asList("RouterA", "RouterB"));
//    // TEST_END
//
//    // TEST
//    try {
//      router.addLink("RouterA", "RouterC", -5);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    router.updateNetworkCondition("RouterA", "RouterB", 5);
//    assert router.findOptimalPath("RouterA", "RouterB").equals(Arrays.asList("RouterA", "RouterB"));
//    // TEST_END
//
//    // TEST
//    try {
//      router.updateNetworkCondition("RouterA", "RouterC", 10);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    router.addRouter("RouterC");
//    router.addLink("RouterB", "RouterC", 15);
//
//    List<String> path = router.findOptimalPath("RouterA", "RouterC");
//    List<String> expectedPath = Arrays.asList("RouterA", "RouterB", "RouterC");
//    assert path.equals(expectedPath) : "Expected " + expectedPath + " but got " + path;
//    // TEST_END
//
//    // TEST
//    try {
//      router.findOptimalPath("RouterA", "");
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//  }
//}
