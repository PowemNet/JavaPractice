package com.powem.inv.ungrouped;
//original[medium]: https://leetcode.com/problems/word-break/

//Write a function called isConcatenated which returns true if a given list of words
//can be derived from a single string (which means that concatenation was used to generate the String).
//Note: not all the words in the input list need to be in the concatenated String.
//example1: given a an input list: ["hello", "invinsible"], and input string s: "helloinvinsible", the function should return
//true.
//example2: given a an input list: ["love", "food"], and input string s: "lovefoodlove", the function should return
//true.
//example3: given a an input list: ["love", "foo"], and input string s: "lovefoodlove", the function should return
//false


import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class ConcatenateInvestigation {

  public static boolean isConcatenated(List<String> wordList, String concatenatedString) {
    if (wordList == null || concatenatedString == null) {
      throw new IllegalArgumentException("Neither input can be null");
    }
    Set<String> words = new HashSet<>(wordList);
    Queue<Integer> queue = new LinkedList<>();
    boolean[] seen = new boolean[concatenatedString.length() + 1];
    queue.add(0);

    while (!queue.isEmpty()) {
      int start = queue.remove();
      if (start == concatenatedString.length()) {
        return true;
      }

      for (int end = start + 1; end <= concatenatedString.length(); end++) {
        if (seen[end]) {
          continue;
        }

        if (words.contains(concatenatedString.substring(start, end))) {
          queue.add(end);
          seen[end] = true;
        }
      }
    }

    return false;
  }
}

//public static void tests() {
//
//  // TEST
//  assert !ConcatenateInvestigation.isConcatenated(List.of(), "helloinvinsible");
//  // TEST_END
//
//  // TEST
//  assert ConcatenateInvestigation.isConcatenated(List.of(), "");
//  // TEST_END
//
//  // TEST
//  try {
//    ConcatenateInvestigation.isConcatenated(null, "invinsible");
//    assert false;
//  } catch (IllegalArgumentException e) {
//    assert e.getMessage() == "Neither input can be null";
//  }
//  // TEST_END
//
//  // TEST
//  try {
//    ConcatenateInvestigation.isConcatenated(List.of(), null);
//    assert false;
//  } catch (IllegalArgumentException e) {
//    assert e.getMessage() == "Neither input can be null";
//  }
//  // TEST_END
//
//  // TEST
//  assert ConcatenateInvestigation.isConcatenated(List.of("hello", "invinsible"), "helloinvinsible");
//  // TEST_END
//
//  // TEST
//  assert ConcatenateInvestigation.isConcatenated(List.of("love", "food"), "lovefoodlove");
//  // TEST_END
//
//  // TEST
//  assert !ConcatenateInvestigation.isConcatenated(List.of("love", "foo"), "lovefoodlove");
//  // TEST_END
//}


