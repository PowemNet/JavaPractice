package com.powem.inv.linkedlist;

//Remove Linked List Elements
//original[easy]: https://leetcode.com/problems/remove-linked-list-elements/description/

//A system consumes events from an event bus. Some events need to be filtered out before they are processed.
//Related events are represented by a LinkedList, identified by an ID.
//Given that the ID is the "value" of a given list node, implement a function called removeEvent(ListNode head, int value) which
//removes an event based on the ID provided, and returns a new LinkedList represented as an array of integers.
//For example for a LinkedList of events: 1 -> 2 -> 2-> 3, if the removeEvent(2) is called, the result should be
// 1 -> 3

public class EventLinkedListItemRemoval {

  public class ListNode {

    public int val;
    public ListNode next;

    public ListNode(int x) {
      val = x;
    }
  }

  public ListNode createListNode(int x) {
    return new ListNode(x);
  }

  public ListNode removeEvent(ListNode head, int value) {
    if (head == null) {
      throw new IllegalArgumentException("head node cannot be null");
    }
    if (value < 0) {
      throw new IllegalArgumentException("value cannot be negative");
    }
    ListNode sentinel = new ListNode(0);
    sentinel.next = head;

    ListNode prevEvent = sentinel;
    ListNode currEvent = head;
    while (currEvent != null) {
      if (currEvent.val == value) {
        prevEvent.next = currEvent.next;
      } else {
        prevEvent = currEvent;
      }
      currEvent = currEvent.next;
    }
    return sentinel.next;
  }
}

//public static void tests() {
//// TEST
//EventLinkedListItemRemoval eventLinkedListItemRemoval = new EventLinkedListItemRemoval();
//EventLinkedListItemRemoval.ListNode input1 = eventLinkedListItemRemoval.createListNode(1);
//input1.next = eventLinkedListItemRemoval.createListNode(2);
//input1.next.next = eventLinkedListItemRemoval.createListNode(2);
//input1.next.next.next = eventLinkedListItemRemoval.createListNode(3);
//
//EventLinkedListItemRemoval.ListNode result1 = eventLinkedListItemRemoval.removeEvent(input1, 2);
//
//    assert result1.val == 1 : "First node should be 1";
//    assert result1.next.val == 3 : "Next node should be 3 after removing 2s";
//// TEST_END
//
//// TEST
//EventLinkedListItemRemoval.ListNode input2 = eventLinkedListItemRemoval.createListNode(1);
//EventLinkedListItemRemoval.ListNode result2 = eventLinkedListItemRemoval.removeEvent(input2, 1);
//
//    try {
//int val = result2.val;
//      assert false;
//          } catch (NullPointerException e) {
//    assert e.getMessage().equals("Cannot read field \"val\" because \"result2\" is null");
//    }
//// TEST_END
//
//// TEST
//EventLinkedListItemRemoval.ListNode input3 = null;
//
//    try {
//        eventLinkedListItemRemoval.removeEvent(input3, 1);
//      assert false;
//          } catch (IllegalArgumentException e) {
//    assert e.getMessage().equals("head node cannot be null");
//    }
//// TEST_END
//
//// TEST
//EventLinkedListItemRemoval.ListNode input4 = eventLinkedListItemRemoval.createListNode(1);
//
//    try {
//        eventLinkedListItemRemoval.removeEvent(input4, -1);
//      assert false;
//          } catch (IllegalArgumentException e) {
//    assert e.getMessage().equals("value cannot be negative");
//    }
//// TEST_END
//}
