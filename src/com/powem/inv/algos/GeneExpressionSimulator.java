package com.powem.inv.algos;
//Dynamic Gene Expression Pathway Simulation
//**Problem**
//Design a Java class to simulate the dynamic behavior of gene expression pathways in a biological system. In this simulation,
// each gene or protein acts as a node, and the interactions (like activation or inhibition) between them are edges with varying
// intensities based on external conditions like temperature or chemical presence.
//
//**Requirements**
//Class Name: GeneExpressionSimulator
//
//**Method Signatures**
//
//public void addGene(String geneId)
//
//public void connectGenes(String geneId1, String geneId2, double baseInteractionStrength)
//
//public void updateConditions(String geneId1, String geneId2, double currentConditionFactor)
//
//public List<String> findOptimalSignalPath(String sourceGene, String targetGene)
//
//**Functionality**
//
//addGene: Registers a new gene in the system.
//
//connectGenes: Establishes a biochemical interaction between two genes with a specified base interaction strength.
//
//updateConditions: Modifies the interaction strength based on external biological conditions.
//
//findOptimalSignalPath: Determines the most effective pathway for a signal to travel from the source gene to the target gene,
//considering the current interaction strengths.


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

public class GeneExpressionSimulator {
    private Map<String, Map<String, Double>> network;
    private Map<Pair<String, String>, Double> baseInteractions;

    public GeneExpressionSimulator() {
        this.network = new HashMap<>();
        this.baseInteractions = new HashMap<>();
    }

    public Map<String, Map<String, Double>> getNetwork() {
        return this.network;
    }

    public void addGene(String geneId) {
        if (geneId == null || geneId.isEmpty()) {
            throw new IllegalArgumentException("Invalid input");
        }
        network.putIfAbsent(geneId, new HashMap<>());
    }

    public void connectGenes(String geneId1, String geneId2, double baseInteractionStrength) {
        if (geneId1 == null || geneId1.isEmpty() || geneId2 == null || geneId2.isEmpty() || baseInteractionStrength < 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        network.get(geneId1).put(geneId2, baseInteractionStrength);
        network.get(geneId2).put(geneId1, baseInteractionStrength);
        baseInteractions.put(new Pair<>(geneId1, geneId2), baseInteractionStrength);
        baseInteractions.put(new Pair<>(geneId2, geneId1), baseInteractionStrength);
    }

    public void updateConditions(String geneId1, String geneId2, double currentConditionFactor) {
        if (geneId1 == null || geneId1.isEmpty() || geneId2 == null || geneId2.isEmpty() || currentConditionFactor < 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        if (!network.containsKey(geneId1) || !network.containsKey(geneId2)) {
            throw new IllegalArgumentException("One or both genes do not exist in the network.");
        }

        Pair<String, String> pair = new Pair<>(geneId1, geneId2);
        if (!baseInteractions.containsKey(pair)) {
            throw new IllegalArgumentException("No existing interaction between these genes.");
        }

        double baseStrength = baseInteractions.get(pair);
        double adjustedStrength = baseStrength * currentConditionFactor;
        network.get(geneId1).put(geneId2, adjustedStrength);
        network.get(geneId2).put(geneId1, adjustedStrength);
    }


    public List<String> findOptimalSignalPath(String sourceGene, String targetGene) {
        if (sourceGene == null || sourceGene.isEmpty() || targetGene == null || targetGene.isEmpty()) {
            throw new IllegalArgumentException("Invalid input");
        }
        Map<String, Double> dist = new HashMap<>();
        PriorityQueue<Pair<String, Double>> pq = new PriorityQueue<>(Comparator.comparingDouble(Pair::getSecond));
        Map<String, String> prev = new HashMap<>();
        Set<String> visited = new HashSet<>();

        for (String gene : network.keySet()) {
            dist.put(gene, Double.MAX_VALUE);
        }
        dist.put(sourceGene, 0.0);
        pq.add(new Pair<>(sourceGene, 0.0));

        while (!pq.isEmpty()) {
            Pair<String, Double> pair = pq.poll();
            String current = pair.getFirst();
            if (!visited.add(current)) {
                continue;
            }
            if (current.equals(targetGene)) {
                break;
            }

            for (Map.Entry<String, Double> entry : network.get(current).entrySet()) {
                String neighbor = entry.getKey();
                double weight = entry.getValue();
                double newDist = dist.get(current) + weight;
                if (newDist < dist.get(neighbor)) {
                    dist.put(neighbor, newDist);
                    prev.put(neighbor, current);
                    pq.add(new Pair<>(neighbor, newDist));
                }
            }
        }

        List<String> path = new ArrayList<>();
        for (String at = targetGene; at != null; at = prev.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path.isEmpty() ? Collections.singletonList("No path available") : path;
    }

    private static class Pair<T, U> {
        private final T first;
        private final U second;

        public Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }

        public T getFirst() {
            return first;
        }

        public U getSecond() {
            return second;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Pair<?, ?> pair = (Pair<?, ?>) o;
            return Objects.equals(first, pair.first) &&
                Objects.equals(second, pair.second);
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }
    }

}


//import com.powem.inv.algos.GeneExpressionSimulator;
//import java.util.Arrays;
//import java.util.List;
//
//public class Main {
//    public static void main(String[] args) {
//        GeneExpressionSimulator simulator = new GeneExpressionSimulator();
//        //TEST
//        try {
//            simulator.addGene(null);
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        //TEST_END
//
//        //TEST
//        try {
//            simulator.addGene("");
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        //TEST_END
//
//        //TEST
//        try {
//            simulator.findOptimalSignalPath(null, "");
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        //TEST_END
//
//        simulator.addGene("GeneA");
//        simulator.addGene("GeneB");
//        simulator.addGene("GeneC");
//        simulator.connectGenes("GeneA", "GeneB", 1.0);
//
//        //TEST
//        simulator.updateConditions("GeneA", "GeneB", 2.0);
//        assert simulator.getNetwork().get("GeneA").get("GeneB") == 2.0;
//        //TEST_END
//
//        simulator.connectGenes("GeneB", "GeneC", 1.0);
//        simulator.connectGenes("GeneA", "GeneC", 10.0);
//
//        //TEST
//        List<String> expectedPath = Arrays.asList("GeneA", "GeneB", "GeneC");
//        List<String> actualPath = simulator.findOptimalSignalPath("GeneA", "GeneC");
//        assert actualPath.equals(expectedPath);
//        //TEST_END
//
//        //TEST
//        try {
//            simulator.findOptimalSignalPath("", "");
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        //TEST_END
//
//        //TEST
//        try {
//            simulator.updateConditions("GeneA", "GeneC", -1);
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        //TEST_END
//
//        //TEST
//        try {
//            simulator.updateConditions("", "GeneC", 0);
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        //TEST_END
//
//        //TEST
//        try {
//            simulator.connectGenes("GeneA", "GeneC", -1);
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        //TEST_END
//
//        //TEST
//        try {
//            simulator.connectGenes("", "GeneC", 0);
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        //TEST_END
//    }
//}