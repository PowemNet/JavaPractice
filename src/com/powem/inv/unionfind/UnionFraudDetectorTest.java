package com.powem.inv.unionfind;

public class UnionFraudDetectorTest {

  public static void tests() {
    // TEST
    try {
      int[][] combinations = {{1, 1, 0}};
      UnionFraudDetector.isFraudDetected(combinations, 2);
      assert false;
    } catch (IllegalArgumentException e) {
      assert e.getMessage().equals("n must be greater or equal to 3");
    }
    // TEST_END

    // TEST
    try {
      int[][] combinationsWithNegativeN = {{1, 1, 0}};
      UnionFraudDetector.isFraudDetected(combinationsWithNegativeN, -2);
      assert false;
    } catch (IllegalArgumentException e) {
      assert e.getMessage().equals("n must be greater or equal to 3");
    }
    // TEST_END

    // TEST
    try {
      int[][] nullCombinations = null;
      UnionFraudDetector.isFraudDetected(nullCombinations, 4);
      assert false;
    } catch (IllegalArgumentException e) {
      assert e.getMessage().equals("Combinations must not be null or empty");
    }
    // TEST_END

    // TEST
    try {
      int[][] emptyCombinations = {};
      UnionFraudDetector.isFraudDetected(emptyCombinations, 4);
      assert false;
    } catch (IllegalArgumentException e) {
      assert e.getMessage().equals("Combinations must not be null or empty");
    }
    // TEST_END

    // TEST
    int[][] combinationsWithoutFraud = {{1, 0, 1}, {2, 1, 2}, {3, 2, 4}};
    assert !UnionFraudDetector.isFraudDetected(combinationsWithoutFraud, 5);
    // TEST_END

    // TEST
    int[][] combinationsWithFraud = {{1, 0, 1}, {2, 1, 2}, {3, 3, 1}, {4, 2, 3}};
    assert UnionFraudDetector.isFraudDetected(combinationsWithFraud, 4);
    // TEST_END

    // TEST
    int[][] combinationsWithFraudButNotSorted = {{1, 0, 1}, {3, 3, 1}, {2, 1, 2}, {4, 2, 3}};
    assert UnionFraudDetector.isFraudDetected(combinationsWithFraudButNotSorted, 4);
    // TEST_END
  }
}