package com.powem.inv.strings;

import java.util.HashMap;
import java.util.Map;

//A real-time messaging application needs to know the longest substring of unique characters within a message,
//as this could highlight the most informationally dense part of the conversation.
//Create a function called longestUniqueSubstring(String s) that returns the length of the longest substring without
//repeating characters.
//
//Example:
//
//Input: "zefzefbb"
//Output: 4 ("zefb" is the longest substring without repeating characters.)
//
//Input: "zzzz"
//Output: 1 (The longest substring without repeating characters is "z".)
public class SubStringUniqueCharacters {
  public static int longestUniqueSubstring(String s) {
    if (s == null) {
      throw new IllegalArgumentException("Input cannot be null");
    }
    int maxLength = 0;
    Map<Character, Integer> map = new HashMap<>();

    for (int i = 0, j = 0; j < s.length(); j++) {
      char c = s.charAt(j);
      if (map.containsKey(c)) {
        i = Math.max(map.get(c) + 1, i);
      }
      map.put(c, j);
      maxLength = Math.max(maxLength, j - i + 1);
    }

    return maxLength;
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

