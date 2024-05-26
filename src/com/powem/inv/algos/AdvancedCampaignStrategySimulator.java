package com.powem.inv.algos;
//Political Campaign Strategy and Demographic Analysis Simulator
//Problem:
//Develop a Java class to simulate and analyze political campaign strategies across different regions, factoring in demographic segments and socio-economic influences. This enhanced simulator will help political analysts predict the effectiveness of different campaign tactics more accurately by considering multi-dimensional data.
//
//Requirements:
//Class Name: AdvancedCampaignStrategySimulator
//Method Signatures:
//public void addRegion(String regionName, int population, Map<String, Double> demographicData)
//public void applyStrategy(String regionName, StrategyType strategy, Map<String, Double> demographicImpact)
//public double predictOutcome(String regionName)
//Functionality:
//addRegion: Adds a region with its population and a map of demographic percentages (e.g., age groups, income brackets).
//applyStrategy: Applies a specific campaigning strategy to a region, which modifies the predicted voting outcome based on demographic adaptability and strategy effectiveness.
//predictOutcome: Estimates the percentage of votes for the party after applying the strategy, considering demographic factors and historical trends.


import java.util.HashMap;
import java.util.Map;

public class AdvancedCampaignStrategySimulator {
    private Map<String, RegionData> regions;

    public AdvancedCampaignStrategySimulator() {
        this.regions = new HashMap<>();
    }

    public Map<String, RegionData> getRegions() {
        return regions;
    }

    public void addRegion(String regionName, int population, Map<String, Double> demographicData) {
        if (regionName == null || regionName.isEmpty()) {
            throw new IllegalArgumentException("Region name cannot be null or empty");
        }
        if (population <= 0) {
            throw new IllegalArgumentException("Invalid population value");
        }
        // Check for negative demographic percentages
        for (Double value : demographicData.values()) {
            if (value < 0) {
                throw new IllegalArgumentException("Demographic percentages must be non-negative");
            }
        }
        regions.put(regionName, new RegionData(population, demographicData));
    }


    public void applyStrategy(String regionName, StrategyType strategy, Map<String, Double> demographicImpact) {
        if (regionName == null || regionName.isEmpty() || !regions.containsKey(regionName)) {
            throw new IllegalArgumentException("Region name is invalid or not found");
        }
        if (strategy == null) {
            throw new IllegalArgumentException("Strategy cannot be null");
        }
        RegionData region = regions.get(regionName);
        region.applyStrategy(strategy, demographicImpact);
    }

    public double predictOutcome(String regionName) {
        if (regionName == null || regionName.isEmpty() || !regions.containsKey(regionName)) {
            throw new IllegalArgumentException("Region name is invalid or not found");
        }
        return regions.get(regionName).calculateProjectedVotingOutcome();
    }

    public static class RegionData {
        int population;
        Map<String, Double> demographicData; // demographic identifier -> percentage
        double baseVotingPercentage;

        RegionData(int population, Map<String, Double> demographicData) {
            this.population = population;
            this.demographicData = demographicData;
            this.baseVotingPercentage = calculateBaseVotingPercentage(demographicData);
        }

        void applyStrategy(StrategyType strategy, Map<String, Double> demographicImpact) {
            demographicData.forEach((demographic, percentage) -> {
                double impact = demographicImpact.getOrDefault(demographic, 0.0);
                demographicData.put(demographic, demographicData.get(demographic) + impact * strategy.getEffectiveness());
            });
        }

        double calculateProjectedVotingOutcome() {
            return demographicData.values().stream().mapToDouble(Double::doubleValue).average().orElse(baseVotingPercentage);
        }

        private double calculateBaseVotingPercentage(Map<String, Double> demographicData) {
            // Simulate initial voting percentage based on demographics
            return demographicData.values().stream().mapToDouble(v -> v * Math.random()).sum();
        }

        public int getPopulation() {
            return population;
        }

        public Map<String, Double> getDemographicData() {
            return demographicData;
        }
    }

    public enum StrategyType {
        GRASSROOTS(1.0), MEDIA_BLITZ(1.5), DEBATE_PRESENCE(1.2);

        private final double effectiveness;

        StrategyType(double effectiveness) {
            this.effectiveness = effectiveness;
        }

        public double getEffectiveness() {
            return effectiveness;
        }
    }
}

