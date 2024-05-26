
//    Problem: Maximum Bandwidth Path in a Network
//    Problem Statement:
//    Develop a class to determine the maximum bandwidth path between two nodes using
//    the Edmonds-Karp algorithm, an implementation of the Ford-Fulkerson method for computing the maximum flow in a
//    flow network.
//
//    Create a class that can find the path between two nodes that maximizes the possible data transfer rate
//    (bandwidth), considering that any path's bandwidth is determined by the smallest bandwidth on that path.
//
//    The network is represented as a directed graph where edges have capacities that represent the bandwidth.
//    The algorithm should handle networks with complex topologies and varying bandwidth capacities.
//    Challenge:
//    Efficiently implement the Edmonds-Karp algorithm, which is used to find the maximum flow in a network, adapting it to determine the maximum bandwidth path specifically.
//
//    Function Signature Suggestion:
//        public class NetworkFlowAnalyzer {
//            public void addEdge(int from, int to, int cap) {
//            }
//        }

package com.powem.inv.algos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class NetworkFlowAnalyzer {
    private int[][] capacity;
    private List<List<Integer>> adjList;
    private int nodeCount;

    public NetworkFlowAnalyzer(int nodeCount) {
        if (nodeCount <= 0) {
            throw new IllegalArgumentException("Number of nodes must be positive");
        }
        this.nodeCount = nodeCount;
        this.capacity = new int[nodeCount][nodeCount];
        this.adjList = new ArrayList<>(nodeCount);
        for (int i = 0; i < nodeCount; i++) {
            this.adjList.add(new ArrayList<>());
        }
    }

    public void addEdge(int from, int to, int cap) {
        if (from < 0 || to < 0 || from >= nodeCount || to >= nodeCount) {
            throw new IllegalArgumentException("Invalid node index");
        }
        if (cap <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        adjList.get(from).add(to);
        adjList.get(to).add(from);
        capacity[from][to] = cap;
    }

    public int maxBandwidthPath(int source, int sink) {
        if (source < 0 || sink < 0 || source >= nodeCount || sink >= nodeCount) {
            throw new IllegalArgumentException("Invalid source or sink node index");
        }
        int totalFlow = 0;
        while (true) {
            int[] parent = new int[capacity.length];
            Arrays.fill(parent, -1);
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(source);
            parent[source] = -2;

            while (!queue.isEmpty() && parent[sink] == -1) {
                int current = queue.poll();

                for (int next : adjList.get(current)) {
                    if (parent[next] == -1 && capacity[current][next] > 0) {
                        parent[next] = current;
                        queue.offer(next);
                        if (next == sink) break;
                    }
                }
            }

            if (parent[sink] == -1) break;

            int flow = Integer.MAX_VALUE;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                flow = Math.min(flow, capacity[u][v]);
            }

            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                capacity[u][v] -= flow;
                capacity[v][u] += flow;
            }

            totalFlow += flow;
        }

        return totalFlow;
    }
}

//TEST
//import com.powem.inv.algos.NetworkFlowAnalyzer;
//
//public class Main {
//    public static void main(String[] args) {
//        NetworkFlowAnalyzer analyzer;
//        int maxBandwidth;
//
//        analyzer = new NetworkFlowAnalyzer(6);
//        analyzer.addEdge(0, 1, 10);
//        analyzer.addEdge(0, 2, 10);
//        analyzer.addEdge(1, 3, 4);
//        analyzer.addEdge(1, 4, 8);
//        analyzer.addEdge(1, 2, 2);
//        analyzer.addEdge(2, 4, 9);
//        analyzer.addEdge(3, 5, 10);
//        analyzer.addEdge(4, 3, 6);
//        analyzer.addEdge(4, 5, 10);
//
//        maxBandwidth = analyzer.maxBandwidthPath(0, 5);
//
//        // TEST
//        assert maxBandwidth == 19;
//        // TEST END
//
//        analyzer = new NetworkFlowAnalyzer(4);
//        analyzer.addEdge(0, 1, 10);
//        analyzer.addEdge(2, 3, 10);
//
//        maxBandwidth = analyzer.maxBandwidthPath(0, 3);
//
//        // TEST
//        assert maxBandwidth == 0;
//        // TEST END
//
//        analyzer = new NetworkFlowAnalyzer(3);
//        analyzer.addEdge(0, 1, 5);
//        analyzer.addEdge(1, 2, 5);
//
//        maxBandwidth = analyzer.maxBandwidthPath(0, 2);
//
//        // TEST
//        assert maxBandwidth == 5;
//        // TEST END
//
//        try {
//            analyzer = new NetworkFlowAnalyzer(3);
//            analyzer.addEdge(0, 3, 5);
//
//            // TEST
//            assert false;
//            // TEST END
//        } catch (IllegalArgumentException e) {
//            // TEST
//            assert true;
//            // TEST END
//        }
//
//        try {
//            analyzer = new NetworkFlowAnalyzer(3);
//            analyzer.addEdge(0, 1, -5);
//
//            // TEST
//            assert false;
//            // TEST END
//        } catch (IllegalArgumentException e) {
//            // TEST
//            assert true;
//            // TEST END
//        }
//
//        try {
//            analyzer = new NetworkFlowAnalyzer(3);
//            analyzer.maxBandwidthPath(0, 3);
//
//            // TEST
//            assert false;
//            // TEST END
//        } catch (IllegalArgumentException e) {
//            // TEST
//            assert true;
//            // TEST END
//        }
//    }
//}


