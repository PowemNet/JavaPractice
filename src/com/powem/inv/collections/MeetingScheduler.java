package com.powem.inv.collections;
//Day 3: Scheduling System for Meetings
//Objective:
//Explore the use of TreeSet to manage sorted data effectively and ensure quick access to scheduled elements.
//
//You are tasked with developing a system for a corporate office that schedules meetings. Each meeting has a start
//time, end time, and a unique identifier. It's crucial for the system to manage meetings efficiently, allowing
//for quick insertion, deletion, and retrieval of meetings based on their scheduled time.
//
//Create a Java class MeetingScheduler that uses a TreeSet to manage meetings. Implement features to add a meeting,
//cancel a meeting, and find the next meeting scheduled after a given time.

//Create a method void addMeeting(String id, Date startTime, Date endTime) which adds a new meeting

//Create a method boolean cancelMeeting(String id) which attempts to cancel a meeting and returns true if successful

//Create a method Meeting getNextMeeting(Date currentTime) which returns the next meeting

import java.util.Date;
import java.util.TreeSet;

public class MeetingScheduler {
  public static class Meeting implements Comparable<Meeting> {
    private String id;
    private Date startTime;
    private Date endTime;

    public Meeting(String id, Date startTime, Date endTime) {
      this.id = id;
      this.startTime = startTime;
      this.endTime = endTime;
    }

    public String getId() {
      return id;
    }

    @Override
    public int compareTo(Meeting other) {
      return this.startTime.compareTo(other.startTime);
    }
  }

  private TreeSet<Meeting> meetings;

  public MeetingScheduler() {
    meetings = new TreeSet<>();
  }

  public void addMeeting(String id, Date startTime, Date endTime) {
    if (id == null || id.isEmpty() || endTime.before(startTime)) {
      throw new IllegalArgumentException("Invalid inputs");
    }
    meetings.add(new Meeting(id, startTime, endTime));
  }

  public boolean cancelMeeting(String id) {
    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException("Invalid inputs");
    }
    return meetings.removeIf(meeting -> meeting.getId().equals(id));
  }

  public Meeting getNextMeeting(Date currentTime) {
    for (Meeting meeting : meetings) {
      if (meeting.startTime.after(currentTime)) {
        return meeting;
      }
    }
    return null;
  }
}


//import java.util.Date;
//
//public class Main {
//  public static void main(String[] args) {
//    MeetingScheduler scheduler = new MeetingScheduler();
//    scheduler.addMeeting("001", new Date(), new Date(new Date().getTime() + 3600000));
//
//    // TEST
//    try {
//      scheduler.addMeeting("", new Date(), new Date(new Date().getTime() + 3600000));
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      scheduler.addMeeting(null, new Date(), new Date(new Date().getTime() + 3600000));
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      scheduler.addMeeting("001", new Date(new Date().getTime() + 3600000), new Date());
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    MeetingScheduler.Meeting nextMeeting = scheduler.getNextMeeting(new Date(new Date().getTime() - 3600000));
//    assert nextMeeting.getId().equals("001");
//    // TEST_END
//
//    // TEST
//    assert !nextMeeting.getId().equals("002");
//    // TEST_END
//
//    // TEST
//    assert !nextMeeting.getId().equals("");
//    // TEST_END
//
//    // TEST
//    assert !nextMeeting.getId().equals("x");
//    // TEST_END
//
//    // TEST
//    assert scheduler.cancelMeeting("001");
//    // TEST_END
//
//    // TEST
//    try {
//      scheduler.cancelMeeting("");
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      scheduler.cancelMeeting(null);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    assert scheduler.getNextMeeting(new Date()) == null;
//    // TEST_END
//  }
//}