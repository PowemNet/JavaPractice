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



//Explanation:
//This algorithm uses an Exponential Moving Average (EMA) to detect trends in historical
// price data and adjust the daily trade sizes accordingly. It also deducts transaction costs
// from the trade sizes, which affects the net amount sold each day. This approach allows for
// dynamic adjustments based on both market trend predictions and cost considerations, making it
// significantly more complex and realistic.


//import com.powem.inv.algos.GeneticSequenceAlignment;
//
//public class Main {
//    public static void main(String[] args) {
//        GeneticSequenceAlignment alignment = new GeneticSequenceAlignment();
//
//        // Test matching sequences
//        assert alignment.alignSequences("ACTG", "ACTG") == 8 : "Test Failed: Perfect match sequences did not return correct score.";
//
//        // Test sequences with mismatches (corrected expected score)
//        assert alignment.alignSequences("ACTG", "ACCG") == 5 : "Test Failed: Sequences with one mismatch did not return correct score.";
//
//        // Test sequences with one gap
//        assert alignment.alignSequences("ACTG", "ACT") == 4 : "Test Failed: Sequences with one gap did not return correct score.";
//
//        // Test sequences with multiple gaps
//        assert alignment.alignSequences("ACTG", "A") == -4 : "Test Failed: Sequences with multiple gaps did not return correct score.";
//
//        // Test completely different sequences
//        assert alignment.alignSequences("AAAA", "TTTT") == -4 : "Test Failed: Completely different sequences did not return correct score.";
//
//    }
//}