//
//import com.powem.inv.algos.AdvancedCampaignStrategySimulator;
//import java.util.HashMap;
//import java.util.Map;
//
//public class Main {
//    public static void main(String[] args) {
//        testRegionAddition();
//        testStrategyApplication();
//        testOutcomePrediction();
//        testInputValidation();
//    }
//
//    private static void testRegionAddition() {
//        AdvancedCampaignStrategySimulator simulator = new AdvancedCampaignStrategySimulator();
//        Map<String, Double> demographics = new HashMap<>();
//        demographics.put("Youth", 30.0);
//        demographics.put("Adult", 50.0);
//        demographics.put("Senior", 20.0);
//        simulator.addRegion("Metropolis", 1000000, demographics);
//
//        //TEST
//        assert simulator.getRegions().containsKey("Metropolis") : "Test Failed: Metropolis should be recorded.";
//        assert simulator.getRegions().get("Metropolis").getPopulation() == 1000000 : "Test Failed: Population should match.";
//        assert simulator.getRegions().get("Metropolis").getDemographicData().equals(demographics) : "Test Failed: Demographic data should match.";
//        System.out.println("Test Passed: Region addition with demographic data verified successfully.");
//        //TEST_END
//    }
//
//    private static void testStrategyApplication() {
//        AdvancedCampaignStrategySimulator simulator = new AdvancedCampaignStrategySimulator();
//        Map<String, Double> demographics = new HashMap<>();
//        demographics.put("Youth", 25.0);
//        demographics.put("Adult", 60.0);
//        demographics.put("Senior", 15.0);
//        simulator.addRegion("Utopia", 800000, demographics);
//
//        Map<String, Double> impact = new HashMap<>();
//        impact.put("Youth", 5.0);
//        impact.put("Adult", 3.0);
//        simulator.applyStrategy("Utopia", AdvancedCampaignStrategySimulator.StrategyType.MEDIA_BLITZ, impact);
//
//        //TEST
//        double expectedYouthImpact = 25.0 + 5.0 * 1.5; // Strategy effectiveness is 1.5
//        assert Math.abs(simulator.getRegions().get("Utopia").getDemographicData().get("Youth") - expectedYouthImpact) < 0.01 : "Test Failed: Youth demographic impact calculation error.";
//        System.out.println("Test Passed: Strategy application and demographic impact calculations verified successfully.");
//        //TEST_END
//    }
//
//    private static void testOutcomePrediction() {
//        AdvancedCampaignStrategySimulator simulator = new AdvancedCampaignStrategySimulator();
//        Map<String, Double> demographics = new HashMap<>();
//        demographics.put("Youth", 30.0);
//        demographics.put("Adult", 70.0);
//        simulator.addRegion("Eden", 500000, demographics);
//
//        //TEST
//        double outcome = simulator.predictOutcome("Eden");
//        assert outcome > 0 && outcome <= 100 : "Test Failed: Projected voting outcome should be within valid range.";
//        System.out.println("Test Passed: Voting outcome prediction verified successfully.");
//        //TEST_END
//    }
//
//    private static void testInputValidation() {
//        AdvancedCampaignStrategySimulator simulator = new AdvancedCampaignStrategySimulator();
//
//        //TEST
//        try {
//            simulator.addRegion(null, 1000000, new HashMap<>());
//            assert false : "Test Failed: Adding a region with null name should throw exception.";
//        } catch (IllegalArgumentException e) {
//            System.out.println("Test Passed: Null region name input validation succeeded.");
//        }
//        //TEST_END
//
//        //TEST
//        try {
//            Map<String, Double> demographics = new HashMap<>();
//            demographics.put("Youth", -10.0);
//            simulator.addRegion("Eden", 500000, demographics);
//            assert false : "Test Failed: Adding a region with negative demographic percentage should throw exception.";
//        } catch (IllegalArgumentException e) {
//            System.out.println("Test Passed: Negative demographic percentage input validation succeeded.");
//        }
//        //TEST_END
//
//        //TEST
//        try {
//            simulator.applyStrategy("Nonexistent", AdvancedCampaignStrategySimulator.StrategyType.GRASSROOTS, new HashMap<>());
//            assert false : "Test Failed: Applying strategy to a non-existent region should throw exception.";
//        } catch (IllegalArgumentException e) {
//            System.out.println("Test Passed: Non-existent region strategy application validation succeeded.");
//        }
//        //TEST_END
//    }
//}





