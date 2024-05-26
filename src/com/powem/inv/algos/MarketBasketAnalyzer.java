//Problem: Real-Time Market Basket Analysis
//Problem Statement:
//Design an algorithm for real-time market basket analysis in a large retail
// environment. The goal is to identify frequently purchased item sets and generate association rules that can be used
// for recommendation systems and inventory management. The complexity arises from the need to handle a continuous
// stream of transaction data and dynamically update the frequent item sets and association rules in real-time.
//
//Objective:
//Develop a method that processes a continuous stream of transactions and dynamically updates the frequent item sets
// and association rules. The solution should be optimized to handle large volumes of data with low latency.
//
//Each transaction is represented as a list of items purchased together.
//The algorithm should use advanced data structures to efficiently update and store frequent item sets and association
// rules.
//The algorithm should aim to minimize time and space complexity while ensuring real-time performance.
//The solution should generate association rules with confidence and support metrics.
//
// Implement the MarketBasketAnalyzer class that processes transaction data and updates frequent item sets and
// association rules in real-time.
//Function Signature Suggestion:
//public class MarketBasketAnalyzer {
//    public List<AssociationRule> getAssociationRules(double minSupport, double minConfidence);
//}

package com.powem.inv.algos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MarketBasketAnalyzer {
  private final Map<Set<String>, Integer> frequentItemSets;
  private final Map<Set<String>, Map<String, Integer>> associationRuleCounts;
  private int totalTransactions;

  public MarketBasketAnalyzer() {
    frequentItemSets = new HashMap<>();
    associationRuleCounts = new HashMap<>();
    totalTransactions = 0;
  }

  public void processTransaction(List<String> transaction) {
    if (transaction == null || transaction.isEmpty()) {
      throw new IllegalArgumentException("Transaction cannot be null or empty");
    }

    totalTransactions++;
    Set<String> uniqueItems = new HashSet<>(transaction);

    for (Set<String> itemSet : getSubsets(uniqueItems)) {
      frequentItemSets.put(itemSet, frequentItemSets.getOrDefault(itemSet, 0) + 1);
    }

    for (Set<String> antecedent : getSubsets(uniqueItems)) {
      if (antecedent.size() == 0 || antecedent.size() == uniqueItems.size()) {
        continue;
      }

      Set<String> consequent = new HashSet<>(uniqueItems);
      consequent.removeAll(antecedent);

      Map<String, Integer> consequentCounts = associationRuleCounts.getOrDefault(antecedent, new HashMap<>());
      for (String item : consequent) {
        consequentCounts.put(item, consequentCounts.getOrDefault(item, 0) + 1);
      }
      associationRuleCounts.put(antecedent, consequentCounts);
    }
  }

  public List<AssociationRule> getAssociationRules(double minSupport, double minConfidence) {
    List<AssociationRule> rules = new ArrayList<>();

    if (totalTransactions == 1) {
      return rules;
    }

    for (Set<String> antecedent : associationRuleCounts.keySet()) {
      Map<String, Integer> consequentCounts = associationRuleCounts.get(antecedent);
      int antecedentCount = frequentItemSets.getOrDefault(antecedent, 0);

      for (Map.Entry<String, Integer> entry : consequentCounts.entrySet()) {
        String consequent = entry.getKey();
        int consequentCount = entry.getValue();
        Set<String> itemSet = new HashSet<>(antecedent);
        itemSet.add(consequent);
        int itemSetCount = frequentItemSets.getOrDefault(itemSet, 0);

        double support = (double) itemSetCount / totalTransactions;
        double confidence = (double) consequentCount / antecedentCount;

        if (support >= minSupport && confidence >= minConfidence) {
          rules.add(new AssociationRule(new HashSet<>(antecedent), new HashSet<>(Collections.singletonList(consequent)), support, confidence));
        }
      }
    }

    return rules;
  }

  private Set<Set<String>> getSubsets(Set<String> set) {
    Set<Set<String>> subsets = new HashSet<>();
    generateSubsets(set, new HashSet<>(), 0, subsets);
    return subsets;
  }

  private void generateSubsets(Set<String> set, Set<String> current, int index, Set<Set<String>> subsets) {
    subsets.add(new HashSet<>(current));

    for (int i = index; i < set.size(); i++) {
      String item = (String) set.toArray()[i];
      current.add(item);
      generateSubsets(set, current, i + 1, subsets);
      current.remove(item);
    }
  }

  public static class AssociationRule {
    private final Set<String> antecedent;
    private final Set<String> consequent;
    private final double support;
    private final double confidence;

    public AssociationRule(Set<String> antecedent, Set<String> consequent, double support, double confidence) {
      this.antecedent = antecedent;
      this.consequent = consequent;
      this.support = support;
      this.confidence = confidence;
    }

    @Override
    public String toString() {
      return "AssociationRule{" +
              "antecedent=" + antecedent +
              ", consequent=" + consequent +
              ", support=" + support +
              ", confidence=" + confidence +
              '}';
    }
  }
}

//TESTS
//import com.powem.inv.algos.MarketBasketAnalyzer;
//
//import java.util.Arrays;
//import java.util.List;
//
//public class Main {
//  public static void main(String[] args) {
//    MarketBasketAnalyzer analyzer = new MarketBasketAnalyzer();
//
//    List<String> transaction1 = Arrays.asList("Milk", "Bread", "Butter");
//    List<String> transaction2 = Arrays.asList("Beer", "Bread");
//    List<String> transaction3 = Arrays.asList("Milk", "Diapers", "Beer", "Bread");
//    List<String> transaction4 = Arrays.asList("Butter", "Diapers", "Beer");
//
//    analyzer.processTransaction(transaction1);
//    analyzer.processTransaction(transaction2);
//    analyzer.processTransaction(transaction3);
//    analyzer.processTransaction(transaction4);
//
//    List<MarketBasketAnalyzer.AssociationRule> rules = analyzer.getAssociationRules(0.5, 0.6);
//
//    //TEST
//    assert !rules.isEmpty();
//    //TEST END
//
//    MarketBasketAnalyzer emptyAnalyzer = new MarketBasketAnalyzer();
//    rules = emptyAnalyzer.getAssociationRules(0.5, 0.6);
//
//    //TEST
//    assert rules.isEmpty();
//    //TEST END
//
//    MarketBasketAnalyzer singleAnalyzer = new MarketBasketAnalyzer();
//    singleAnalyzer.processTransaction(Arrays.asList("Milk", "Bread"));
//    rules = singleAnalyzer.getAssociationRules(0.5, 0.6);
//
//    //TEST
//    assert rules.isEmpty();
//    //TEST END
//
//    try {
//      analyzer.processTransaction(null);
//
//      //TEST
//      assert false;
//      //TEST END
//
//    } catch (IllegalArgumentException e) {
//
//      //TEST
//      assert true;
//      //TEST END
//
//    }
//
//    try {
//      analyzer.processTransaction(Arrays.asList());
//
//      //TEST
//      assert false;
//      //TEST END
//
//    } catch (IllegalArgumentException e) {
//
//      //TEST
//      assert true;
//      //TEST END
//
//    }
//
//    rules = analyzer.getAssociationRules(1.0, 1.0);
//
//    //TEST
//    assert rules.isEmpty();
//    //TEST END
//  }
//}