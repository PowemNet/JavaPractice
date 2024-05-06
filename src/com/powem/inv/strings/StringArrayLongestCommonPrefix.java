package com.powem.inv.strings;

//Longest Common Prefix

//Implement a function called longestCommonPrefix(String[] strs) that returns the longest common prefix string present in the array,
//given an array of mutile filenames. The function will help in grouping files under common categories based on their names.
//If there is no common prefix, return an empty string.
//
//    Example:
//
//Input: ["magnesium","maginificent","maggot"]
//Output: "mag"
//
//Input: ["dog","cow","car"]
//Output: ""
//    (There is no common prefix among the input strings.)


public class StringArrayLongestCommonPrefix {
  public static String longestCommonPrefix(String[] strs) {
    if (strs == null) {
      throw new IllegalArgumentException("Invalid input");
    }
    if (strs.length == 0) {
      return "";
    }
    String prefix = strs[0];
    for (int i = 1; i < strs.length; i++) {
      while (strs[i].indexOf(prefix) != 0) {
        prefix = prefix.substring(0, prefix.length() - 1);
        if (prefix.isEmpty()) {
          return "";
        }
      }
    }
    return prefix;
  }
}

//public static void tests() {
//public static void main(String[] args) {
//// TEST
//    assert StringArrayLongestCommonPrefix.longestCommonPrefix(new String[]{"magnesium", "maginificent", "maggot"}).equals("mag");
//// TEST_END
//
//// TEST
//    assert StringArrayLongestCommonPrefix.longestCommonPrefix(new String[]{"dog", "cow", "car"}).equals("");
//// TEST_END
//
//// TEST
//    assert StringArrayLongestCommonPrefix.longestCommonPrefix(new String[]{""}).equals("");
//// TEST_END
//
//// TEST
//    try {
//        StringArrayLongestCommonPrefix.longestCommonPrefix(null);
//      assert false;
//          } catch (IllegalArgumentException e) {
//    assert true;
//    }
//// TEST_END
//}
//}

