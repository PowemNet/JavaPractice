package com.powem.inv.algos;
//Advanced Environmental Dynamics Simulator
//Problem:
//Develop a Java class to simulate the dynamic and cumulative environmental impacts of
//various industrial activities over time, incorporating feedback loops that can simulate
//real-world complexities such as the effects of cumulative pollution on resource regeneration rates.
//
//Requirements:

//Class Name: EnvironmentalDynamicsSimulator

//Method Signatures:

//public void addActivity(String activityId, double initialPollutionLevel, double initialResourceUsage)

//public void simulateYear(double annualIncreasePercentage)

//public Map<String, EnvironmentalStats> getEnvironmentalStats()

//Functionality:

//addActivity: Adds an industrial activity with its initial pollution level and resource usage.

//simulateYear: Simulates the passage of one year, adjusting pollution levels and resource usage based
//on feedback mechanisms (use a feedback factor of 0.05 for pollution).

//getEnvironmentalStats: Returns the current environmental statistics for each activity, including
//adjusted pollution and resource usage levels.


import java.util.HashMap;
import java.util.Map;

public class EnvironmentalDynamicsSimulator {
    private Map<String, Activity> activities;

    public EnvironmentalDynamicsSimulator() {
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

    public void simulateYear(double annualIncreasePercentage) {
        for (Activity activity : activities.values()) {
            double feedbackImpact = calculateFeedbackImpact(activity);
            activity.pollutionLevel = activity.pollutionLevel * (1 + annualIncreasePercentage) - feedbackImpact;
            activity.resourceUsage *= (1.0 - feedbackImpact);  // Applying feedback impact on resource usage
        }
    }


    private double calculateFeedbackImpact(Activity activity) {
        return 0.05 * activity.pollutionLevel;
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
//
//import com.powem.inv.algos.EnvironmentalDynamicsSimulator;
//
//public class Main {
//    public static void main(String[] args) {
//        EnvironmentalDynamicsSimulator simulator = new EnvironmentalDynamicsSimulator();
//        simulator.addActivity("Factory1", 100.0, 500.0);
//
//        //TEST
//        assert simulator.getActivities().containsKey("Factory1");
//        //TEST_END
//
//        //TEST
//        assert simulator.getActivities().get("Factory1").getPollutionLevel() == 100.0;
//        //TEST_END
//
//        //TEST
//        assert simulator.getActivities().get("Factory1").getResourceUsage() == 500.0;
//        simulator.addActivity("Factory1", 100.0, 500.0);
//        for (int i = 0; i < 5; i++) {
//            simulator.simulateYear(0.03);
//        }
//        //TEST_END
//
//        //TEST
//        assert simulator.getActivities().get("Factory1").getPollutionLevel() < 105.0;
//        //TEST_END
//
//        //TEST
//        assert simulator.getActivities().get("Factory1").getResourceUsage() < 500.0;
//        //TEST_END
//
//        simulator.addActivity("Factory2", 100.0, 500.0);
//        simulator.simulateYear(0.03);
//
//        double initialPollution = 100.0;
//        double annualIncrease = 0.03;
//        double feedbackImpact = 0.05 * initialPollution;
//        double expectedPollution = initialPollution * (1 + annualIncrease) - feedbackImpact;
//
//        double actualPollution = simulator.getActivities().get("Factory2").getPollutionLevel();
//
//        //TEST
//        assert Math.abs(actualPollution - expectedPollution) < 0.01;
//        //TEST_END
//
//        //TEST
//        try {
//            simulator.addActivity(null, 100.0, 500.0);
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        //TEST_END
//
//        //TEST
//        try {
//            simulator.addActivity("Factory1", -100.0, 500.0);
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        //TEST_END
//
//        //TEST
//        try {
//            simulator.addActivity("Factory1", 100.0, -500.0);
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        //TEST_END
//    }
//}







