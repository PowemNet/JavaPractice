import com.powem.inv.algos.EnvironmentalSensorSystem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        EnvironmentalSensorSystem system = new EnvironmentalSensorSystem();

        // TEST
        system.addSensor("Sensor1", "Location1");
        system.addSensor("Sensor2", "Location2");
        Map<String, Map<String, Double>> averageReadings = system.calculateAverageReadings();
        assert averageReadings.isEmpty();
        // TEST_END

        // TEST
        try {
            system.addSensor("", "Location1");
            assert false;
        } catch (IllegalArgumentException e) {
            assert true;
        }
        // TEST_END

        // TEST
        try {
            system.addSensor(null, "Location1");
            assert false;
        } catch (IllegalArgumentException e) {
            assert true;
        }
        // TEST_END

        // TEST
        try {
            system.addSensor("Sensor1", "");
            assert false;
        } catch (IllegalArgumentException e) {
            assert true;
        }
        // TEST_END

        // TEST
        try {
            system.addSensor("Sensor1", null);
            assert false;
        } catch (IllegalArgumentException e) {
            assert true;
        }
        // TEST_END

        // TEST
        Map<String, Double> readings1 = new HashMap<>();
        readings1.put("Temperature", 25.0);
        readings1.put("Humidity", 60.0);
        system.updateSensorData("Sensor1", readings1);

        Map<String, Double> readings2 = new HashMap<>();
        readings2.put("Temperature", 22.0);
        readings2.put("Humidity", 55.0);
        system.updateSensorData("Sensor2", readings2);

        averageReadings = system.calculateAverageReadings();
        assert !averageReadings.isEmpty();
        // TEST_END

        // TEST
        try {
            system.updateSensorData("Sensor1", null);
            assert false;
        } catch (IllegalArgumentException e) {
            assert true;
        }
        // TEST_END

        // TEST
        try {
            system.updateSensorData("", readings1);
            assert false;
        } catch (IllegalArgumentException e) {
            assert true;
        }
        // TEST_END

        // TEST
        try {
            system.updateSensorData("Sensor3", readings1);
            assert false;
        } catch (IllegalArgumentException e) {
            assert true;
        }
        // TEST_END

        // TEST
        Map<String, Map<String, Double>> thresholds = new HashMap<>();
        Map<String, Double> temperatureThresholds = new HashMap<>();
        temperatureThresholds.put("min", 20.0);
        temperatureThresholds.put("max", 30.0);
        thresholds.put("Temperature", temperatureThresholds);

        Map<String, Double> humidityThresholds = new HashMap<>();
        humidityThresholds.put("min", 40.0);
        humidityThresholds.put("max", 70.0);
        thresholds.put("Humidity", humidityThresholds);

        system.setThresholds(thresholds);

        Map<String, Double> abnormalReadings = new HashMap<>();
        abnormalReadings.put("Temperature", 18.0);
        abnormalReadings.put("Humidity", 75.0);
        system.updateSensorData("Sensor1", abnormalReadings);

        List<String> abnormalities = system.detectAbnormalConditions();
        assert abnormalities.size() == 2;
        // TEST_END

        // TEST
        assert abnormalities.get(0).contains("Temperature = 18.0");
        // TEST_END

        // TEST
        assert abnormalities.get(1).contains("Humidity = 75.0");
        // TEST_END
    }
}