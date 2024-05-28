import com.powem.inv.algos.AirTrafficControlSystem;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    AirTrafficControlSystem system = new AirTrafficControlSystem();

    // TEST
    system.addAircraft("A1", 40.7128, -74.0060, 10000.0, 550.0, "Los Angeles");
    system.addAircraft("A2", 34.0522, -118.2437, 12000.0, 600.0, "New York");
    system.manageFlightSchedules();
    List<String> flightStatus = system.getFlightStatus();
    assert flightStatus.size() == 2;
    assert flightStatus.get(0).contains("A1");
    assert flightStatus.get(1).contains("A2");
    // TEST_END

    // TEST
    system.updateAircraftPosition("A1", 41.0, -75.0, 10500.0);
    system.manageFlightSchedules();
    flightStatus = system.getFlightStatus();
    assert flightStatus.get(0).contains("(41.0, -75.0, 10500.0)");
    // TEST_END

    // TEST
    try {
      system.addAircraft("", 40.7128, -74.0060, 10000.0, 550.0, "Los Angeles");
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    // TEST_END

    // TEST
    try {
      system.updateAircraftPosition("A1", 91.0, -75.0, 10500.0);
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    // TEST_END

    // TEST
    try {
      system.updateAircraftPosition("A3", 40.0, -70.0, 9000.0);
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    // TEST_END

    // TEST
    system.addAircraft("A3", 51.5074, -0.1278, 9000.0, 500.0, "Chicago");
    system.addAircraft("A4", 35.6895, 139.6917, 11000.0, 650.0, "San Francisco");
    system.manageFlightSchedules();
    flightStatus = system.getFlightStatus();
    assert flightStatus.size() == 4;
    // TEST_END

    // TEST
    AirTrafficControlSystem emptySystem = new AirTrafficControlSystem();
    emptySystem.manageFlightSchedules();
    List<String> emptyFlightStatus = emptySystem.getFlightStatus();
    assert emptyFlightStatus.isEmpty();
    // TEST_END

    // TEST
    system.updateAircraftPosition("A1", 42.0, -76.0, 11000.0);
    system.manageFlightSchedules();
    flightStatus = system.getFlightStatus();
    assert flightStatus.get(0).contains("(42.0, -76.0, 11000.0)");
    // TEST_END

    // TEST
    system.addAircraft("A5", 37.7749, -122.4194, 11500.0, 700.0, "Miami");
    system.manageFlightSchedules();
    flightStatus = system.getFlightStatus();
    assert flightStatus.size() == 5;

    system.updateAircraftPosition("A5", 38.0, -123.0, 11500.0);
    system.manageFlightSchedules();
    flightStatus = system.getFlightStatus();
    assert flightStatus.get(4).contains("A5");
    assert flightStatus.get(4).contains("(38.0, -123.0, 11500.0)");
    // TEST_END
  }
}
