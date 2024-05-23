package com.powem.inv.strings;

//Decode a Specially Encoded String

//Given a messaging system where strings have been encoded where each segment of the string starts with the
//length of the word followed by a ‘#’ and then the word itself. Implement a method  decode(String s) to decode
//this encoded string back into a list of the original strings.
//
//Example:
//
//Decode Input: “5#Hello5#World”
//Decode Output: [“Hello”, “World”]


import java.util.ArrayList;
import java.util.List;

public class StringDecoder {
  public static List<String> decode(String s) {
    List<String> decoded = new ArrayList<>();
    int i = 0;
    while (i < s.length()) {
      int hashPos = s.indexOf('#', i);
      if (hashPos == -1) {
        throw new IllegalArgumentException("Invalid encoding: '#' not found.");
      }
      int size;
      try {
        size = Integer.parseInt(s.substring(i, hashPos));
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Invalid encoding: Expected a number before '#'.", e);
      }

      if (hashPos + 1 + size > s.length()) {
        throw new IllegalArgumentException("Invalid encoding: Specified length " + size +
            " exceeds remaining string length.");
      }

      String word = s.substring(hashPos + 1, hashPos + 1 + size);
      if (word.length() != size) {
        throw new IllegalArgumentException("Invalid encoding: Specified length " + size +
            " does not match the length of the word '" + word + "'.");
      }
      decoded.add(word);
      i = hashPos + 1 + size;
    }
    return decoded;
  }
}

//import java.util.List;
//
//public class Main {
//  public static void main(String[] args) {
//    // TEST
//    assert StringDecoder.decode("5#Hello5#World").equals(List.of("Hello", "World"));
//    // TEST_END
//
//    // TEST
//    assert StringDecoder.decode("1#H5#World").equals(List.of("H", "World"));
//    // TEST_END
//
//    // TEST
//    try {
//      StringDecoder.decode("1#Hello5#World");
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      StringDecoder.decode("1Hello5");
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      StringDecoder.decode("1#Hello5");
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      StringDecoder.decode("1#Hello5");
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      StringDecoder.decode("#5Hell");
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//  }
//}
