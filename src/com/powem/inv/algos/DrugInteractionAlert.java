
//      Problem: Optimizing Drug Interaction Alerts
//      Problem Statement:
//      Design an algorithm for optimizing the generation of drug interaction alerts in a hospital system. Each drug
//      has a list of other drugs it interacts with, and some interactions are more critical than others. The goal is
//      to generate a list of alerts for a given set of drugs administered to a patient, prioritizing the most critical
//      interactions while ensuring no critical interaction is missed.
//
//
//      Develop a method that processes a list of drugs and their interaction levels to generate an optimized alert
//      list. Each alert should be unique and should indicate the interaction level.
//
//      Each drug has a list of other drugs it interacts with and an associated interaction
//      level (e.g., "high", "medium", "low").
//      The system must prioritize "high" interactions over "medium" and "low".
//      The system should avoid duplicate alerts and ensure all critical interactions are included.
//
//      Implement the DrugInteractionAlert class that takes a list of drugs and their interactions, and generates a prioritized list of alerts.
//
//      Function Signature Suggestion:
//public class DrugInteractionAlert {
//  public List<String> generateAlerts(Map<String, Map<String, String>> interactions, List<String> administeredDrugs);
//}


package com.powem.inv.algos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class DrugInteractionAlert {
  private Map<String, Map<String, String>> interactions;

  public DrugInteractionAlert(Map<String, Map<String, String>> interactions) {
    if (interactions == null || interactions.isEmpty()) {
      throw new IllegalArgumentException("Interactions map cannot be null or empty");
    }
    this.interactions = interactions;
  }

  public List<String> generateAlerts(List<String> administeredDrugs) {
    if (administeredDrugs == null || administeredDrugs.isEmpty()) {
      throw new IllegalArgumentException("Administered drugs list cannot be null or empty");
    }

    Map<String, PriorityQueue<Interaction>> alerts = new HashMap<>();
    Set<String> seenInteractions = new HashSet<>();
    for (String drug : administeredDrugs) {
      if (!interactions.containsKey(drug)) continue;
      processDrugInteractions(drug, administeredDrugs, alerts, seenInteractions);
    }

    return collectAlerts(alerts);
  }

  private void processDrugInteractions(String drug, List<String> administeredDrugs, Map<String, PriorityQueue<Interaction>> alerts, Set<String> seenInteractions) {
    Map<String, String> drugInteractions = interactions.get(drug);
    for (Map.Entry<String, String> entry : drugInteractions.entrySet()) {
      String interactingDrug = entry.getKey();
      String interactionLevel = entry.getValue();
      if (administeredDrugs.contains(interactingDrug)) {
        String interactionKey = generateInteractionKey(drug, interactingDrug);
        if (!seenInteractions.contains(interactionKey)) {
          seenInteractions.add(interactionKey);
          addAlert(alerts, interactionLevel, drug, interactingDrug);
        }
      }
    }
  }

  private String generateInteractionKey(String drug1, String drug2) {
    return drug1.compareTo(drug2) < 0 ? drug1 + "_" + drug2 : drug2 + "_" + drug1;
  }

  private void addAlert(Map<String, PriorityQueue<Interaction>> alerts, String interactionLevel, String drug, String interactingDrug) {
    alerts.putIfAbsent(interactionLevel, new PriorityQueue<>((a, b) -> b.level.compareTo(a.level)));
    alerts.get(interactionLevel).add(new Interaction(drug, interactingDrug, interactionLevel));
  }

  private List<String> collectAlerts(Map<String, PriorityQueue<Interaction>> alerts) {
    List<String> result = new ArrayList<>();
    if (alerts.containsKey("high")) result.addAll(extractAlertMessages(alerts.get("high")));
    if (alerts.containsKey("medium")) result.addAll(extractAlertMessages(alerts.get("medium")));
    if (alerts.containsKey("low")) result.addAll(extractAlertMessages(alerts.get("low")));
    return result;
  }

  private List<String> extractAlertMessages(PriorityQueue<Interaction> interactions) {
    List<String> messages = new ArrayList<>();
    while (!interactions.isEmpty()) {
      Interaction interaction = interactions.poll();
      messages.add(interaction.toString());
    }
    return messages;
  }

  private static class Interaction {
    String drug;
    String interactingDrug;
    String level;

    Interaction(String drug, String interactingDrug, String level) {
      this.drug = drug;
      this.interactingDrug = interactingDrug;
      this.level = level;
    }

    @Override
    public String toString() {
      return drug + " interacts with " + interactingDrug + " (" + level + ")";
    }
  }
}

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
//    int m, n;
//    int[] depot;
//    List<int[]> wasteBlocks;
//    WasteCollectionPlanner planner;
//    List<int[]> route;
//
//    m = 5;
//    n = 5;
//    depot = new int[]{0, 0};
//    wasteBlocks = Arrays.asList(
//            new int[]{1, 2},
//            new int[]{3, 3},
//            new int[]{4, 1}
//    );
//    planner = new WasteCollectionPlanner(m, n, depot, wasteBlocks);
//    route = planner.planRoute();
//
//    // TEST
//    assert route.size() == 5;
//    // TEST END
//
//    // TEST
//    assert Arrays.equals(route.get(0), depot);
//    // TEST END
//
//    // TEST
//    assert Arrays.equals(route.get(route.size() - 1), depot);
//    // TEST END
//
//
//    Set<int[]> visitedBlocks = new HashSet<>(wasteBlocks);
//    for (int i = 1; i < route.size() - 1; i++) {
//
//      // TEST
//      assert visitedBlocks.contains(route.get(i));
//      // TEST END
//    }
//
//    m = 10;
//    n = 10;
//    depot = new int[]{0, 0};
//    wasteBlocks = Arrays.asList(
//            new int[]{1, 2},
//            new int[]{3, 3},
//            new int[]{4, 1},
//            new int[]{6, 6},
//            new int[]{8, 8},
//            new int[]{9, 9}
//    );
//    planner = new WasteCollectionPlanner(m, n, depot, wasteBlocks);
//    route = planner.planRoute();
//
//    // TEST
//    assert route.size() == 8;
//    // TEST END
//
//    // TEST
//    assert Arrays.equals(route.get(0), depot);
//    // TEST END
//
//    // TEST
//    assert Arrays.equals(route.get(route.size() - 1), depot);
//    // TEST END
//
//
//    visitedBlocks = new HashSet<>(wasteBlocks);
//    for (int i = 1; i < route.size() - 1; i++) {
//
//      // TEST
//      assert visitedBlocks.contains(route.get(i));
//      // TEST END
//    }
//
//    try {
//      new WasteCollectionPlanner(-5, 5, new int[]{0, 0}, Arrays.asList(new int[]{1, 2}));
//
//      // TEST
//      assert false;
//      // TEST END
//
//    } catch (IllegalArgumentException e) {
//      // TEST
//      assert true;
//      // TEST END
//    }
//
//    try {
//      new WasteCollectionPlanner(5, 5, null, Arrays.asList(new int[]{1, 2}));
//
//      // TEST
//      assert false;
//      // TEST END
//
//    } catch (IllegalArgumentException e) {
//      // TEST
//      assert true;
//      // TEST END
//    }
//
//    try {
//      new WasteCollectionPlanner(5, 5, new int[]{0, 0}, new ArrayList<>());
//
//      // TEST
//      assert false;
//      // TEST END
//
//    } catch (IllegalArgumentException e) {
//      // TEST
//      assert true;
//      // TEST END
//    }
//
//    m = 5;
//    n = 5;
//    depot = new int[]{0, 0};
//    wasteBlocks = Arrays.asList(new int[]{1, 2});
//    planner = new WasteCollectionPlanner(m, n, depot, wasteBlocks);
//    route = planner.planRoute();
//
//    // TEST
//    assert route.size() == 3;
//    // TEST END
//
//    // TEST
//    assert Arrays.equals(route.get(0), depot);
//    // TEST END
//
//    // TEST
//    assert Arrays.equals(route.get(1), new int[]{1, 2});
//    // TEST END
//
//    // TEST
//    assert Arrays.equals(route.get(2), depot);
//    // TEST END
//  }
//}
