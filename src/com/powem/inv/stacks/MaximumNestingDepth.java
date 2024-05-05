package com.powem.inv.stacks;
//
//    A mathematical calculator system needs to determine the depth of parentheses.
//    Create a method maxNestingDepth(String expression) that returns the maximum nesting
//    depth of the parentheses in a given mathematical expression.
//
//    The nesting depth is the maximum number of pairs of parentheses surrounding any part of the expression.
//    If the expression contains unbalanced parentheses, the method should return  an error.
//
//    Example:
//
//    Input: expression = "1 + (2 * (3 + (4 / 2)))"
//    Output: 3 (The maximum depth is 3 layers deep)
//    Input: expression = "(1 + 2) + (3 * (4 - 5))"
//    Output: 2
//    Input: expression = "(1 + 3) + ((4 - 2)"
//    Output: Throw error (The expression is not balanced)

import java.util.Stack;

public class MaximumNestingDepth {
  public static int maxNestingDepth(String expression) {
    int currentDepth = 0;
    int maxDepth = 0;
    Stack<Character> stack = new Stack<>();

    for (char c : expression.toCharArray()) {
      if (c == '(') {
        stack.push(c);
        currentDepth++;
        maxDepth = Math.max(maxDepth, currentDepth);
      } else if (c == ')') {
        if (stack.isEmpty() || stack.pop() != '(') {
          throw new IllegalArgumentException("Unbalanced parentheses");
        }
        currentDepth--;
      }
    }

    if (!stack.isEmpty()) {
      throw new IllegalArgumentException("Unbalanced parentheses");
    }

    return maxDepth;
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

