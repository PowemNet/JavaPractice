package com.powem.inv.dynamicprogramming;

//You are tasked with designing a system that calculates the different ways to
//combine given transaction processing fees to exactly match a specified maximum
//transaction fee cap. Each fee can be used any number of times. The system should
//return the total number of unique combinations that achieve this exact sum.
//
//Example:
//
//Input: Processing Fees = [1, 2, 5], Fee Cap = 5
//Output: 4
//Explanation: The combinations that sum up to 5 are:
//    [1, 1, 1, 1, 1]
//    [1, 1, 1, 2]
//    [1, 2, 2]
//    [5]

public class TransactionFeeCombinations {
  public static int countCombinations(int[] fees, int cap) {
    if (fees == null || cap < 0) {
      throw new IllegalArgumentException("Invalid fees or cap");
    }
    int[] feesMatrix = new int[cap + 1];
    feesMatrix[0] = 1;

    for (int fee : fees) {
      for (int j = fee; j <= cap; j++) {
        feesMatrix[j] += feesMatrix[j - fee];
      }
    }
    return feesMatrix[cap];
  }
}

//public static void tests() {
//// TEST
//int[] fees1 = {1, 2, 5};
//    assert TransactionFeeCombinations.countCombinations(fees1, 5) == 4;
//// TEST_END
//
//// TEST
//int[] fees2 = {1};
//    assert TransactionFeeCombinations.countCombinations(fees2, 5) == 1;
//// TEST_END
//
//// TEST
//int[] fees3 = {};
//    assert TransactionFeeCombinations.countCombinations(fees3, 5) == 0;
//    // TEST_END
//
//    // TEST
//    try {
//    TransactionFeeCombinations.countCombinations(null, 1);
//      assert false;
//          } catch (IllegalArgumentException e) {
//    assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//    TransactionFeeCombinations.countCombinations(fees2, -1);
//      assert false;
//          } catch (IllegalArgumentException e) {
//    assert true;
//    }
//// TEST_END
//}
