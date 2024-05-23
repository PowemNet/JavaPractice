package com.powem.inv.collections;
//Day 4: Flight Delay Management System
//Objective:
//Learn to use the PriorityQueue to manage and prioritize time-sensitive data effectively.
//
//You are developing a system for an airport to manage flight delays. Each flight is represented
//by a unique flight number, scheduled departure time, and a delay duration. Flights need to be
//rescheduled based on their new estimated departure time, which includes the delay.
//
//Create a Java class FlightDelayManager that uses a PriorityQueue to manage and prioritize flights based
//on the shortest delay to the longest. Implement methods to add flights, update flight delays, and retrieve
//the next flight scheduled to depart.
//
//Create methods:
//
//addFlight(String flightNumber, Date scheduledDeparture, int delayMinutes): Adds a flight to the system.

//updateFlightDelay(String flightNumber, int newDelayMinutes): Updates an existing flight's delay.

//getNextFlight(): Retrieves and removes the flight with the shortest delay from the queue, indicating it's next to depart.

import java.util.Calendar;
import java.util.Date;
import java.util.PriorityQueue;

public class FlightDelayManager {
  public static class Flight implements Comparable<Flight> {
    private String flightNumber;
    private Date scheduledDeparture;
    private int delayMinutes;

    public Flight(String flightNumber, Date scheduledDeparture, int delayMinutes) {
      if (flightNumber == null || flightNumber.isEmpty()) {
        throw new IllegalArgumentException("Flight number cannot be null or empty.");
      }
      if (scheduledDeparture == null) {
        throw new IllegalArgumentException("Scheduled departure cannot be null.");
      }
      if (delayMinutes < 0) {
        throw new IllegalArgumentException("Delay minutes cannot be negative.");
      }
      this.flightNumber = flightNumber;
      this.scheduledDeparture = scheduledDeparture;
      this.delayMinutes = delayMinutes;
    }

    @Override
    public int compareTo(Flight other) {
      Date thisDeparture = this.getUpdatedDeparture();
      Date otherDeparture = other.getUpdatedDeparture();
      return thisDeparture.compareTo(otherDeparture);
    }

    public void setDelayMinutes(int delayMinutes) {
      this.delayMinutes = delayMinutes;
    }

    public Date getUpdatedDeparture() {
      Calendar cal = Calendar.getInstance();
      cal.setTime(scheduledDeparture);
      cal.add(Calendar.MINUTE, delayMinutes);
      return cal.getTime();
    }

    public String getFlightNumber() {
      return flightNumber;
    }

    public int getDelayMinutes() {
      return delayMinutes;
    }
  }

  private PriorityQueue<Flight> flights;

  public FlightDelayManager() {
    this.flights = new PriorityQueue<>();
  }

  public void addFlight(String flightNumber, Date scheduledDeparture, int delayMinutes) {
    Flight newFlight = new Flight(flightNumber, scheduledDeparture, delayMinutes);
    flights.add(newFlight);
  }

  public boolean updateFlightDelay(String flightNumber, int newDelayMinutes) {
    for (Flight flight : flights) {
      if (flight.getFlightNumber().equals(flightNumber)) {
        flights.remove(flight);
        flight.setDelayMinutes(newDelayMinutes);
        flights.add(flight);
        return true;
      }
    }
    return false;
  }

  public Flight getNextFlight() {
    return flights.poll();
  }
}

//import java.util.Date;
//
//public class Main {
//  public static void main(String[] args) {
//    FlightDelayManager manager = new FlightDelayManager();
//    manager.addFlight("FL123", new Date(System.currentTimeMillis() + 3600 * 1000), 90);  // Flight in 1 hour with 90 minutes delay
//    manager.addFlight("FL456", new Date(System.currentTimeMillis() + 1800 * 1000), 30);  // Flight in 30 minutes with 30 minutes delay
//    manager.addFlight("FL789", new Date(System.currentTimeMillis() + 7200 * 1000), 120); // Flight in 2 hours with 120 minutes delay
//
//    // TEST
//    FlightDelayManager.Flight nextFlight = manager.getNextFlight();
//    assert nextFlight.getFlightNumber().equals("FL456");
//    // TEST_END
//
//    // TEST
//    assert manager.updateFlightDelay("FL123", 150);
//    nextFlight = manager.getNextFlight();
//    assert nextFlight != null && nextFlight.getFlightNumber().equals("FL123") && nextFlight.getDelayMinutes() == 150;
//    // TEST_END
//
//    // TEST
//    assert !manager.updateFlightDelay("FL000", 100);
//    // TEST_END
//
//    // TEST
//    assert manager.updateFlightDelay("FL789", 60);
//    // TEST_END
//
//    // TEST
//    assert manager.getNextFlight().getFlightNumber().equals("FL789");
//    // TEST_END
//
//    // TEST
//    manager.addFlight("FL101", new Date(System.currentTimeMillis() + 5400 * 1000), 60);
//    assert manager.getNextFlight().getFlightNumber().equals("FL101");
//    // TEST_END
//
//    // TEST
//    manager.getNextFlight();
//    assert manager.getNextFlight() == null;
//    // TEST_END
//
//    // TEST
//    manager.addFlight("FL201", new Date(System.currentTimeMillis() + 3600 * 1000), 90);
//    manager.addFlight("FL202", new Date(System.currentTimeMillis() + 3600 * 1000), 90);
//    manager.addFlight("FL203", new Date(System.currentTimeMillis() + 3600 * 1000), 90);
//    manager.getNextFlight(); // Remove one
//    assert manager.getNextFlight() != null;
//    assert manager.getNextFlight() != null;
//    // TEST_END
//
//    try {
//      manager.addFlight("FL123", new Date(System.currentTimeMillis() + 3600 * 1000), 90);
//      manager.addFlight("FL456", null, 30);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//
//    // TEST
//    try {
//      manager.addFlight("", new Date(), 10);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      manager.addFlight("FL789", new Date(), -1);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//  }
//}
