package com.powem.inv.stacks;
//StockSpan
//original[medium]: https://leetcode.com/problems/online-stock-span/description/

//Given an array of integers which represent the price of a given commodity on each day,
//create a function called findDaysSpannedForPrice which finds how many days for which the price
//has been less than or equal to the price today.
//example, given the price in the past four days: [7,2,1,2], if today's price is 3, then the output
//is 4 because the price ha ben <= 3 for four days (2,1,2 and 3).

import java.util.Stack;

public class PriceSpanForConsecutiveDays {

  public static int findDaysSpannedForPrice(int[] priceHistory, int priceToday) {
    if(priceToday < 0) {
      throw new IllegalArgumentException("Price value cannot be negative");
    }

    if (priceHistory == null || priceHistory.length == 0) {
      return 1;
    }

    Stack<Integer> stack = new Stack<>();
    for (int p : priceHistory) {
      if(p < 0) {
        throw new IllegalArgumentException("Price value in priceHistory cannot be negative");
      }
      stack.push(p);
    }

    int days = 1;
    while (!stack.isEmpty() && stack.peek() <= priceToday) {
      days += 1;
      stack.pop();
    }
    return days;
  }
}

//public static void tests() {
// TEST
//    try {
//        PriceSpanForConsecutiveDays.findDaysSpannedForPrice(new int[]{7, 1, 1, 2}, -4);
//    assert false;
//    } catch (IllegalArgumentException e) {
//    assert e.getMessage() == "Price value cannot be negative";
//    }
//    // TEST_END
//    // TEST
//    try {
//    PriceSpanForConsecutiveDays.findDaysSpannedForPrice(new int[]{-7, 1, 1, -2}, 4);
//    assert false;
//    } catch (IllegalArgumentException e) {
//    assert e.getMessage() == "Price value in priceHistory cannot be negative";
//    }
//    // TEST_END
//
//    // TEST
//    assert PriceSpanForConsecutiveDays.findDaysSpannedForPrice(null, 4) == 1;
//    // TEST_END
//
//    // TEST
//    assert PriceSpanForConsecutiveDays.findDaysSpannedForPrice(new int[]{}, 4) == 1;
//    // TEST_END
//
//    // TEST
//    assert PriceSpanForConsecutiveDays.findDaysSpannedForPrice(new int[]{7, 1, 1, 2}, 4) == 4;
//    // TEST_END
//
//    // TEST
//    assert PriceSpanForConsecutiveDays.findDaysSpannedForPrice(new int[]{1, 2, 3, 4, 5}, 4) == 1;
//// TEST_END
//}

