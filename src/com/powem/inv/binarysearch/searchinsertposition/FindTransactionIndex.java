package com.powem.inv.binarysearch.searchinsertposition;
//original[easy]: https://leetcode.com/problems/search-insert-position/

//A financial system has an array of order transactions. Before adding a new transaction,
//we need to know if the transaction already exists. If it does not already exist, we need
//to know what the index would be if the transaction was added to the array, based on its id.
//Create a function called findIndex which takes an array of transactions, a transaction, and
//return an index. For example, if the input transactions = [0,1,4], and the transaction we want is
//5, then return. 3. If the transaction we want it 4, then return 2.
//Constraints:
// 1- All transactions IDs are integers.

import java.util.Arrays;

public class FindTransactionIndex {

  public static int findIndex(int[] transactions, int transaction) {
    Arrays.sort(transactions);
    int pivot, left = 0, right = transactions.length - 1;
    while (left <= right) {
      pivot = left + (right - left) / 2;
      if (transactions[pivot] == transaction) {
        return pivot;
      }
      if (transaction < transactions[pivot]) {
        right = pivot - 1;
      } else {
        left = pivot + 1;
      }
    }
    return left;
  }
}

