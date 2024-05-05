package com.powem.inv.hashtables;

//Frequency Count
//
//In a financial management system, transfer descriptions are tagged with various types for better categorization
//and searchability. For efficient search operations, the system needs a way to quickly determine the
//frequency of each tag across multiple transfer descriptions.
//Task:
//Implement a function, countTypeFrequency(List<String> transferDescriptions, List <String> validTypes),
// that returns a hash table where each
//key is a unique type and the corresponding value is the number of times that type appears across all
//given transfer descriptions, given the validTypes string list. Types should be considered case-insensitive,
// and are also Strings.
//
//Example:
//Input: transferDescriptions = ["This is a Check transfer", "A Check", "A  Wire transfer", "check transfer", "random"]
//        validTypes = ["check", "wire"]
//Output: {"check": 3, "wire": 1}


import java.util.HashMap;
import java.util.List;

public class TransfersFrequencyCount {
  public static HashMap<String, Integer> countTypeFrequency(List<String> descriptions, List<String> validTypes) {
    if (descriptions == null || descriptions.isEmpty() || validTypes == null || validTypes.isEmpty()) {
      throw new IllegalArgumentException("Inputs cannot be null or empty");
    }
    HashMap<String, Integer> frequencyMap = new HashMap<>();
    for (String doc : descriptions) {
      String[] words = doc.toLowerCase().split("\\s+");
      for (String word : words) {
        if (validTypes.contains(word)) {
          frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
        }
      }
    }
    return frequencyMap;
  }
}

//public static void tests() {
//// TEST
//List<String> transferDescriptions1 = List.of("This is a Check transfer", "A Check", "A  Wire transfer", "check transfer");
//List<String> validTypes1 = List.of("check", "wire");
//HashMap<String, Integer> result1 = TransfersFrequencyCount.countTypeFrequency(transferDescriptions1, validTypes1);
//    assert result1.containsKey("check");
//    assert result1.get("check") == 3;
//    assert result1.containsKey("wire");
//    assert result1.get("wire") == 1;
//// TEST_END
//
//// TEST
//List<String> validTypes2 = List.of("check", "wire");
//    try {
//        TransfersFrequencyCount.countTypeFrequency(null, validTypes2);
//      assert false;
//          } catch (IllegalArgumentException e) {
//    assert true;
//    }
//// TEST_END
//
//// TEST
//List<String> transferDescriptions3 = List.of("This is a Check transfer", "A Check", "A  Wire transfer", "check transfer");
//    try {
//        TransfersFrequencyCount.countTypeFrequency(transferDescriptions3, null);
//      assert false;
//          } catch (IllegalArgumentException e) {
//    assert true;
//    }
//// TEST_END
//}
