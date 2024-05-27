package com.powem.inv.algos;
//Influencer Identification in Social Networks
//Problem:
//Develop a Java method to identify key influencers in a social network based on their connectivity and interactions with other users. This problem involves analyzing network graphs to determine which nodes (users) have the most influence over the network through their connections.
//
//Requirements:
//Method Signature:
//public List<String> identifyInfluencers(Map<String, List<String>> network, int topN)
//Functionality:
//identifyInfluencers: This method analyzes the network to identify the top N influencers based on the number of direct connections they have.

import java.util.*;

public class SocialNetworkAnalysis {

    public List<String> identifyInfluencers(Map<String, List<String>> network, int topN) {
        // This map will store the user as key and the count of their connections as value.
        Map<String, Integer> influenceScores = new HashMap<>();

        // Calculate the influence score for each user based on direct connections.
        for (Map.Entry<String, List<String>> entry : network.entrySet()) {
            influenceScores.put(entry.getKey(), entry.getValue().size());
        }

        // Create a priority queue to store users by their influence scores in descending order.
        PriorityQueue<Map.Entry<String, Integer>> maxHeap = new PriorityQueue<>(
            (a, b) -> b.getValue().compareTo(a.getValue())
        );

        maxHeap.addAll(influenceScores.entrySet());

        // Extract the top N influencers from the priority queue.
        List<String> influencers = new ArrayList<>();
        while (topN-- > 0 && !maxHeap.isEmpty()) {
            influencers.add(maxHeap.poll().getKey());
        }

        return influencers;
    }
}

//import com.powem.inv.algos.SocialNetworkAnalysis;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class Main {
//    public static void main(String[] args) {
//        testIdentifyTopInfluencers();
//        testEmptyNetwork();
//        testSingleUserNetwork();
//        testNetworkWithIsolatedUsers();
//    }
//
//
//    private static void testIdentifyTopInfluencers() {
//        SocialNetworkAnalysis analysis = new SocialNetworkAnalysis();
//        Map<String, List<String>> network = new HashMap<>();
//        network.put("Alice", List.of("Bob", "Carol", "Dave"));
//        network.put("Bob", List.of("Alice", "Eve", "Carol"));
//        network.put("Carol", List.of("Alice", "Bob", "Dave", "Eve"));
//        network.put("Dave", List.of("Alice", "Carol"));
//        network.put("Eve", List.of("Bob", "Carol"));
//
//        List<String> influencers = analysis.identifyInfluencers(network, 3);
//
//        //TEST
//        System.out.println("Top 3 Influencers: " + influencers);
//        assert influencers.size() == 3 : "Test Failed: Should identify exactly 3 top influencers.";
//        assert influencers.containsAll(List.of("Carol", "Alice", "Bob")) : "Test Failed: Incorrect influencers identified.";
//        System.out.println("Test Passed: Correctly identified the top influencers.");
//        //TEST_END
//    }
//
//    private static void testEmptyNetwork() {
//        SocialNetworkAnalysis analysis = new SocialNetworkAnalysis();
//        Map<String, List<String>> network = new HashMap<>();
//
//        List<String> influencers = analysis.identifyInfluencers(network, 3);
//
//        //TEST
//        assert influencers.isEmpty() : "Test Failed: Empty network should result in no influencers.";
//        System.out.println("Test Passed: No influencers identified in an empty network.");
//        //TEST_END
//    }
//
//    private static void testSingleUserNetwork() {
//        SocialNetworkAnalysis analysis = new SocialNetworkAnalysis();
//        Map<String, List<String>> network = new HashMap<>();
//        network.put("Alice", List.of());
//
//        List<String> influencers = analysis.identifyInfluencers(network, 1);
//
//        //TEST
//        assert influencers.size() == 1 && influencers.contains("Alice") : "Test Failed: Single user network should identify the user as an influencer.";
//        System.out.println("Test Passed: Correctly identified the single user as an influencer.");
//        //TEST_END
//    }
//
//    private static void testNetworkWithIsolatedUsers() {
//        SocialNetworkAnalysis analysis = new SocialNetworkAnalysis();
//        Map<String, List<String>> network = new HashMap<>();
//        network.put("Alice", List.of("Bob"));
//        network.put("Bob", List.of("Alice"));
//        network.put("Carol", List.of());
//        network.put("Dave", List.of());
//
//        List<String> influencers = analysis.identifyInfluencers(network, 2);
//
//        //TEST
//        assert influencers.size() == 2 && influencers.containsAll(List.of("Alice", "Bob")) : "Test Failed: Isolated users should not be identified as top influencers.";
//        System.out.println("Test Passed: Correctly ignored isolated users when identifying top influencers.");
//        //TEST_END
//    }
//}







