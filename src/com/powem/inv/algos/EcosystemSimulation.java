//Problem: Ecosystem Simulation for Wildlife Conservation
//Problem Statement:
//Develop a system to simulate an ecosystem for wildlife conservation efforts. The system should
//model interactions between different species, track population changes, and predict the impact
//of various environmental factors on the ecosystem.
//
//Create a system that simulates an ecosystem, models interactions between species, tracks
//population changes, and predicts the impact of environmental factors.
//
//The ecosystem consists of various species, each with a unique ID, population size, and
//interaction rules.
//Environmental factors such as temperature, precipitation, and human activity influence the
//ecosystem.
//The system should support adding species, updating environmental factors, simulating population
//changes, and predicting the impact of environmental factors.
//Function Signature:
//public class EcosystemSimulation {
//    public void addSpecies(String speciesId, int population, Map<String, Double> interactionRules);
//    public void updateEnvironmentalFactors(double temperature, double precipitation, double humanActivity);
//    public void simulateEcosystem(int days);
//    public Map<String, Integer> getPopulationSizes();
//}

package com.powem.inv.algos;

import java.util.HashMap;
import java.util.Map;

public class EcosystemSimulation {
  private Map<String, Species> speciesMap = new HashMap<>();
  private double temperature;
  private double precipitation;
  private double humanActivity;

  public void addSpecies(String speciesId, int population, Map<String, Double> interactionRules) {
    if (speciesId == null || speciesId.isEmpty() || population < 0 || interactionRules == null) {
      throw new IllegalArgumentException("Invalid species parameters");
    }
    speciesMap.put(speciesId, new Species(speciesId, population, interactionRules));
  }

  public void updateEnvironmentalFactors(double temperature, double precipitation, double humanActivity) {
    if (temperature < -50 || temperature > 50 || precipitation < 0 || precipitation > 500 || humanActivity < 0 || humanActivity > 100) {
      throw new IllegalArgumentException("Invalid environmental factors");
    }
    this.temperature = temperature;
    this.precipitation = precipitation;
    this.humanActivity = humanActivity;
  }

  public void simulateEcosystem(int days) {
    for (int day = 0; day < days; day++) {
      for (Species species : speciesMap.values()) {
        species.simulateDay(temperature, precipitation, humanActivity, speciesMap);
      }
    }
  }

  public Map<String, Integer> getPopulationSizes() {
    Map<String, Integer> populationSizes = new HashMap<>();
    for (Species species : speciesMap.values()) {
      populationSizes.put(species.getSpeciesId(), species.getPopulation());
    }
    return populationSizes;
  }

  private static class Species {
    private String speciesId;
    private int population;
    private Map<String, Double> interactionRules;

    public Species(String speciesId, int population, Map<String, Double> interactionRules) {
      this.speciesId = speciesId;
      this.population = population;
      this.interactionRules = interactionRules;
    }

    public String getSpeciesId() {
      return speciesId;
    }

    public int getPopulation() {
      return population;
    }

    public void simulateDay(double temperature, double precipitation, double humanActivity, Map<String, Species> speciesMap) {
      for (Map.Entry<String, Double> interaction : interactionRules.entrySet()) {
        Species otherSpecies = speciesMap.get(interaction.getKey());
        if (otherSpecies != null) {
          population += population * interaction.getValue() * otherSpecies.getPopulation() / 1000;
        }
      }
      population -= population * (humanActivity / 1000);
      population = Math.max(population, 0);
    }
  }
}

//import com.powem.inv.algos.EcosystemSimulation;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
//public class Main {
//  public static void main(String[] args) {
//    EcosystemSimulation simulation = new EcosystemSimulation();
//
//    // TEST
//    Map<String, Double> interactionRules1 = new HashMap<>();
//    interactionRules1.put("SpeciesB", -0.01);
//    simulation.addSpecies("SpeciesA", 1000, interactionRules1);
//
//    Map<String, Double> interactionRules2 = new HashMap<>();
//    interactionRules2.put("SpeciesA", 0.005);
//    simulation.addSpecies("SpeciesB", 500, interactionRules2);
//
//    simulation.updateEnvironmentalFactors(25, 100, 10);
//    simulation.simulateEcosystem(30);
//    Map<String, Integer> populationSizes = simulation.getPopulationSizes();
//    assert populationSizes.get("SpeciesA") > 0;
//    assert populationSizes.get("SpeciesB") > 0;
//    // TEST_END
//
//    // TEST
//    try {
//      simulation.addSpecies("", 100, new HashMap<>());
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      simulation.updateEnvironmentalFactors(60, 100, 10);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    simulation.addSpecies("SpeciesC", 200, Collections.singletonMap("SpeciesA", -0.02));
//    simulation.simulateEcosystem(10);
//    populationSizes = simulation.getPopulationSizes();
//    assert populationSizes.get("SpeciesC") > 0;
//    assert populationSizes.get("SpeciesA") > 0;
//    assert populationSizes.get("SpeciesB") > 0;
//    // TEST_END
//
//    // TEST
//    simulation.updateEnvironmentalFactors(20, 80, 90);
//    simulation.simulateEcosystem(20);
//    populationSizes = simulation.getPopulationSizes();
//    assert populationSizes.get("SpeciesA") > 0;
//    assert populationSizes.get("SpeciesB") > 0;
//    assert populationSizes.get("SpeciesC") > 0;
//    // TEST_END
//  }
//}
