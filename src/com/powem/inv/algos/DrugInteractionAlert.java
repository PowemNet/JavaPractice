
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


//TESTS

//public class Main {
//  public static void main(String[] args) {
//    {
//      Map<String, Map<String, String>> interactions = new HashMap<>();
//      interactions.put("DrugA", Map.of("DrugB", "high"));
//      interactions.put("DrugB", Map.of("DrugA", "high"));
//
//      DrugInteractionAlert alertSystem = new DrugInteractionAlert(interactions);
//      List<String> administeredDrugs = Arrays.asList("DrugA", "DrugB");
//      List<String> alerts = alertSystem.generateAlerts(administeredDrugs);
//
//      // TEST
//      assert alerts.contains("DrugA interacts with DrugB (high)");
//      //TEST END
//
//      // TEST
//      assert alerts.size() == 1;
//      //TEST END
//    }
//
//    {
//      Map<String, Map<String, String>> interactions = new HashMap<>();
//      interactions.put("DrugA", Map.of("DrugB", "high", "DrugC", "medium"));
//      interactions.put("DrugB", Map.of("DrugA", "high"));
//      interactions.put("DrugC", Map.of("DrugA", "medium"));
//
//      DrugInteractionAlert alertSystem = new DrugInteractionAlert(interactions);
//      List<String> administeredDrugs = Arrays.asList("DrugA", "DrugB", "DrugC");
//      List<String> alerts = alertSystem.generateAlerts(administeredDrugs);
//
//      // TEST
//      assert alerts.contains("DrugA interacts with DrugB (high)");
//      //TEST END
//
//      // TEST
//      assert alerts.contains("DrugA interacts with DrugC (medium)");
//      //TEST END
//
//      // TEST
//      assert alerts.size() == 2;
//      //TEST END
//    }
//
//    {
//      Map<String, Map<String, String>> interactions = new HashMap<>();
//      interactions.put("DrugA", Map.of("DrugD", "high"));
//      interactions.put("DrugB", Map.of());
//      interactions.put("DrugC", Map.of());
//
//      DrugInteractionAlert alertSystem = new DrugInteractionAlert(interactions);
//      List<String> administeredDrugs = Arrays.asList("DrugA", "DrugB", "DrugC");
//      List<String> alerts = alertSystem.generateAlerts(administeredDrugs);
//
//      // TEST
//      assert alerts.isEmpty();
//      //TEST END
//
//    }
//
//    {
//      Map<String, Map<String, String>> interactions = new HashMap<>();
//      interactions.put("DrugA", Map.of("DrugB", "high"));
//
//      DrugInteractionAlert alertSystem = new DrugInteractionAlert(interactions);
//      List<String> administeredDrugs = Arrays.asList("DrugA", "DrugB", "DrugD");
//      List<String> alerts = alertSystem.generateAlerts(administeredDrugs);
//
//      // TEST
//      assert alerts.contains("DrugA interacts with DrugB (high)");
//      //TEST END
//
//      // TEST
//      assert alerts.size() == 1;
//      //TEST END
//    }
//
//    {
//      Map<String, Map<String, String>> interactions = new HashMap<>();
//      interactions.put("DrugA", Map.of());
//
//      DrugInteractionAlert alertSystem = new DrugInteractionAlert(interactions);
//      List<String> administeredDrugs = Arrays.asList("DrugA");
//      List<String> alerts = alertSystem.generateAlerts(administeredDrugs);
//
//      // TEST
//      assert alerts.isEmpty();
//      //TEST END
//    }
//
//    {
//      Map<String, Map<String, String>> interactions = new HashMap<>();
//      interactions.put("DrugA", Map.of("DrugB", "high"));
//
//      DrugInteractionAlert alertSystem = new DrugInteractionAlert(interactions);
//
//      try {
//        alertSystem.generateAlerts(null);
//
//        //TEST
//        assert false : "Null administered drugs should throw IllegalArgumentException";
//        //TEST END
//      } catch (IllegalArgumentException e) {
//        System.out.println("Test Passed: Null administered drugs handled correctly.");
//      }
//    }
//
//    {
//      Map<String, Map<String, String>> interactions = new HashMap<>();
//      interactions.put("DrugA", Map.of("DrugB", "high"));
//
//      DrugInteractionAlert alertSystem = new DrugInteractionAlert(interactions);
//
//      try {
//        alertSystem.generateAlerts(new ArrayList<>());
//
//        //TEST
//        assert false : "Empty administered drugs should throw IllegalArgumentException";
//        //TEST END
//
//      } catch (IllegalArgumentException e) {
//        System.out.println("Test Passed: Empty administered drugs handled correctly.");
//      }
//    }
//
//    {
//      try {
//        new DrugInteractionAlert(null);
//
//        //TEST
//        assert false : "Null interactions map should throw IllegalArgumentException";
//        //TEST END
//      } catch (IllegalArgumentException e) {
//        System.out.println("Test Passed: Null interactions map handled correctly.");
//      }
//    }
//
//    {
//      try {
//        new DrugInteractionAlert(new HashMap<>());
//
//        //TEST
//        assert false : "Empty interactions map should throw IllegalArgumentException";
//        //TEST END
//
//      } catch (IllegalArgumentException e) {
//        System.out.println("Test Passed: Empty interactions map handled correctly.");
//      }
//    }
//  }
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

