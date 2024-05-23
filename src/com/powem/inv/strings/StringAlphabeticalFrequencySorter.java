package com.powem.inv.strings;

//Frequency Sorting of Characters
//
//A text analysis tool needs to sort the characters of a string by their
//frequency in descending order. If multiple characters have the same frequency, they should be sorted in alphabetical
//order.
//
//Implement a function frequencySort(String s) that returns a string where the characters are sorted first by frequency
//(highest to lowest) and then by lexicographical order if frequencies are the same.
//
//Example:
//
//Input: "flee"
//Output: "eefl" (Both 'e' characters appear twice, and 'f' and 'l' each appear once, but 'e' comes
//before 'f' and 'l' in frequency and then alphabetically.)
//The input cannot be an empty String
//Strings which are capital letters should be treated differently from Strings which are small letter
//Also, The final output should preserve the original case sensitivity
//for example: Input: "Aabb" would have output "bbAa" because 'A' comes before 'a'


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class StringAlphabeticalFrequencySorter {
  public static String frequencySort(String input) {
    if (input == null || input.isEmpty()) {
      throw new IllegalArgumentException("Input cannot be null or empty");
    }
    Map<Character, Integer> frequencyMap = new HashMap<>();
    for (char c : input.toCharArray()) {
      frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
    }

    List<Entry<Character, Integer>> list = new ArrayList<>(frequencyMap.entrySet());

    Collections.sort(list, (string1, string2) -> {
      if (string1.getValue().equals(string2.getValue())) {
        return string1.getKey().compareTo(string2.getKey());
      } else {
        return string2.getValue() - string1.getValue();
      }
    });

    StringBuilder output = new StringBuilder();
    for (Map.Entry<Character, Integer> entry : list) {
      for (int i = 0; i < entry.getValue(); i++) {
        output.append(entry.getKey());
      }
    }
    return output.toString();
  }
}

//public class Main {
//  public static void main(String[] args) {
//    // TEST
//    assert StringAlphabeticalFrequencySorter.frequencySort("flee").equals("eefl");
//    // TEST_END
//
//    // TEST
//    assert StringAlphabeticalFrequencySorter.frequencySort("tree").equals("eert");
//    // TEST_END
//
//    // TEST
//    assert StringAlphabeticalFrequencySorter.frequencySort("Abb").equals("bbA");
//    // TEST_END
//
//    // TEST
//    assert StringAlphabeticalFrequencySorter.frequencySort("Aabb").equals("bbAa");
//    // TEST_END
//
//    // TEST
//    assert StringAlphabeticalFrequencySorter.frequencySort("Aa  bb").equals("  bbAa");
//    // TEST_END
//
//    // TEST
//    assert StringAlphabeticalFrequencySorter.frequencySort("1Aabb").equals("bb1Aa");
//    // TEST_END
//
//    // TEST
//    try {
//      StringAlphabeticalFrequencySorter.frequencySort(null);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      StringAlphabeticalFrequencySorter.frequencySort("");
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//  }
//}

