package com.powem.inv.strings;

//Reverse Words in a String

//An online publishing systems wants to reverse headlines to create catch phrases.
//    Implement a function reverseWords(String s) that returns a string where the words (sequences of characters separated by spaces)
//    are reversed in order.
//
//    Example:
//
//    Input: "I am hungry"
//    Output: "hungry I am"


public class WordsReverser {
  public static String reverseWords(String s) {
    if (s == null) {
      throw new IllegalArgumentException("Inout cannot be null");
    }
    String[] words = s.trim().split("\\s+");
    StringBuilder reversed = new StringBuilder();
    for (int i = words.length - 1; i >= 0; i--) {
      reversed.append(words[i]);
      if (i > 0) {
        reversed.append(" ");
      }
    }
    return reversed.toString();
  }
}

//public static void tests() {
//public static void main(String[] args) {
//// TEST
//    assert WordsReverser.reverseWords("I am hungry").equals("hungry am I");
//// TEST_END
//
//// TEST
//    assert WordsReverser.reverseWords("   Hello World  ").equals("World Hello");
//// TEST_END
//
//// TEST
//    assert WordsReverser.reverseWords("Hello      World").equals("World Hello");
//// TEST_END
//
//// TEST
//    assert WordsReverser.reverseWords("").equals("");
//// TEST_END
//
//// TEST
//    try {
//        WordsReverser.reverseWords(null);
//      assert false;
//          } catch (IllegalArgumentException e) {
//    assert true;
//    }
//// TEST_END
//}
//}

