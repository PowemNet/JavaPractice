package com.powem.inv.collections;
//Day 5: Conference Event Scheduling System
//Objective:
//Learn to use the TreeMap to manage and access scheduled events efficiently based on their start times.
//
//You are developing a system for managing the scheduling of various events at a technology conference.
//Each event has a unique event ID, a title, a description, a start time, and a duration.
//
//Create a Java class TechConferenceEventScheduler that uses a TreeMap to store and manage events, allowing for quick
// retrieval based on event start times. Implement methods to add events, remove events, and retrieve the
// next event based on the current time.
//
//Method Requirements:
//
//addEvent(String eventId, String title, String description, Date startTime, int duration): Adds a new
//event to the schedule.

//removeEvent(String eventId): Removes an event from the schedule based on its ID.

//getNextEvent(Date currentTime): Retrieves the next event that will start after the given current time.

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class TechConferenceEventScheduler {

  public static class Event implements Comparable<Event> {
    private String eventId;
    private String title;
    private String description;
    private Date startTime;
    private int duration;

    public Event(String eventId, String title, String description, Date startTime, int duration) {
      if (eventId == null || eventId.isEmpty()) {
        throw new IllegalArgumentException("Event ID cannot be null or empty.");
      }
      if (title == null || title.isEmpty()) {
        throw new IllegalArgumentException("Title cannot be null or empty.");
      }
      if (description == null || description.isEmpty()) {
        throw new IllegalArgumentException("Description cannot be null or empty.");
      }
      if (startTime == null) {
        throw new IllegalArgumentException("Start time cannot be null.");
      }
      if (duration <= 0 || startTime.before(new Date())) {
        throw new IllegalArgumentException("Duration must be greater than zero or in the future");
      }
      this.eventId = eventId;
      this.title = title;
      this.description = description;
      this.startTime = startTime;
      this.duration = duration;
    }

    @Override
    public int compareTo(Event other) {
      return this.startTime.compareTo(other.startTime);
    }

    public String getEventId() {
      return eventId;
    }
  }

  private TreeMap<Date, Event> events;

  public TechConferenceEventScheduler() {
    this.events = new TreeMap<>();
  }

  public void addEvent(String eventId, String title, String description, Date startTime, int duration) {
    Event event = new Event(eventId, title, description, startTime, duration);
    events.put(startTime, event);
  }

  public boolean removeEvent(String eventId) {
    return events.entrySet().removeIf(entry -> entry.getValue().eventId.equals(eventId));
  }

  public Event getNextEvent(Date currentTime) {
    Map.Entry<Date, Event> nextEntry = events.ceilingEntry(currentTime);
    return nextEntry != null ? nextEntry.getValue() : null;
  }
}

//import java.util.Date;
//
//public class Main {
//  public static void main(String[] args) {
//    TechConferenceEventScheduler scheduler = new TechConferenceEventScheduler();
//    Date now = new Date();
//    scheduler.addEvent("E001", "Keynote Speech", "Opening keynote with visionary talks", new Date(now.getTime() + 3600000), 90); // 1 hour from now
//    scheduler.addEvent("E002", "AI Workshop", "Hands-on AI workshop", new Date(now.getTime() + 5400000), 120); // 1.5 hours from now
//
//    //TEST
//    TechConferenceEventScheduler.Event nextEvent = scheduler.getNextEvent(now);
//    assert "E001".equals(nextEvent.getEventId()) : "E001 should be the next event to start.";
//    //TEST_END
//
//    //TEST
//    boolean isRemoved = scheduler.removeEvent("E001");
//    assert isRemoved : "E001 should be successfully removed.";
//    nextEvent = scheduler.getNextEvent(now);
//    assert "E002".equals(nextEvent.getEventId()) : "After removing E001, E002 should be the next event.";
//    //TEST_END
//
//    //TEST
//    isRemoved = scheduler.removeEvent("E999");
//    assert !isRemoved : "Attempting to remove a non-existent event should return false.";
//    //TEST_END
//
//    //TEST
//    try {
//      scheduler.addEvent("E003", "Past Event", "This is a past event", new Date(now.getTime() - 10000), 60);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    // TEST: Add an event with invalid duration
//    //TEST
//    try {
//      scheduler.addEvent("E004", "Invalid Duration Event", "This event has invalid duration", new Date(now.getTime() + 7200000), -1);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    scheduler.removeEvent("E002");
//    nextEvent = scheduler.getNextEvent(now);
//    assert nextEvent == null : "There should be no more events left.";
//    //TEST_END
//  }
//}
