package com.powem.inv.strings;

//Validate String with Allowed Characters

//To prevent SQL injection or other forms of input tampering, a system needs a function
//    isValidInput(String input, String allowed) which checks whether all characters in the input string
//    are found in the allowed character string. Use a HashSet in the implementation.
//
//    Example:
//
//    Input: input = "test123", allowed = "abcdefghijklmnopqrstuvwxyz1234567890"
//    Output: true (All characters in "test123" are allowed.)
//
//    Input: input = "test#123", allowed = "abcdefghijklmnopqrstuvwxyz1234567890"
//    Output: false (# is not an allowed character.)


import java.util.HashSet;
import java.util.Set;

public class InputValidator {
  public static boolean isValidInput(String input, String allowed) {
    if (input == null || allowed == null) {
      throw new IllegalArgumentException("Invalid input");
    }
    Set<Character> allowedSet = new HashSet<>();
    for (char c : allowed.toCharArray()) {
      allowedSet.add(c);
    }

    for (char c : input.toCharArray()) {
      if (!allowedSet.contains(c)) {
        return false;
      }
    }
    return true;
  }
}

//public static void tests() {
//public static void main(String[] args) {
//  // TEST
//  assert InputValidator.isValidInput("test123", "abcdefghijklmnopqrstuvwxyz1234567890");
//  // TEST_END
//
//  // TEST
//  assert !InputValidator.isValidInput("test#123", "abcdefghijklmnopqrstuvwxyz1234567890");
//  // TEST_END
//
//  // TEST
//  assert InputValidator.isValidInput("", "abcdefghijklmnopqrstuvwxyz1234567890");
//  // TEST_END
//
//  // TEST
//  assert InputValidator.isValidInput("", "");
//  // TEST_END
//
//  // TEST
//  try {
//    InputValidator.isValidInput(null, "abcdefghijklmnopqrstuvwxyz1234567890");
//    assert false;
//  } catch (IllegalArgumentException e) {
//    assert true;
//  }
//  // TEST_END
//
//  // TEST
//  try {
//    InputValidator.isValidInput("", null);
//    assert false;
//  } catch (IllegalArgumentException e) {
//    assert true;
//  }
//  // TEST_END
//}
//}

