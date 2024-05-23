package com.powem.inv.strings;

//Remove All Adjacent Duplicates in String

//In an application for editing and formatting text, a feature is needed to clean up
//user-submitted content by removing consecutive, repeating characters. Implement a
//    function that removes all adjacent duplicates from a string recursively until no
//    adjacent duplicates are left.
//
//    The function should be called removeDuplicates(String s) and it should returns
//    a string after all consecutive duplicates have been removed.
//
//    Example:
//
//    Input: "deedfg"
//    Output: "fg" (The pairs "ee" and "dd" are removed, leaving "fg".)


public class RemoveStringDuplicates {
  public static String removeDuplicates(String s) {
    if (s == null) {
      throw new IllegalArgumentException("Input cannot be null");
    }
    StringBuilder sb = new StringBuilder();
    for (char c : s.toCharArray()) {
      int length = sb.length();
      if (length > 0 && sb.charAt(length - 1) == c) {
        sb.deleteCharAt(length - 1);
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }
}

//public class Main {
//  public static void main(String[] args) {
//    // TEST
//    assert RemoveStringDuplicates.removeDuplicates("deedfg").equals("fg");
//    // TEST_END
//
//    // TEST
//    assert RemoveStringDuplicates.removeDuplicates("azxxzy").equals("ay");
//    // TEST_END
//
//    // TEST
//    assert RemoveStringDuplicates.removeDuplicates("").equals("");
//    // TEST_END
//
//    // TEST
//    try {
//      RemoveStringDuplicates.removeDuplicates(null);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//  }
//}

