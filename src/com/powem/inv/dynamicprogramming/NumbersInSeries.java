package com.powem.inv.dynamicprogramming;

//A queue system wants to be able to anticipate the length of a message so that it can preallocate
//memory resources before receiving a message. The messages are delivered with a length
//parameter, which follows a sequence in the format:
//F(0) = 0, F(1) = 1
//For n > 1, F(n) = F(n-1) + F(n-2)
//Create a function called findNextValueInSequence which use dynamic programming to calculate the next
//length value of the next message, given the index of the next value.
//For example findNextValueInSequence(3) should return 2, and findNextValueInSequence(6) should return 8

public class NumbersInSeries {
  public static int findNextValueInSequence(int n) {
    if (n < 0) {
      throw new IllegalArgumentException("Number cannot be negative");
    }

    if (n == 0) {
      return 0;
    }

    int[] numbsTable = new int[n + 1];
    numbsTable[0] = 0;
    numbsTable[1] = 1;

    for (int i = 2; i <= n; i++) {
      numbsTable[i] = numbsTable[i - 1] + numbsTable[i - 2];
    }
    return numbsTable[n];
  }
}

//public static void tests() {
//// TEST
//    assert NumbersInSeries.findNextValueInSequence(0) == 0;
//    // TEST_END
//
//    // TEST
//    assert NumbersInSeries.findNextValueInSequence(1) == 1;
//    // TEST_END
//
//    // TEST
//    assert NumbersInSeries.findNextValueInSequence(2) == 1;
//    // TEST_END
//
//    // TEST
//    assert NumbersInSeries.findNextValueInSequence(3) == 2;
//    // TEST_END
//
//    // TEST
//    assert NumbersInSeries.findNextValueInSequence(4) == 3;
//    // TEST_END
//
//    // TEST
//    try {
//    NumbersInSeries.findNextValueInSequence(-18);
//      assert false;
//          } catch (IllegalArgumentException e) {
//    assert true;
//    }
//// TEST_END
//}
