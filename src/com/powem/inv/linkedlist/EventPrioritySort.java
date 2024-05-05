package com.powem.inv.linkedlist;

//LinkedList Cycle detection

//Transaction events represented as a linked list need to be processed based on priority given as an integer with each event node.
//Implement a function called sortEventsByPriority(ListNode head) which sorts the events by ascending
//order of their priority and returns the sorted linked list.
//Assume each list node has an additional int priority field. For example, sorting the list:
//[(1, 3), (2, 1), (3, 2)] should result in the list
//[(2, 1), (3, 2), (1, 3)] based on priority values.

public class EventPrioritySort {
  public static class ListNode {
    public int val;
    public int priority;
    public ListNode next;

    ListNode(int x, int p) {
      val = x;
      priority = p;
    }
  }

  public ListNode createListNode(int x, int p) {
    return new ListNode(x, p);
  }

  public static ListNode sortEventsByPriority(ListNode head) {
    if (head == null || head.next == null) {
      return head;
    }

    ListNode sentinel = new ListNode(0, 0);
    sentinel.next = head;
    ListNode current = head.next;
    ListNode prev = head;
    ListNode temp;

    while (current != null) {
      if (current.priority < prev.priority) {
        temp = sentinel;
        while (temp.next != null && temp.next.priority <= current.priority) {
          temp = temp.next;
        }
        prev.next = current.next;
        current.next = temp.next;
        temp.next = current;

        current = prev.next;
      } else {
        prev = current;
        current = current.next;
      }
    }

    return sentinel.next;
  }
}

//public static void tests() {
//// TEST
//EventPrioritySort prioritySort = new EventPrioritySort();
//EventPrioritySort.ListNode head1 = prioritySort.createListNode(1, 3);
//head1.next = prioritySort.createListNode(2, 1);
//head1.next.next = prioritySort.createListNode(3, 2);
//
//EventPrioritySort.ListNode result1 = prioritySort.sortEventsByPriority(head1);
//    assert result1.priority == 1;
//    assert result1.val == 2;
//    assert result1.next.priority == 2;
//    assert result1.next.val == 3;
//    assert result1.next.next.priority == 3;
//    assert result1.next.next.val == 1;
//// TEST_END
//
//// TEST
//EventPrioritySort prioritySort2 = new EventPrioritySort();
//    assert prioritySort2.sortEventsByPriority(null) == null;
//// TEST_END
//
//// TEST
//EventPrioritySort prioritySort3 = new EventPrioritySort();
//EventPrioritySort.ListNode head3 = prioritySort.createListNode(1, 3);
//EventPrioritySort.ListNode result3 = prioritySort.sortEventsByPriority(head3);
//    assert result3.priority == 3;
//    assert result3.val == 1;
//// TEST_END
//}
