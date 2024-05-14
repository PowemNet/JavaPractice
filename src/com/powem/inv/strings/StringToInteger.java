package com.powem.inv.strings;

//Anagram Grouping

//A financial system takes user input but needs to sanitise it before processing it.
//Create a function extractIntegerFromUserInput(String str) which converts a string to an integer.
//The function should discard as many whitespace characters as necessary until the
//first non-whitespace character is found. Then, starting from this character,
//take an optional initial plus or minus sign followed by as many numerical digits
//as possible which form a valid integer, and interpret them as a numerical value.
//
//if no valid integer is found, or if the input string is null, the function should return 0.
//
//Example:
//
//Input: " -23"
//Output: -23
//
//Input: "some text 2"
//Output: 0
//
//Input: "2 some text"
//Output: 2
//
//Input: "-891283472332"
//Output: -2147483648

public class StringToInteger {
  public static int extractIntegerFromUserInput(String input) {
    if(input == null) {
      return 0;
    }
    int index = 0, sign = 1, total = 0;
    input = input.trim();
    if (input.isEmpty()) return 0;

    if (input.charAt(index) == '+' || input.charAt(index) == '-') {
      sign = input.charAt(index) == '+' ? 1 : -1;
      index++;
    }

    while (index < input.length()) {
      int digit = input.charAt(index) - '0';
      if (digit < 0 || digit > 9) break;

      if (total > Integer.MAX_VALUE / 10 ||
          (total == Integer.MAX_VALUE / 10 && digit > Integer.MAX_VALUE % 10)) {
        return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
      }

      total = 10 * total + digit;
      index++;
    }
    return total * sign;
  }
}

//public class Main {
//  public static void main(String[] args) {
//    // TEST
//    assert StringToInteger.extractIntegerFromUserInput("   ") == 0;
//    // TEST_END
//
//    // TEST
//    assert StringToInteger.extractIntegerFromUserInput(null) == 0;
//    // TEST_END
//
//    // TEST
//    assert StringToInteger.extractIntegerFromUserInput("   -23") == -23;
//    // TEST_END
//
//    // TEST
//    assert StringToInteger.extractIntegerFromUserInput("some text 2") == 0;
//    // TEST_END
//
//    // TEST
//    assert StringToInteger.extractIntegerFromUserInput("2 some text") == 2;
//    // TEST_END
//
//    // TEST
//    assert StringToInteger.extractIntegerFromUserInput("2 some text 2") == 2;
//    // TEST_END
//
//    // TEST
//    assert StringToInteger.extractIntegerFromUserInput("-891283472332") == -2147483648;
//    // TEST_END
//  }
//}

