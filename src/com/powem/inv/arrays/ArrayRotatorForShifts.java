package com.powem.inv.arrays;

//In a company, a shift manager needs to organize employee schedules for a continuous operation plant that works around the clock.
//The plant operates in 5 consecutive shifts, and at the end of each week, the last two shifts need to be rotated to the front to
//prepare for the next operational week. Create a  function called rotateShifts(String[] shifts, int k) which
//updates the shift schedule by rotating the array representing the shifts by k.
//Example:
//
//Input: ["John", "Doe", "Jane", "Doe", "Chris", "Smith"], number of rotations: 2
//Output: ["Chris", "Smith", "John", "Doe", "Jane", "Doe"]
//Explanation: Rotating the shift list to the right by 2 moves the last two employees to the beginning of the list.
public class ArrayRotatorForShifts {
  public static void rotateShifts(String[] shifts, int k) {
    if (shifts == null || shifts.length == 0) {
      throw new IllegalArgumentException("Shifts cannot be null or empty");
    }
    int n = shifts.length;
    k = k % n;
    reverse(shifts, 0, n - 1);
    reverse(shifts, 0, k - 1);
    reverse(shifts, k, n - 1);
  }

  private static void reverse(String[] array, int start, int end) {
    while (start < end) {
      String temp = array[start];
      array[start] = array[end];
      array[end] = temp;
      start++;
      end--;
    }
  }
}

//public static void tests() {
//// TEST
//String[] shifts1 = {"John", "Doe", "Jane", "Doe", "Chris", "Smith"};
//    ArrayRotatorForShifts.rotateShifts(shifts1, 2);
//    assert shifts1[0].equals("Chris") && shifts1[1].equals("Smith") &&
//shifts1[2].equals("John") && shifts1[3].equals("Doe") &&
//shifts1[4].equals("Jane") && shifts1[5].equals("Doe");
//// TEST_END
//
//// TEST
//String[] shifts2 = {"Alice", "Bob", "Charlie", "Dana"};
//    ArrayRotatorForShifts.rotateShifts(shifts2, 1);
//    assert shifts2[0].equals("Dana") && shifts2[1].equals("Alice") &&
//shifts2[2].equals("Bob") && shifts2[3].equals("Charlie");
//// TEST_END
//
//// TEST
//String[] shifts3 = {"Alice", "Bob", "Charlie", "Dana"};
//    ArrayRotatorForShifts.rotateShifts(shifts3, 5);
//    assert shifts3[0].equals("Dana") && shifts3[1].equals("Alice") &&
//shifts3[2].equals("Bob") && shifts3[3].equals("Charlie");
//// TEST_END
//
//// TEST
//    try {
//String[] shifts4 = {};
//      ArrayRotatorForShifts.rotateShifts(shifts4, 5);
//      assert false;
//          } catch (IllegalArgumentException e) {
//    assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//    ArrayRotatorForShifts.rotateShifts(null, 5);
//      assert false;
//          } catch (IllegalArgumentException e) {
//    assert true;
//    }
//// TEST_END
//}

