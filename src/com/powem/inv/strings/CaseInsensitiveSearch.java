package com.powem.inv.strings;

//Case Insensitive Search in a String

//In a document management system, users need to search for terms within documents
//regardless of case sensitivity so as to locate all instances of
//a word or phrase without missing any due to variations in capitalization.
//
//Create a function countCaseInsensitiveMatches(String text, String query) that
//returns the number of times a query appears in a text string, ignoring case differences.
//
//    Example:
//
//Input: text = "Invisible is so cool that you feel invisible when you think about it and all that invisibility.", query = "invisible"
//Output: 2 (The word "invisible" appears twice, ignoring c ase.)
//if the text or query is null, return 0
//if both text and query are empty, return 0

public class CaseInsensitiveSearch {
  public static int countCaseInsensitiveMatches(String text, String query) {
    if (text == null || query == null) {
      return 0;
    }

    if (text.isEmpty() || query.isEmpty()) {
      return 0;
    }
    String normalizedText = text.toLowerCase();
    String normalizedQuery = query.toLowerCase();

    int index = 0, count = 0;
    while (true) {
      index = normalizedText.indexOf(normalizedQuery, index);

      if (index == -1) {
        break;
      }
      count++;
      index += normalizedQuery.length();
    }

    return count;
  }
}

//public class Main {
//  public static void main(String[] args) {
//    // TEST
//    assert CaseInsensitiveSearch.countCaseInsensitiveMatches(
//        "Invisible is so cool that you feel invisible when you think about it and all that invisibility.", "invisible") == 2;
//    // TEST_END
//
//    // TEST
//    assert CaseInsensitiveSearch.countCaseInsensitiveMatches(
//        "New new new new.", "new") == 4;
//    // TEST_END
//
//    // TEST
//    assert CaseInsensitiveSearch.countCaseInsensitiveMatches(
//        "", "invisible") == 0;
//    // TEST_END
//
//    // TEST
//    assert CaseInsensitiveSearch.countCaseInsensitiveMatches(
//        "", null) == 0;
//    // TEST_END
//
//    // TEST
//    assert CaseInsensitiveSearch.countCaseInsensitiveMatches(
//        "", "") == 0;
//    // TEST_END
//
//    // TEST
//    assert CaseInsensitiveSearch.countCaseInsensitiveMatches(
//        "new", "") == 0;
//    // TEST_END
//  }
//}


