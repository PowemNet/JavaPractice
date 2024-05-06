package com.powem.inv.strings;

//Frequency Sorting of Characters

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class StringAlphabeticalFrequencySorter {
  public static String frequencySort(String s) {
    if (s == null) {
      throw new IllegalArgumentException("Input cannot be null");
    }
    Map<Character, Integer> frequencyMap = new HashMap<>();
    for (char c : s.toCharArray()) {
      frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
    }

    List<Entry<Character, Integer>> list = new ArrayList<>(frequencyMap.entrySet());

    Collections.sort(list, (a, b) -> {
      if (a.getValue().equals(b.getValue())) {
        return a.getKey().compareTo(b.getKey());
      } else {
        return b.getValue() - a.getValue();
      }
    });

    StringBuilder sb = new StringBuilder();
    for (Map.Entry<Character, Integer> entry : list) {
      for (int i = 0; i < entry.getValue(); i++) {
        sb.append(entry.getKey());
      }
    }
    return sb.toString();
  }
}

//public static void tests() {
//// TEST
//      assert StringAlphabeticalFrequencySorter.frequencySort("flee").equals("eefl");
//// TEST_END
//
//// TEST
//      assert StringAlphabeticalFrequencySorter.frequencySort("tree").equals("eert");
//// TEST_END
//
//// TEST
//      assert StringAlphabeticalFrequencySorter.frequencySort("Abb").equals("bbA");
//// TEST_END
//
//// TEST
//      try {
//          StringAlphabeticalFrequencySorter.frequencySort(null);
//        assert false;
//            } catch (IllegalArgumentException e) {
//    assert true;
//    }
//// TEST_END
//}

