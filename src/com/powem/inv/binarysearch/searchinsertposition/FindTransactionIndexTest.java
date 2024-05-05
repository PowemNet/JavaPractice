package com.powem.inv.binarysearch.searchinsertposition;

public class FindTransactionIndexTest {

  public static void tests() {
    // TEST
    int[] unorderedTransactions = {3, 2, 4};
    assert FindTransactionIndex.findIndex(unorderedTransactions, 2) == 0;
    // TEST_END

    // TEST
    int[] orderedTransactions = {2, 3, 4};
    assert FindTransactionIndex.findIndex(unorderedTransactions, 2) == 0;
    // TEST_END

    // TEST
    int[] orderedTransactionsItemNotExist = {3, 2, 4};
    assert FindTransactionIndex.findIndex(unorderedTransactions, 5) == 3;
    // TEST_END
  }
}