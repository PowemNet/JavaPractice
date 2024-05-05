package com.powem.inv.ungrouped;
//original[easy]: https://leetcode.com/problems/single-number/description/

//You have a system which stores signature records between two people. signer1 and signer2.
//For each signer, a new record is stored in a database. You want to know which combinations of
//signer pairs have missing records.
//Each signer record is stored as a unique integer per doc. Return the doc with only
//one signer instead of 2.
//Create a function missingItemInPair which takes an array of integers and returns an integer.
//For example if the input is [1,1,2], return 2. doc 1 has both signature but doc 2 has only one.
//The input will always be an array of at least 3 integers, and output should be an integer.

import java.util.ArrayList;
import java.util.List;

public class MissingItemPair {

  public static int missingItemInPair(int[] records) {
    if (records == null || records.length == 0) {
      throw new IllegalArgumentException("Input cannot be empty or null");
    }

    if (records.length < 3) {
      throw new IllegalArgumentException("Input must be at least 3 items");
    }

    List<Integer> noDuplicateList = new ArrayList<>();

    for (int i : records) {
      if (!noDuplicateList.contains(i)) {
        noDuplicateList.add(i);
      } else {
        noDuplicateList.remove(Integer.valueOf(i));
      }
    }
    return noDuplicateList.get(0);
  }
}

//  public static void tests() {
//    // TEST
//    try {
//      MissingItemPair.missingItemInPair(new int[]{});
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert e.getMessage() == "Input cannot be empty or null";
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      MissingItemPair.missingItemInPair(null);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert e.getMessage() == "Input cannot be empty or null";
//    }
//    // TEST_END
//
//    // TEST
//    assert MissingItemPair.missingItemInPair(new int[]{1, 1, 2}) == 2;
//    // TEST_END
//
//    // TEST
//    assert MissingItemPair.missingItemInPair(new int[]{1, 1, 3, 3, 4}) == 4;
//    // TEST_END
//  }

