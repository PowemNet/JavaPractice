package com.powem.inv.arrays;

//A data transmissions system wants to check that no packets are missing from data received.
//Given an array of integers (each int represents a unique data packet) where each number from 1 to n
//appears once except for one number that is missing, create a function called findMissingNumber(int[] numbers)
// which finds and returns the missing number.
// The implementation should use Gauss' arithmetic series sum formula to find the missing number, ensuring a linear time
// complexity and constant space usage.
//
// Example:
//
//Input: [3, 7, 1, 2, 8, 4, 5]
//Output: 6
//Explanation: Numbers from 1 to 8 are supposed to be in the array, but 6 is missing.

public class MissingNumberFinder {
  public static int findMissingNumber(int[] numbers) {
    if (numbers == null || numbers.length == 0) {
      throw new IllegalArgumentException("Input cannot be null or empty");
    }
    int n = numbers.length + 1;
    int expectedSum = n * (n + 1) / 2;
    int actualSum = 0;
    for (int num : numbers) {
      actualSum += num;
    }
    return expectedSum - actualSum;
  }
}

//public static void tests() {
//// TEST
//int[] numbers1 = {3, 7, 1, 2, 8, 4, 5};
//    assert MissingNumberFinder.findMissingNumber(numbers1) == 6;
//// TEST_END
//
//// TEST
//int[] numbers2 = {1, 2, 3, 5};
//    assert MissingNumberFinder.findMissingNumber(numbers2) == 4;
//// TEST_END
//
//// TEST
//int[] numbers3 = {2, 3, 1, 5};
//    assert MissingNumberFinder.findMissingNumber(numbers3) == 4;
//    // TEST_END
//
//    // TEST
//    try{
//int[] numbers4 = {};
//      MissingNumberFinder.findMissingNumber(numbers4);
//      assert false;
//          } catch (IllegalArgumentException e){
//    assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try{
//    MissingNumberFinder.findMissingNumber(null);
//      assert false;
//          } catch (IllegalArgumentException e){
//    assert true;
//    }
//// TEST_END
//}

