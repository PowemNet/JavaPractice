package com.powem.inv.linkedlist;

//LinkedList Cycle detection

//An events system processes events stored in a circular linked list where the last node points back to
//an earlier node, creating a cycle. Cycels represent related events and can be used to tract when a cycle starts and ends.
//Implement a function called findEventCycle(ListNode head)
//which returns the index (0-based) of the node where the cycle begins. If there is no cycle, return -1.
//For example, for a linked list configured as 1 -> 2 -> 3 -> 4 -> 2 (back to second node),
//the function should return 1 as the cycle starts at the node with value 2.

public class EventLinkedListCycleDetection {

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

  public int findEventCycle(ListNode head) {
    if (head == null) {
      return -1;
    }

    ListNode slow = head, fast = head;
    boolean hasCycle = false;

    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
      if (slow == fast) {
        hasCycle = true;
        break;
      }
    }

    if (!hasCycle) {
      return -1;
    }
    ListNode entry = head;
    int index = 0;
    while (entry != slow) {
      entry = entry.next;
      slow = slow.next;
      index++;
    }

    return index;
  }
}

//public static void tests() {
//// TEST
//EventLinkedListCycleDetection detector1 = new EventLinkedListCycleDetection();
//EventLinkedListCycleDetection.ListNode head1 = detector1.createListNode(1);
//head1.next = detector1.createListNode(2);
//head1.next.next = detector1.createListNode(3);
//head1.next.next.next = detector1.createListNode(4);
//head1.next.next.next.next = head1.next;
//
//    assert detector1.findEventCycle(head1) == 1;
//// TEST_END
//
//// TEST
//EventLinkedListCycleDetection detector2 = new EventLinkedListCycleDetection();
//EventLinkedListCycleDetection.ListNode head2 = detector2.createListNode(1);
//head2.next = detector2.createListNode(2);
//head2.next.next = detector2.createListNode(3);
//head2.next.next.next = detector2.createListNode(4);
//
//    assert detector2.findEventCycle(head2) == -1;
//// TEST_END
//
//// TEST
//EventLinkedListCycleDetection detector3 = new EventLinkedListCycleDetection();
//    assert detector3.findEventCycle(null) == -1;
//// TEST_END
//}
