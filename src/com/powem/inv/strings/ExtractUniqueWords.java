package com.powem.inv.strings;

//Extract Unique Words from a Sentence


//To indexing content in a text analysis tool, create a function called extractUniqueWords(String sentence) which
//returns a list of unique words from the given sentence. Words should be considered case-insensitive, and the
//function should handle punctuation and whitespace effectively (i.e. they should be ignored).
//
//Example:
//
//Input: “Hello, hello? World! This is a test. A TEST.”
//Output: [“hello”, “world”, “this”, “is”, “a”, “test”]

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ExtractUniqueWords {
  public static List<String> extractUniqueWords(String sentence) {
    if (sentence == null || sentence.isEmpty()) {
      throw new IllegalArgumentException("Input sentence is null or empty.");
    }

    sentence = sentence.toLowerCase();
    sentence = sentence.replaceAll("[^a-zA-Z0-9\\s]", " ");
    String[] words = sentence.split("\\s+");
    Set<String> uniqueWords = new LinkedHashSet<>(Arrays.asList(words));
    return new ArrayList<>(uniqueWords);
  }
}

//import java.util.List;
//
//public class Main {
//  public static void main(String[] args) {
//    // TEST
//    assert ExtractUniqueWords.extractUniqueWords("Hello, hello? World! This is a test. A TEST.")
//        .equals(List.of("hello", "world", "this", "is", "a", "test"));
//    // TEST_END
//
//    // TEST
//    assert ExtractUniqueWords.extractUniqueWords("Hello,     .p").equals(List.of("hello", "p"));
//    // TEST_END
//
//    // TEST
//    assert ExtractUniqueWords.extractUniqueWords("Hello123,  World!").equals(List.of("hello123", "world"));
//    // TEST_END
//
//    // TEST
//    assert ExtractUniqueWords.extractUniqueWords("123,  World!").equals(List.of("123", "world"));
//    // TEST_END
//
//    // TEST
//    try {
//      ExtractUniqueWords.extractUniqueWords("");
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      ExtractUniqueWords.extractUniqueWords(null);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//  }
//}
