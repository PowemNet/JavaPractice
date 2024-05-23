package com.powem.inv.collections;
//Day 4: Emergency Room Queue Management
//Learn to use the PriorityQueue to manage data based on priority, ensuring that urgent cases are handled first.
//
//You are developing a system for an emergency room (ER) where patients are prioritized based on the severity of their condition. Each patient
//has a severity level, a unique identifier, and an arrival timestamp.
//
//Create a Java class EmergencyRoomManagement that uses a PriorityQueue to manage patient queues efficiently.
//Implement features to admit a patient, discharge a patient, and check the next patient to be treated.
//
//Create a method void admitPatient(String id, int severityLevel, Date arrivalTime) to admit a new patient to the emergency room

//Create a method boolean dischargePatient(String id) to discharge a patient

//Create a method Patient getNextPatient() which gets the next patient but does not discharge them

import java.util.Date;
import java.util.PriorityQueue;

public class EmergencyRoomManagement {

  public static class Patient implements Comparable<Patient> {
    public String id;
    public int severityLevel;
    public Date arrivalTime;

    public Patient(String id, int severityLevel, Date arrivalTime) {
      this.id = id;
      this.severityLevel = severityLevel;
      this.arrivalTime = arrivalTime;
    }

    @Override
    public int compareTo(Patient other) {
      if (this.severityLevel != other.severityLevel) {
        return other.severityLevel - this.severityLevel;
      }
      return this.arrivalTime.compareTo(other.arrivalTime);
    }

    @Override
    public String toString() {
      return "Patient ID: " + id + ", Severity Level: " + severityLevel + ", Arrival Time: " + arrivalTime;
    }
  }

  private PriorityQueue<Patient> patientQueue;

  public EmergencyRoomManagement() {
    patientQueue = new PriorityQueue<>();
  }

  public void admitPatient(String id, int severityLevel, Date arrivalTime) {
    if (id == null || id.isEmpty() || arrivalTime == null) {
      throw new IllegalArgumentException("Invalid input");
    }
    patientQueue.add(new Patient(id, severityLevel, arrivalTime));
  }

  public boolean dischargePatient(String id) {
    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException("Invalid input");
    }
    return patientQueue.removeIf(patient -> patient.id.equals(id));
  }

  public Patient getNextPatient() {
    return patientQueue.peek();
  }
}
//
//import java.util.Date;
//
//public class Main {
//  public static void main(String[] args) {
//    EmergencyRoomManagement erManagement = new EmergencyRoomManagement();
//    erManagement.admitPatient("001", 5, new Date());
//
//    // TEST
//    try {
//      erManagement.admitPatient(null, 5, new Date());
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      erManagement.admitPatient("002", 5, null);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    EmergencyRoomManagement.Patient nextPatient = erManagement.getNextPatient();
//    assert nextPatient.id.equals("001");
//    // TEST_END
//
//    // TEST
//    assert !nextPatient.id.equals("x");
//    // TEST_END
//
//    // TEST
//    assert !nextPatient.id.equals("x");
//    // TEST_END
//
//    // TEST
//    assert erManagement.dischargePatient("001");
//    // TEST_END
//
//    // TEST
//    assert erManagement.getNextPatient() == null;
//    // TEST_END
//
//    // TEST
//    try {
//      erManagement.dischargePatient(null);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      erManagement.dischargePatient("");
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//  }
//}
