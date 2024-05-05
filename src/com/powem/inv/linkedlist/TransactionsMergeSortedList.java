package com.powem.inv.linkedlist;

//Merge Two Sorted Lists

//A transactions event consumer needs to consolidate orders from two different
//kafka topics into a single, sorted order list for processing. Each topic's events are already sorted by transaction ID,
//an increasing sequence of integers.
//Implement a function mergeTransactions(TransactionList topic1, TransactionList topic2) that merges the two event
//lists into one sorted list by transaction ID.
//The merged list should be constructed using the nodes (transactions) from the provided lists to minimize
//data duplication.
//
//Example:
//Input:
//topic1: Transaction IDs: 100 -> 371 -> 444
//topic2: Transaction IDs: 100 -> 386 -> 444
//Output:
//Merged Transaction List: 100 -> 100 -> 222 -> 386 -> 444 -> 444

public class TransactionsMergeSortedList {
  public class TransactionList {

    public int id;
    public TransactionList next;

    public TransactionList(int x) {
      id = x;
    }
  }

  public TransactionList createListNode(int x) {
    if(x < 0 ) {
      throw new IllegalArgumentException("Root cannot be negative");
    }
    return new TransactionList(x);
  }

  public static TransactionList mergeTransactions(TransactionList topic1, TransactionList topic2) {
    if (topic1 == null) {
      return topic2;
    }
    if (topic2 == null) {
      return topic1;
    }

    if (topic1.id < topic2.id) {
      topic1.next = mergeTransactions(topic1.next, topic2);
      return topic1;
    } else {
      topic2.next = mergeTransactions(topic1, topic2.next);
      return topic2;
    }
  }
}

//public static void tests() {
//// TEST
//TransactionsMergeSortedList mergeSortedList = new TransactionsMergeSortedList();
//TransactionsMergeSortedList.TransactionList topic1 = mergeSortedList.createListNode(1);
//topic1.next = mergeSortedList.createListNode(2);
//topic1.next.next = mergeSortedList.createListNode(4);
//
//TransactionsMergeSortedList.TransactionList topic2 = mergeSortedList.createListNode(1);
//topic2.next = mergeSortedList.createListNode(3);
//topic2.next.next = mergeSortedList.createListNode(4);
//
//TransactionsMergeSortedList.TransactionList result1 = mergeSortedList.mergeTransactions(topic1, topic2);
//    assert result1.id == 1;
//    assert result1.next.id == 1;
//    assert result1.next.next.id == 2;
//    assert result1.next.next.next.id == 3;
//    assert result1.next.next.next.next.id == 4;
//    assert result1.next.next.next.next.next.id == 4;
//// TEST_END
//
//// TEST
//TransactionsMergeSortedList.TransactionList topic = mergeSortedList.createListNode(1);
//topic.next = mergeSortedList.createListNode(2);
//topic.next.next = mergeSortedList.createListNode(4);
//TransactionsMergeSortedList.TransactionList result2 = mergeSortedList.mergeTransactions(topic, null);
//    assert result2.id == 1;
//    assert result2.next.id == 2;
//    assert result2.next.next.id == 4;
//    // TEST_END
//
//    // TEST
//    assert mergeSortedList.mergeTransactions(null, null) == null;
//// TEST_END
//}
