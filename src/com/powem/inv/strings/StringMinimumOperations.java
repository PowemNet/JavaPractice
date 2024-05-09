package com.powem.inv.strings;


//In an automated document editing tool, users frequently need to transform strings from one form to another,
//especially in templated content.
//Create a function minOperations(String source, String target) that calculates the minimum number of operations
//required to transform the source string into the target string.
//The function will determine the minimum number of operations needed to transform one string into another
//using only insertions, deletions, and substitutions of characters.
//
//
//Example:
//
//Input: source = "kitten", target = "sitting"
//Output: 3 (The operations are: substitute 'k' with 's', substitute 'e' with 'i', insert 'g' at the end.)
public class StringMinimumOperations {
  public static int minOperations(String source, String target) {
    if (source == null || target == null) {
      throw new IllegalArgumentException("Inputs cannot be null");
    }
    int m = source.length();
    int n = target.length();
    int[][] dp = new int[m + 1][n + 1];

    for (int i = 0; i <= m; i++) {
      for (int j = 0; j <= n; j++) {
        if (i == 0) {
          dp[i][j] = j;
        } else if (j == 0) {
          dp[i][j] = i;
        } else if (source.charAt(i - 1) == target.charAt(j - 1)) {
          dp[i][j] = dp[i - 1][j - 1];
        } else {
          dp[i][j] = 1 + Math.min(dp[i - 1][j - 1],
              Math.min(dp[i - 1][j],
                  dp[i][j - 1]));
        }
      }
    }
    return dp[m][n];
  }
}

//public static void tests() {
//// TEST
//    assert MaximumNestingDepth.maxNestingDepth("1 + (2 * (3 + (4 / 2)))") == 3;
//    // TEST_END
//
//    // TEST
//    assert MaximumNestingDepth.maxNestingDepth("(1 + 2) + (3 * (4 - 5))") == 2;
//    // TEST_END
//
//    // TEST
//    try {
//    MaximumNestingDepth.maxNestingDepth("(1 + 3) + ((4 - 2)");
//      assert false;
//          } catch (IllegalArgumentException e) {
//    assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//    MaximumNestingDepth.maxNestingDepth("(");
//      assert false;
//          } catch (IllegalArgumentException e) {
//    assert true;
//    }
//    // TEST_END
//    }
//}

