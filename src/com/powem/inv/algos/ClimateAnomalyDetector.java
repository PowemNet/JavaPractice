package com.powem.inv.algos;
//Climate Data Anomaly Detection

//**Problem**
//Create a Java class to detect anomalies in historical climate data, focusing on temperature
//fluctuations that significantly deviate from historical averages. This system will help
//researchers identify unusual temperature events that could indicate significant climatic changes.
//
//**Requirements**
//Class Name: ClimateAnomalyDetector
//
//**Method Signatures**:
//
//public void addTemperatureRecord(String date, double temperature)
//
//public List<String> detectAnomalies(double threshold)
//
//**Functionality**:
//addTemperatureRecord: Records the temperature for a given date.
//
//detectAnomalies: Returns a list of dates where the temperature was outside the normal
//range by more than the specified threshold percentage compared to the historical average.


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClimateAnomalyDetector {
    private Map<String, Double> temperatureRecords;
    private double averageTemperature;

    public ClimateAnomalyDetector() {
        this.temperatureRecords = new HashMap<>();
        this.averageTemperature = 0.0;
    }

    public double getAverageTemperature(){
        return this.averageTemperature;
    }

    public void addTemperatureRecord(String date, double temperature) {
        if (date == null || date.isEmpty()) {
            throw new IllegalArgumentException("Date cannot be null or empty");
        }
        if (Double.isNaN(temperature) || Double.isInfinite(temperature)) {
            throw new IllegalArgumentException("Temperature must be a valid number");
        }
        temperatureRecords.put(date, temperature);
        updateAverageTemperature(temperature);
    }

    private void updateAverageTemperature(double newTemp) {
        averageTemperature = temperatureRecords.values().stream()
            .mapToDouble(Double::doubleValue)
            .average()
            .orElse(Double.NaN);
    }

    public List<String> detectAnomalies(double threshold) {
        if (threshold < 0) {
            throw new IllegalArgumentException("Threshold must be non-negative");
        }
        List<String> anomalies = new ArrayList<>();
        double lowerBound = averageTemperature * (1 - threshold);
        double upperBound = averageTemperature * (1 + threshold);

        for (Map.Entry<String, Double> entry : temperatureRecords.entrySet()) {
            double temp = entry.getValue();
            if (temp < lowerBound || temp > upperBound) {
                anomalies.add(entry.getKey());
            }
        }
        return anomalies;
    }
}


//import java.util.List;
//
//public class Main {
//    public static void main(String[] args) {
//        ClimateAnomalyDetector detector = new ClimateAnomalyDetector();
//        detector.addTemperatureRecord("2021-01-01", 20.0);
//        detector.addTemperatureRecord("2021-01-02", 22.0);
//        detector.addTemperatureRecord("2021-01-03", 18.0);
//
//        //TEST
//        double expectedAverage = (20.0 + 22.0 + 18.0) / 3;
//        assert Math.abs(detector.getAverageTemperature() - expectedAverage) < 0.01;
//        //TEST_END
//
//        detector.addTemperatureRecord("2021-01-04", 30.0);
//        //TEST
//        List<String> anomalies = detector.detectAnomalies(0.20);
//        assert anomalies.contains("2021-01-04");
//        assert anomalies.size() == 1;
//        //TEST_END
//
//
//        //TEST
//        try {
//            detector.addTemperatureRecord(null, 20.0);
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        //TEST_END
//
//        //TEST
//        try {
//            detector.addTemperatureRecord("", 20.0);
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        //TEST_END
//
//        //TEST
//        try {
//            detector.addTemperatureRecord("2021-01-05", Double.NaN);
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        //TEST_END
//
//        //TEST
//        try {
//            detector.detectAnomalies(-0.1);
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        //TEST_END
//    }
//}


