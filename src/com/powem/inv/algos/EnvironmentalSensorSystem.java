package com.powem.inv.algos;

//Problem: Environmental Sensor Data Aggregation
//Problem Statement:
//Develop a system to aggregate and analyze data from multiple environmental sensors deployed
//across various locations. The system should process real-time data from these sensors, calculate
//average readings, and detect any abnormal conditions based on predefined thresholds.
//
//Create a system that efficiently manages and aggregates real-time environmental data from
//multiple sensors, calculates average readings, and detects abnormal conditions.
//
//Each sensor reports data such as temperature, humidity, and air quality.
//The system should support adding sensors, updating real-time data, and calculating average
//readings for each location.
//Implement algorithms to detect abnormal conditions based on predefined thresholds.
//
// Function Signature:
//public class EnvironmentalSensorSystem {
//    public void addSensor(String sensorId, String location);
//    public void updateSensorData(String sensorId, Map<String, Double> readings);
//    public Map<String, Map<String, Double>> calculateAverageReadings();
//    public List<String> detectAbnormalConditions();
//}


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnvironmentalSensorSystem {
    private static class SensorData {
        String location;
        Map<String, Double> totalReadings;
        Map<String, Integer> countReadings;
        Map<String, Double> latestReadings;

        SensorData(String location) {
            this.location = location;
            this.totalReadings = new HashMap<>();
            this.countReadings = new HashMap<>();
            this.latestReadings = new HashMap<>();
        }

        void addReadings(Map<String, Double> readings) {
            for (Map.Entry<String, Double> entry : readings.entrySet()) {
                String metric = entry.getKey();
                Double value = entry.getValue();
                totalReadings.put(metric, totalReadings.getOrDefault(metric, 0.0) + value);
                countReadings.put(metric, countReadings.getOrDefault(metric, 0) + 1);
                latestReadings.put(metric, value);
            }
        }

        Map<String, Double> getAverageReadings() {
            Map<String, Double> averages = new HashMap<>();
            for (String metric : totalReadings.keySet()) {
                double average = totalReadings.get(metric) / countReadings.get(metric);
                averages.put(metric, average);
            }
            return averages;
        }
    }

    private final Map<String, SensorData> sensors = new HashMap<>();
    private final Map<String, Map<String, Double>> thresholds = new HashMap<>();

    public void addSensor(String sensorId, String location) {
        if (sensorId == null || sensorId.isEmpty() || location == null || location.isEmpty()) {
            throw new IllegalArgumentException("Invalid sensor or location");
        }
        sensors.put(sensorId, new SensorData(location));
    }

    public void updateSensorData(String sensorId, Map<String, Double> readings) {
        if (sensorId == null || sensorId.isEmpty() || readings == null) {
            throw new IllegalArgumentException("Invalid input data");
        }
        SensorData sensorData = sensors.get(sensorId);
        if (sensorData == null) {
            throw new IllegalArgumentException("Sensor not found");
        }
        sensorData.addReadings(readings);
    }

    public Map<String, Map<String, Double>> calculateAverageReadings() {
        Map<String, Map<String, Double>> averageReadings = new HashMap<>();
        for (SensorData sensorData : sensors.values()) {
            if (!sensorData.totalReadings.isEmpty()) {
                averageReadings.put(sensorData.location, sensorData.getAverageReadings());
            }
        }
        return averageReadings;
    }

    public List<String> detectAbnormalConditions() {
        List<String> abnormalities = new ArrayList<>();
        for (SensorData sensorData : sensors.values()) {
            for (Map.Entry<String, Double> entry : sensorData.latestReadings.entrySet()) {
                String metric = entry.getKey();
                Double value = entry.getValue();
                Map<String, Double> threshold = thresholds.get(metric);
                if (threshold != null && (value < threshold.get("min") || value > threshold.get("max"))) {
                    abnormalities.add("Abnormal condition detected at " + sensorData.location + ": " + metric + " = " + value);
                }
            }
        }
        return abnormalities;
    }

    public void setThresholds(Map<String, Map<String, Double>> thresholds) {
        this.thresholds.putAll(thresholds);
    }
}

//TEST--
//import com.powem.inv.algos.EnvironmentalSensorSystem;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class Main {
//  public static void main(String[] args) {
//    EnvironmentalSensorSystem system = new EnvironmentalSensorSystem();
//
//    // TEST
//    system.addSensor("Sensor1", "Location1");
//    system.addSensor("Sensor2", "Location2");
//    Map<String, Map<String, Double>> averageReadings = system.calculateAverageReadings();
//    assert averageReadings.isEmpty();
//    // TEST_END
//
//    // TEST
//    try {
//      system.addSensor("", "Location1");
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      system.addSensor(null, "Location1");
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      system.addSensor("Sensor1", "");
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      system.addSensor("Sensor1", null);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    Map<String, Double> readings1 = new HashMap<>();
//    readings1.put("Temperature", 25.0);
//    readings1.put("Humidity", 60.0);
//    system.updateSensorData("Sensor1", readings1);
//
//    Map<String, Double> readings2 = new HashMap<>();
//    readings2.put("Temperature", 22.0);
//    readings2.put("Humidity", 55.0);
//    system.updateSensorData("Sensor2", readings2);
//
//    averageReadings = system.calculateAverageReadings();
//    assert !averageReadings.isEmpty();
//    // TEST_END
//
//    // TEST
//    try {
//      system.updateSensorData("Sensor1", null);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      system.updateSensorData("", readings1);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      system.updateSensorData("Sensor3", readings1);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    Map<String, Map<String, Double>> thresholds = new HashMap<>();
//    Map<String, Double> temperatureThresholds = new HashMap<>();
//    temperatureThresholds.put("min", 20.0);
//    temperatureThresholds.put("max", 30.0);
//    thresholds.put("Temperature", temperatureThresholds);
//
//    Map<String, Double> humidityThresholds = new HashMap<>();
//    humidityThresholds.put("min", 40.0);
//    humidityThresholds.put("max", 70.0);
//    thresholds.put("Humidity", humidityThresholds);
//
//    system.setThresholds(thresholds);
//
//    Map<String, Double> abnormalReadings = new HashMap<>();
//    abnormalReadings.put("Temperature", 18.0);
//    abnormalReadings.put("Humidity", 75.0);
//    system.updateSensorData("Sensor1", abnormalReadings);
//
//    List<String> abnormalities = system.detectAbnormalConditions();
//    assert abnormalities.size() == 2;
//    // TEST_END
//
//    // TEST
//    assert abnormalities.get(0).contains("Temperature = 18.0");
//    // TEST_END
//
//    // TEST
//    assert abnormalities.get(1).contains("Humidity = 75.0");
//    // TEST_END
//  }
//}