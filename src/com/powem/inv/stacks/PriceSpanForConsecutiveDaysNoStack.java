package com.powem.inv.stacks;
//StockSpan
//original[medium]: https://leetcode.com/problems/online-stock-span/description/

//Solve this problem without using the Stack data structure.
//Given an array of integers which represent the price of a given commodity on each day,
//create a function called findDaysSpannedForPrice which finds how many days for which the price
//has been less than or equal to the price today.
//example, given the price in the past four days: [7,2,1,2], if today's price is 3, then the output
//is 4 because the price has been <= 3 for four days (2,1,2 and 3).

public class PriceSpanForConsecutiveDaysNoStack {

  public static int findDaysSpannedForPrice(int[] priceHistory, int priceToday) {
    if (priceHistory == null || priceHistory.length == 0) {
      return 1;
    }
    int days = 0;
    for (int i = 0; i < priceHistory.length; i++) {
      if (priceHistory[i] <= priceToday) {
        days++;
      } else {
        days = 0;
      }
    }
    return days + 1;
  }
}

//public static void tests() {
//  // TEST
//  assert PriceSpanForConsecutiveDaysMonotonicStack.findDaysSpannedForPrice(null, 4) == 1;
//  // TEST_END
//
//  // TEST
//  assert PriceSpanForConsecutiveDaysMonotonicStack.findDaysSpannedForPrice(new int[]{}, 4) == 1;
//  // TEST_END
//
//  // TEST
//  assert PriceSpanForConsecutiveDaysMonotonicStack.findDaysSpannedForPrice(new int[]{7, 1, 1, 2}, 4) == 4;
//  // TEST_END
//
//  // TEST
//  assert PriceSpanForConsecutiveDaysMonotonicStack.findDaysSpannedForPrice(new int[]{1, 2, 3, 4, 5}, 4) == 1;
//  // TEST_END
//}

