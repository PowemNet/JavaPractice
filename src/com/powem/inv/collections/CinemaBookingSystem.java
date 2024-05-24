package com.powem.inv.collections;
//Day1: Advanced Use of Collections in Real-World Scenarios

//You are developing a system for a cinema that needs to manage seat bookings for multiple movie screenings throughout the day. Each screening
//room has multiple rows, and each row has a number of seats. The system should handle reservations efficiently, avoiding overbooking and
//allowing quick checks for seat availability.
//
//Create a Java class CinemaBookingSystem that manages seat bookings using collections. Implement features to add a screening, book a seat,
//and check seat availability. Use nested classes to encapsulate screening and seat details.
//if an attempt to book a seat is made, and the seat is available, return true. if not, return false.

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CinemaBookingSystem {
  public static class Seat {
    private boolean isBooked;

    public Seat() {
      this.isBooked = false;
    }

    public boolean bookSeat() {
      if (!isBooked) {
        isBooked = true;
        return true;
      }
      return false;
    }

    public boolean isBooked() {
      return isBooked;
    }
  }

  public static class Screening {
    private String movieTitle;
    private Map<Integer, List<Seat>> seatsByRow;

    public Screening(String movieTitle, int rows, int seatsPerRow) {
      this.movieTitle = movieTitle;
      this.seatsByRow = new HashMap<>();
      for (int i = 1; i <= rows; i++) {
        List<Seat> seats = new ArrayList<>();
        for (int j = 0; j < seatsPerRow; j++) {
          seats.add(new Seat());
        }
        seatsByRow.put(i, seats);
      }
    }

    public boolean bookSeat(int row, int seatNumber) {
      List<Seat> seats = seatsByRow.get(row);
      if (seats != null && seatNumber < seats.size()) {
        return seats.get(seatNumber).bookSeat();
      }
      return false;
    }

    public String getMovieTitle() {
      return movieTitle;
    }
  }

  private Map<String, Screening> screenings;

  public CinemaBookingSystem() {
    this.screenings = new HashMap<>();
  }

  public void addScreening(String movieTitle, int rows, int seatsPerRow) {
    screenings.put(movieTitle, new Screening(movieTitle, rows, seatsPerRow));
  }

  public boolean bookSeat(String movieTitle, int row, int seatNumber) {
    if (movieTitle == null || movieTitle.isEmpty()) {
      throw new IllegalArgumentException("Title cannot be null or empty");
    }

    if (row <= 0 || seatNumber <= 0) {
      throw new IllegalArgumentException("row or seatNumber must be greater than 0");
    }
    Screening screening = screenings.get(movieTitle);
    if (screening != null) {
      return screening.bookSeat(row, seatNumber);
    }
    return false;
  }
}

//public class Main {
//  public static void main(String[] args) {
//    //TEST
//    CinemaBookingSystem system = new CinemaBookingSystem();
//    system.addScreening("Titanic", 5, 20);
//    assert system.bookSeat("Titanic", 3, 5);
//    //TEST_END
//
//    //TEST
//    assert !system.bookSeat("Titanic", 3, 5);
//    //TEST_END
//
//    //TEST
//    assert system.bookSeat("Titanic", 3, 3);
//    //TEST_END
//
//    //TEST
//    assert !system.bookSeat("Titanic", 3, 3);
//    //TEST_END
//
//    //TEST
//    assert !system.bookSeat("Titanic", 3, 21);
//    //TEST_END
//
//    //TEST
//    assert !system.bookSeat("HelloWord", 3, 3);
//    //TEST_END
//
//    //TEST
//    assert !system.bookSeat("10", 3, 3);
//    //TEST_END
//
//    //TEST
//    try {
//      system.bookSeat("Titanic", 0, 3);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    try {
//      system.bookSeat(null, 3, 3);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//  }
//}

