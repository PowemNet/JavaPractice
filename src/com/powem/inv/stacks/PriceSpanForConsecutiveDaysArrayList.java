package com.powem.inv.stacks;
//StockSpan
//original[medium]: https://leetcode.com/problems/online-stock-span/description/

//Solve this problem without using the Stack data structure. Use an Array List data structure.
//Given an array of integers which represent the price of a given commodity on each day,
//create a function called findDaysSpannedForPrice which finds how many days for which the price
//has been less than or equal to the price today.
//example, given the price in the past four days: [7,2,1,2], if today's price is 3, then the output
//is 4 because the price has been <= 3 for four days (2,1,2 and 3). Notice that the result also includes the current day.
//Also notice that the price 7 is higher than 3, so 7 was not included in the result.

public class PriceSpanForConsecutiveDaysArrayList {

  public static int findDaysSpannedForPrice(int[] priceHistory, int priceToday) {
    if (priceToday < 0) {
      throw new IllegalArgumentException("Price value cannot be negative");
    }

    if (priceHistory == null || priceHistory.length == 0) {
      return 1;
    }

    int days = 0;
    for (int i = priceHistory.length - 1; i > 0; i--) {
      if (priceHistory[i] <= priceToday) {
        days++;
      } else {
        return days + 1;
      }
    }

    return days + 1;
  }
}

//public static void tests() {
// TEST
//    try {
//        PriceSpanForConsecutiveDaysArrayList.findDaysSpannedForPrice(new int[]{7, 1, 1, 2}, -4);
//    assert false;
//    } catch (IllegalArgumentException e) {
//    assert e.getMessage() == "Price value cannot be negative";
//    }
//    // TEST_END
//    // TEST
//    try {
//    PriceSpanForConsecutiveDaysArrayList.findDaysSpannedForPrice(new int[]{7, 1, 1, -2}, 4);
//    assert false;
//    } catch (IllegalArgumentException e) {
//    assert e.getMessage() == "Price value in priceHistory cannot be negative";
//    }
//    // TEST_END
//
//    // TEST
//    assert PriceSpanForConsecutiveDaysArrayList.findDaysSpannedForPrice(null, 4) == 1;
//    // TEST_END
//
//    // TEST
//    assert PriceSpanForConsecutiveDaysArrayList.findDaysSpannedForPrice(new int[]{}, 4) == 1;
//    // TEST_END
//
//    // TEST
//    assert PriceSpanForConsecutiveDaysArrayList.findDaysSpannedForPrice(new int[]{7, 1, 1, 2}, 4) == 4;
//    // TEST_END
//
//    // TEST
//    assert PriceSpanForConsecutiveDaysArrayList.findDaysSpannedForPrice(new int[]{1, 2, 3, 4, 5}, 4) == 1;
//// TEST_END
//}

