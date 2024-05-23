package com.powem.inv.strings;

//String Tokenizer

//Create a function called  tokenizeWords(String s, String delimiters) which splits the
//string s into tokens based on the characters found in the delimiters string.
//Example
//Input: s = "I, want! to Sleep.", delimiters = " ,!."
//Output: ["I", "want", "to", "Sleep"]
//Constraint: The inputs cannot be null or empty

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StringTokenizer {
  public static String[] tokenizeWords(String s, String delimiters) {
    if (s == null || s.isEmpty() || delimiters == null || delimiters.isEmpty()) {
      throw new IllegalArgumentException("Invalid input");
    }

    Set<Character> delimiterSet = new HashSet<>();
    for (char c : delimiters.toCharArray()) {
      delimiterSet.add(c);
    }

    List<String> tokens = new ArrayList<>();
    StringBuilder token = new StringBuilder();

    for (char c : s.toCharArray()) {
      if (delimiterSet.contains(c)) {
        if (token.length() > 0) {
          tokens.add(token.toString());
          token = new StringBuilder();
        }
      } else {
        token.append(c);
      }
    }

    if (token.length() > 0) {
      tokens.add(token.toString());
    }

    return tokens.toArray(new String[0]);
  }
}

//import java.util.Arrays;
//import java.util.List;
//
//public class Main {
//  public static void main(String[] args) {
//    // TEST
//    List<String> result1 = Arrays.asList(StringTokenizer.tokenizeWords("I, want! to Sleep.", " ,!."));
//    assert result1.size() == 4;
//    // TEST_END
//
//    // TEST
//    assert result1.get(0).equals("I");
//    // TEST_END
//
//    // TEST
//    assert result1.get(1).equals("want");
//    // TEST_END
//
//    // TEST
//    assert result1.get(2).equals("to");
//    // TEST_END
//
//    // TEST
//    assert result1.get(3).equals("Sleep");
//    // TEST_END
//
//    // TEST
//    try {
//      StringTokenizer.tokenizeWords(null, " ,!.");
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      StringTokenizer.tokenizeWords("", " ,!.");
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      StringTokenizer.tokenizeWords(null, null);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//  }
//}

