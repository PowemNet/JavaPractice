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






