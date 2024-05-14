package com.powem.inv.linkedlist;

//LinkedList: Meeting Room Scheduler

//Meeting times are managed in a linked list where each node represents a
//    meeting's ' start time and duration.
//Each input has the startTime and duration eg ({startTime1} ({duration1} hours)-> {startTime2} ({duration2} hours))
//No startTime can be repeated.
//
//    Implement a function detectConflicts(Meeting head) that checks for any overlapping
//    meetings and returns a list of strings describing each conflict.
//    Each conflict should be identified by the overlapping meeting times and should list the affected meetings.
//    Example:
//
//    Input: Linked List of Meetings: ("09:00", 2 hours) -> ("10:00", 1 hour) -> ("11:00", 2 hours)
//    Output: List of Conflicts: ["Conflict between 09:00 (2 hours) and 10:00 (1 hours)"]

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class MeetingRoomScheduler {
  public static class Meeting {
    public LocalTime start;
    public int duration;
    public Meeting next;

    public Meeting(String start, int duration) {
      if (start == null || start.isEmpty() || duration < 0) {
        throw new IllegalArgumentException("invalid input");
      }
      this.start = LocalTime.parse(start);
      this.duration = duration;
      this.next = null;
    }
  }

  public static List<String> detectConflicts(Meeting head) {
    List<String> conflicts = new ArrayList<>();
    Meeting current = head;

    while (current != null) {
      Meeting next = current.next;
      LocalTime currentEndTime = current.start.plusHours(current.duration);

      while (next != null) {
        LocalTime nextStartTime = next.start;
        LocalTime nextEndTime = next.start.plusHours(next.duration);

        if ((current.start.isBefore(nextEndTime) && currentEndTime.isAfter(nextStartTime)) ||
            (nextStartTime.isBefore(currentEndTime) && nextEndTime.isAfter(current.start))) {
          String conflictMessage = "Conflict between " + current.start + " (" + current.duration +
              " hours) and " + next.start + " (" + next.duration + " hours)";
          if (!conflicts.contains(conflictMessage)) {
            conflicts.add(conflictMessage);
          }
        }
        next = next.next;
      }
      current = current.next;
    }

    return conflicts;
  }
}

//public static void tests() {
//// TEST
//Meeting meeting1 = new MeetingRoomScheduler.Meeting("09:00", 2);
//meeting1.next = new MeetingRoomScheduler.Meeting("10:00", 1);
//meeting1.next.next = new MeetingRoomScheduler.Meeting("11:00", 2);
//List<String> result1 = MeetingRoomScheduler.detectConflicts(meeting1);
//    assert result1.size() == 1;
//    // TEST_END
//
//    // TEST
//    assert result1.get(0).equals("Conflict between 09:00 (2 hours) and 10:00 (1 hours)");
//// TEST_END
//
//// TEST
//Meeting meeting2 = new MeetingRoomScheduler.Meeting("09:00", 2);
//List<String> result2 = MeetingRoomScheduler.detectConflicts(meeting2);
//    assert result2.isEmpty();
//// TEST_END
//
//// TEST
//    try {
//Meeting meeting3 = new MeetingRoomScheduler.Meeting("", 2);
//      assert false;
//          } catch (IllegalArgumentException e) {
//    assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//Meeting meeting4 = new MeetingRoomScheduler.Meeting("09:00", -1);
//      assert false;
//          } catch (IllegalArgumentException e) {
//    assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//Meeting meeting4 = new MeetingRoomScheduler.Meeting("09:00kkk", 1);
//      assert false;
//          } catch (
//DateTimeParseException e) {
//    assert true;
//    }
//// TEST_END
//}
