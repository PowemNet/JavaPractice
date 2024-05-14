package com.powem.inv.strings;

//Anagram Grouping

//Create a function which groups strings into sets of anagrams. Each group should
//    contain strings that are anagrams of each other.
//
//    The function is groupAnagrams(String[] strs) and it returns a list of lists,
//    where each sublist contains anagrams grouped together.
//
//    Example:
//
//    Input: ["eat", "tea", "tan", "ate", "nat", "bat"]
//    Output: [["eat", "tea", "ate"], ["tan", "nat"], ["bat"]]


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnagramGrouper {
  public static List<List<String>> groupAnagrams(String[] strs) {
    if (strs == null || strs.length == 0) {
      throw new IllegalArgumentException("Invalid input");
    }
    Map<String, List<String>> map = new HashMap<>();
    for (String s : strs) {
      char[] characters = s.toCharArray();
      Arrays.sort(characters);
      String sorted = new String(characters);
      if (!map.containsKey(sorted)) map.put(sorted, new ArrayList<>());
      map.get(sorted).add(s);
    }
    return new ArrayList<>(map.values());
  }
}

//public static void main(String[] args) {
//  // TEST
//  List<List<String>> result1 =  AnagramGrouper.groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"});
//  assert result1.size() == 3;
//  // TEST_END
//
//  // TEST
//  assert result1.get(0).size() == 3;
//  // TEST_END
//
//  // TEST
//  assert result1.get(0).get(0) == "eat";
//  // TEST_END
//
//  // TEST
//  try {
//    AnagramGrouper.groupAnagrams(null);
//    assert false;
//  } catch (IllegalArgumentException e) {
//    assert true;
//  }
//  // TEST_END
//
//  // TEST
//  List<List<String>> result2 =  AnagramGrouper.groupAnagrams(new String[]{"eat", "eat"});
//  assert result2.size() == 1;
//  // TEST_END
//
//  // TEST
//  assert result2.get(0).size() == 2;
//  // TEST_END
//}

