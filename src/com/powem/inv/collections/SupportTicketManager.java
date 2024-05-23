package com.powem.inv.collections;
//Day 6: Customer Support Ticket System
//Objective:
//Learn to use the PriorityQueue to efficiently manage customer support tickets based on urgency and wait time.
//
//You are tasked with developing a system for managing customer support tickets for a tech company.
//Each support ticket includes a ticket ID, customer details, the issue category, and an urgency level.
//Tickets need to be addressed in an order that prioritizes both the urgency of the issue and how long
//the customer has been waiting.
//
//Create a Java class SupportTicketManager that uses a PriorityQueue to prioritize and manage support
// tickets effectively. Implement methods to add a ticket, update ticket urgency, and retrieve the next ticket to be resolved.
//
//addTicket(String ticketId, String customerName, String issueCategory, int urgencyLevel): Adds a new ticket to the system.

//updateTicketUrgency(String ticketId, int newUrgencyLevel): Updates an existing ticket's urgency level. Urgency must be between
//1 and 5.

//getNextTicket(): Retrieves and removes the ticket with the highest urgency from the queue.

import java.util.PriorityQueue;

public class SupportTicketManager {
  public static class SupportTicket implements Comparable<SupportTicket> {
    private String ticketId;
    private String customerName;
    private String issueCategory;
    private int urgencyLevel;
    private long creationTime;

    public SupportTicket(String ticketId, String customerName, String issueCategory, int urgencyLevel) {
      if (ticketId == null || ticketId.isEmpty()) {
        throw new IllegalArgumentException("Ticket ID cannot be null or empty.");
      }
      if (customerName == null || customerName.isEmpty()) {
        throw new IllegalArgumentException("Customer name cannot be null or empty.");
      }
      if (urgencyLevel < 1 || urgencyLevel > 5) {
        throw new IllegalArgumentException("Urgency level must be between 1 and 5.");
      }
      this.ticketId = ticketId;
      this.customerName = customerName;
      this.issueCategory = issueCategory;
      this.urgencyLevel = urgencyLevel;
      this.creationTime = System.currentTimeMillis();
    }

    @Override
    public int compareTo(SupportTicket other) {
      if (this.urgencyLevel != other.urgencyLevel) {
        return other.urgencyLevel - this.urgencyLevel;
      }
      return Long.compare(this.creationTime, other.creationTime);
    }

    public String getTicketId() {
      return ticketId;
    }
  }

  private PriorityQueue<SupportTicket> tickets;

  public SupportTicketManager() {
    tickets = new PriorityQueue<>();
  }

  public void addTicket(String ticketId, String customerName, String issueCategory, int urgencyLevel) {
    tickets.add(new SupportTicket(ticketId, customerName, issueCategory, urgencyLevel));
  }

  public boolean updateTicketUrgency(String ticketId, int newUrgencyLevel) {
    if (newUrgencyLevel < 1 || newUrgencyLevel > 5) {
      throw new IllegalArgumentException("Urgency level must be between 1 and 5.");
    }
    for (SupportTicket ticket : tickets) {
      if (ticket.ticketId.equals(ticketId)) {
        tickets.remove(ticket);
        ticket.urgencyLevel = newUrgencyLevel;
        tickets.add(ticket);
        return true;
      }
    }
    return false;
  }

  public SupportTicket getNextTicket() {
    return tickets.poll();
  }
}

//
//public class Main {
//  public static void main(String[] args) {
//    SupportTicketManager manager = new SupportTicketManager();
//
//    manager.addTicket("T001", "Peter", "Software Issue", 3);
//    manager.addTicket("T002", "Grace", "Hardware Failure", 5);
//
//    // TEST
//    SupportTicketManager.SupportTicket nextTicket = manager.getNextTicket();
//    assert nextTicket.getTicketId().equals("T002");
//    // TEST_END
//
//    // TEST
//    try {
//      manager.addTicket("", "Alice Johnson", "Networking Issue", 4);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      manager.updateTicketUrgency("T001", 0);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    assert !manager.updateTicketUrgency("T999", 4);
//    // TEST_END
//
//    // TEST
//    assert manager.updateTicketUrgency("T001", 5);
//    nextTicket = manager.getNextTicket();
//    assert (nextTicket.getTicketId()).equals("T001");
//    // TEST_END
//
//    // TEST
//    manager.addTicket("T003", "Bob Brown", "Software Issue", 4);
//    manager.addTicket("T004", "Eve Davis", "Hardware Failure", 4);
//    assert manager.getNextTicket().getTicketId().equals("T003");
//    assert manager.getNextTicket().getTicketId().equals("T004");
//    // TEST_END
//
//    // TEST
//    assert manager.getNextTicket() == null;
//    // TEST_END
//  }
//}

