package com.powem.inv.algos;
//Advanced Environmental Dynamics Simulator
//Problem:
//Develop a Java class to simulate the dynamic and cumulative environmental impacts of various industrial activities over time, incorporating feedback loops that can simulate real-world complexities such as the effects of cumulative pollution on resource regeneration rates.
//
//    Requirements:
//Class Name: AdvancedEnvironmentalDynamicsSimulator
//Method Signatures:
//public void addActivity(String activityId, double initialPollutionLevel, double initialResourceUsage)
//public void simulateYear()
//public Map<String, EnvironmentalStats> getEnvironmentalStats()
//Functionality:
//addActivity: Adds an industrial activity with its initial pollution level and resource usage.
//simulateYear: Simulates the passage of one year, adjusting pollution levels and resource usage based on feedback mechanisms.
//    getEnvironmentalStats: Returns the current environmental statistics for each activity, including adjusted pollution and resource usage levels.


import java.util.HashMap;
import java.util.Map;

public class AdvancedEnvironmentalDynamicsSimulator {
    private Map<String, Activity> activities;

    public AdvancedEnvironmentalDynamicsSimulator() {
        this.activities = new HashMap<>();
    }

    public Map<String, Activity> getActivities() {
        return activities;
    }

    public void addActivity(String activityId, double initialPollutionLevel, double initialResourceUsage) {
        if (activityId == null || activityId.isEmpty()) {
            throw new IllegalArgumentException("Activity ID cannot be null or empty");
        }
        if (initialPollutionLevel < 0 || initialResourceUsage < 0) {
            throw new IllegalArgumentException("Initial values must be non-negative");
        }
        activities.put(activityId, new Activity(initialPollutionLevel, initialResourceUsage));
    }

    public void simulateYear() {
        for (Activity activity : activities.values()) {
            double feedbackImpact = calculateFeedbackImpact(activity);
            double annualIncrease = 0.03; // Annual increase of 3%
            activity.pollutionLevel = activity.pollutionLevel * (1 + annualIncrease) - feedbackImpact;
            activity.resourceUsage *= (1.0 - feedbackImpact);  // Applying feedback impact on resource usage
        }
    }


    private double calculateFeedbackImpact(Activity activity) {
        // Increasing the feedback impact to have a more noticeable effect
        return 0.05 * activity.pollutionLevel;  // Changing from 1% to 5%
    }

    public Map<String, EnvironmentalStats> getEnvironmentalStats() {
        Map<String, EnvironmentalStats> stats = new HashMap<>();
        for (Map.Entry<String, Activity> entry : activities.entrySet()) {
            Activity activity = entry.getValue();
            stats.put(entry.getKey(), new EnvironmentalStats(activity.pollutionLevel, activity.resourceUsage));
        }
        return stats;
    }

    public static class Activity {
        double pollutionLevel;
        double resourceUsage;

        Activity(double pollutionLevel, double resourceUsage) {
            this.pollutionLevel = pollutionLevel;
            this.resourceUsage = resourceUsage;
        }

        public double getPollutionLevel() {
            return this.pollutionLevel;
        }

        public double getResourceUsage() {
            return this.resourceUsage;
        }
    }

    public static class EnvironmentalStats {
        double pollutionLevel;
        double resourceUsage;

        EnvironmentalStats(double pollutionLevel, double resourceUsage) {
            this.pollutionLevel = pollutionLevel;
            this.resourceUsage = resourceUsage;
        }
    }
}

//import com.powem.inv.algos.AdvancedEnvironmentalDynamicsSimulator;
//
//public class Main {
//    public static void main(String[] args) {
//        testActivityAddition();
//        testYearlySimulation();
//        testEnvironmentalFeedback();
//        testInputValidation();
//    }
//
//    private static void testActivityAddition() {
//        AdvancedEnvironmentalDynamicsSimulator simulator = new AdvancedEnvironmentalDynamicsSimulator();
//        simulator.addActivity("Factory1", 100.0, 500.0);
//
//        // Test to ensure the activity was added correctly
//        assert simulator.getActivities().containsKey("Factory1") : "Test Failed: Activity should be added.";
//        assert simulator.getActivities().get("Factory1").getPollutionLevel() == 100.0 : "Test Failed: Initial pollution level incorrect.";
//        assert simulator.getActivities().get("Factory1").getResourceUsage() == 500.0 : "Test Failed: Initial resource usage incorrect.";
//        System.out.println("Test Passed: Activity addition verified successfully.");
//    }
//
//    private static void testYearlySimulation() {
//        AdvancedEnvironmentalDynamicsSimulator simulator = new AdvancedEnvironmentalDynamicsSimulator();
//        simulator.addActivity("Factory1", 100.0, 500.0);
//        simulator.simulateYear();
//
//        // Calculate expected pollution with an annual increase and feedback reduction
//        double initialPollution = 100.0;
//        double annualIncrease = 0.03;  // 3%
//        double feedbackImpact = 0.05 * initialPollution;  // 5% feedback
//        double expectedPollution = initialPollution * (1 + annualIncrease) - feedbackImpact;
//
//        // Compare expected with actual
//        double actualPollution = simulator.getActivities().get("Factory1").getPollutionLevel();
//        assert Math.abs(actualPollution - expectedPollution) < 0.01 : "Test Failed: Pollution level after one year incorrect. Expected: " + expectedPollution + ", Actual: " + actualPollution;
//        System.out.println("Test Passed: Yearly simulation verified successfully.");
//    }
//
//
//    private static void testEnvironmentalFeedback() {
//        AdvancedEnvironmentalDynamicsSimulator simulator = new AdvancedEnvironmentalDynamicsSimulator();
//        simulator.addActivity("Factory1", 100.0, 500.0);
//        // Simulating multiple years to observe feedback effects
//        for (int i = 0; i < 5; i++) {
//            simulator.simulateYear();
//        }
//
//        // Expect reduced impact over years due to feedback loop
//        assert simulator.getActivities().get("Factory1").getPollutionLevel() < 105.0 : "Test Failed: Pollution level should decrease over years due to feedback.";
//        assert simulator.getActivities().get("Factory1").getResourceUsage() < 500.0 : "Test Failed: Resource usage should decrease due to feedback.";
//        System.out.println("Test Passed: Environmental feedback mechanism verified successfully.");
//    }
//
//    private static void testInputValidation() {
//        AdvancedEnvironmentalDynamicsSimulator simulator = new AdvancedEnvironmentalDynamicsSimulator();
//
//        // Testing invalid inputs
//        try {
//            simulator.addActivity(null, 100.0, 500.0);
//            assert false : "Test Failed: Adding an activity with null ID should throw exception.";
//        } catch (IllegalArgumentException e) {
//            System.out.println("Test Passed: Null activity ID input validation succeeded.");
//        }
//
//        try {
//            simulator.addActivity("Factory1", -100.0, 500.0);
//            assert false : "Test Failed: Negative pollution level should throw exception.";
//        } catch (IllegalArgumentException e) {
//            System.out.println("Test Passed: Negative pollution level input validation succeeded.");
//        }
//
//        try {
//            simulator.addActivity("Factory1", 100.0, -500.0);
//            assert false : "Test Failed: Negative resource usage should throw exception.";
//        } catch (IllegalArgumentException e) {
//            System.out.println("Test Passed: Negative resource usage input validation succeeded.");
//        }
//    }
//}







