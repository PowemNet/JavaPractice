package com.powem.inv.strings;

//A UI system needs to verify if any permutation of a given string can form a palindrome so as to create identifiers
//that are visually symmetric, enhancing readability and memorability in UI design.
//
//Create a function called canFormPalindrome(String input) that checks whether any permutation of the characters
//in the input string can form a palindrome. The solution should
//efficiently handle this check with consideration for potential high-frequency
//usage in user interface checks.
//
//Example:
//
//Input: "aabb"
//Output: true (The string can be permuted to "abba" or "baab", both of which are palindromes.)
//
//Input: "abc"
//Output: false (No permutation of "abc" results in a palindrome.)

public class Palindrome {
  public static boolean canFormPalindrome(String input) {
    if (input == null) {
      return false;
    }
    int[] charCounts = new int[128];
    for (char c : input.toCharArray()) {
      charCounts[c]++;
    }

    int oddCount = 0;
    for (int count : charCounts) {
      if (count % 2 != 0) {
        oddCount++;
        if (oddCount > 1) {
          return false;
        }
      }
    }
    return true;
  }
}

//public static void tests() {
//// TEST
//    assert MaximumNestingDepth.maxNestingDepth("1 + (2 * (3 + (4 / 2)))") == 3;
//    // TEST_END
//
//    // TEST
//    assert MaximumNestingDepth.maxNestingDepth("(1 + 2) + (3 * (4 - 5))") == 2;
//    // TEST_END
//
//    // TEST
//    try {
//    MaximumNestingDepth.maxNestingDepth("(1 + 3) + ((4 - 2)");
//      assert false;
//          } catch (IllegalArgumentException e) {
//    assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//    MaximumNestingDepth.maxNestingDepth("(");
//      assert false;
//          } catch (IllegalArgumentException e) {
//    assert true;
//    }
//    // TEST_END
//    }
//}

