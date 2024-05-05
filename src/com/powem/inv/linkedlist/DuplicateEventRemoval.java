package com.powem.inv.linkedlist;

//LinkedList Cycle detection

//Events are represented in a linked list, but due to system errors, some events are duplicated consecutively.
//Implement a function called removeDuplicates(ListNode head) which removes consecutive duplicate nodes and
//returns the linked list without duplicates.
//For example, for a LinkedList of events: 1 -> 2 -> 2 -> 3 -> 3 -> 3 -> 4, after
//calling removeDuplicates(), the result should be 1 -> 2 -> 3 -> 4.

public class DuplicateEventRemoval {
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

  public static ListNode removeDuplicates(ListNode head) {
    if (head == null) {
      return null;
    }

    ListNode current = head;
    while (current != null && current.next != null) {
      if (current.val == current.next.val) {
        current.next = current.next.next;
      } else {
        current = current.next;
      }
    }
    return head;
  }
}

//public static void tests() {
//// TEST
//DuplicateEventRemoval duplicateRemoval = new DuplicateEventRemoval();
//DuplicateEventRemoval.ListNode head1 = duplicateRemoval.createListNode(1);
//head1.next = duplicateRemoval.createListNode(2);
//head1.next.next = duplicateRemoval.createListNode(2);
//head1.next.next.next = duplicateRemoval.createListNode(3);
//head1.next.next.next.next = duplicateRemoval.createListNode(3);
//head1.next.next.next.next.next = duplicateRemoval.createListNode(3);
//head1.next.next.next.next.next.next = duplicateRemoval.createListNode(4);
//
//DuplicateEventRemoval.ListNode result1 = duplicateRemoval.removeDuplicates(head1);
//    assert result1.val == 1;
//    assert result1.next.val == 2;
//    assert result1.next.next.val == 3;
//    assert result1.next.next.next.val == 4;
//// TEST_END
//
//// TEST
//DuplicateEventRemoval duplicateRemoval2 = new DuplicateEventRemoval();
//    assert duplicateRemoval2.removeDuplicates(null) == null;
//// TEST_END
//}
