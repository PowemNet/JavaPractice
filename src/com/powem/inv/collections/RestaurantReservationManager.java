package com.powem.inv.collections;
//Day 7: Restaurant Reservation Management System
//Objective:
//Learn to use the LinkedList to manage a dynamic list of restaurant reservations, allowing
//for efficient additions and cancellations.
//
//You are tasked with developing a system for a restaurant to manage table reservations. Each
//reservation includes a unique reservation ID, the customer's name, the number of guests, and the reservation time.
//
//Create a Java class RestaurantReservationManager that uses a LinkedList to manage reservations. Implement
//methods to add a reservation, cancel a specific reservation, and retrieve the next reservation due.
//
//Helper functions:
//
//addReservation(String reservationId, String customerName, int numberOfGuests, Date reservationTime): Adds a
//new reservation to the list.
//
//cancelReservation(String reservationId): Removes a reservation based on its ID.
//
//getNextReservation(): Retrieves and removes the next reservation due based on the reservation time.

import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;

public class RestaurantReservationManager {

  public static class Reservation implements Comparable<Reservation> {
    private String reservationId;
    private String customerName;
    private int numberOfGuests;
    private Date reservationTime;

    public Reservation(String reservationId, String customerName, int numberOfGuests, Date reservationTime) {
      if (reservationId == null || reservationId.isEmpty()) {
        throw new IllegalArgumentException("Reservation ID cannot be null or empty.");
      }
      if (customerName == null || customerName.isEmpty()) {
        throw new IllegalArgumentException("Customer name cannot be null or empty.");
      }
      if (numberOfGuests < 1) {
        throw new IllegalArgumentException("Number of guests must be at least 1.");
      }
      if (reservationTime == null || reservationTime.before(new Date())) {
        throw new IllegalArgumentException("Reservation time cannot be null.");
      }
      this.reservationId = reservationId;
      this.customerName = customerName;
      this.numberOfGuests = numberOfGuests;
      this.reservationTime = reservationTime;
    }

    @Override
    public int compareTo(Reservation other) {
      return this.reservationTime.compareTo(other.reservationTime);
    }

    public String getReservationId() {
      return reservationId;
    }
  }

  private LinkedList<Reservation> reservations;

  public RestaurantReservationManager() {
    reservations = new LinkedList<>();
  }

  public void addReservation(String reservationId, String customerName, int numberOfGuests, Date reservationTime) {
    reservations.add(new Reservation(reservationId, customerName, numberOfGuests, reservationTime));
    reservations.sort(Comparator.naturalOrder());
  }

  public boolean cancelReservation(String reservationId) {
    return reservations.removeIf(reservation -> reservation.reservationId.equals(reservationId));
  }

  public Reservation getNextReservation() {
    return reservations.poll();
  }
}

//import java.util.Date;
//
//public class Main {
//  public static void main(String[] args) {
//    RestaurantReservationManager manager = new RestaurantReservationManager();
//    manager.addReservation("R001", "Alice Johnson", 4, new Date(System.currentTimeMillis() + 3600000));
//    manager.addReservation("R002", "Bob Smith", 2, new Date(System.currentTimeMillis() + 1800000));
//
//    //TEST
//    RestaurantReservationManager.Reservation nextReservation = manager.getNextReservation();
//    assert nextReservation != null && "R002".equals(nextReservation.getReservationId()) : "R002 should be the next reservation due.";
//    //TEST_END
//
//    //TEST
//    boolean result = manager.cancelReservation("R001");
//    assert result : "Reservation R001 should be canceled successfully.";
//    //TEST_END
//
//    //TEST
//    result = manager.cancelReservation("R999");
//    assert !result : "Attempting to cancel a non-existent reservation should return false.";
//    //TEST_END
//
//    //TEST
//    try {
//      manager.addReservation("", "John Doe", 3, new Date(System.currentTimeMillis() + 5000000));
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    try {
//      manager.addReservation("R003", "John Doe", 3, new Date(System.currentTimeMillis() - 1000));
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    try {
//      manager.addReservation("R004", "Jane Doe", 0, new Date(System.currentTimeMillis() + 5000000));
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    manager.getNextReservation();
//    assert manager.getNextReservation() == null;
//    //TEST_END
//  }
//}
