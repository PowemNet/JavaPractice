package com.powem.inv.algos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

//        Problem: Efficient Classroom Seating Arrangement
//        Problem Statement:
//        You are tasked with designing an algorithm for arranging students in a classroom to maximize student satisfaction based on their seating preferences. Each student has a set of preferred neighbors, and your goal is to place them such that the number of students sitting next to at least one preferred neighbor is maximized.
//
//        Objective:
//        Develop a method that arranges students in a linear row of seats to maximize the number of satisfied seating preferences.
//
//        Details:
//
//        Each student can list one or more other students they prefer to sit next to.
//        The classroom has a single row of seats.
//        Challenge:
//        Implement the ClassroomSeating class that takes a list of students and their preferences, and arranges them to maximize preference satisfaction.
//

public class ClassRoomSeating {
    private Map<String, List<String>> preferences;
    private Set<String> placedStudents;
    private List<String> arrangement;

    public ClassRoomSeating(Map<String, List<String>> preferences) {
        this.preferences = preferences;
        this.placedStudents = new HashSet<>();
        this.arrangement = new ArrayList<>();
    }

    public List<String> arrangeSeats() {
        // Sort the students based on the number of preferences
        List<String> sortedStudents = new ArrayList<>(preferences.keySet());
        sortedStudents.sort((s1, s2) -> Integer.compare(preferences.get(s2).size(), preferences.get(s1).size()));

        // Place each student that hasn't been placed yet
        for (String student : sortedStudents) {
            if (!placedStudents.contains(student)) {
                placeStudent(student);
            }
        }
        return arrangement;
    }

    private void placeStudent(String student) {
        if (placedStudents.contains(student)) {
            return; // Student already placed
        }

        placedStudents.add(student);

        // Try to place the student next to a preferred neighbor
        List<String> prefs = preferences.get(student);
        for (String neighbor : prefs) {
            if (placedStudents.contains(neighbor)) {
                int index = arrangement.indexOf(neighbor);
                // Try to place to the left or right if space allows
                if (index > 0 && !placedStudents.contains(arrangement.get(index - 1))) {
                    arrangement.add(index, student);
                    return;
                } else if (index < arrangement.size() - 1 && !placedStudents.contains(arrangement.get(index + 1))) {
                    arrangement.add(index + 1, student);
                    return;
                }
            }
        }

        // If no preferred spot, place at the end
        arrangement.add(student);

        // Try to place preferred neighbors next
        for (String neighbor : prefs) {
            if (!placedStudents.contains(neighbor)) {
                placeStudent(neighbor);
            }
        }

        // After placing preferred neighbors, try to rearrange
        rearrangePreferredNeighbors(student);
    }

    private void rearrangePreferredNeighbors(String student) {
        List<String> prefs = preferences.get(student);
        for (String neighbor : prefs) {
            if (placedStudents.contains(neighbor)) {
                int studentIndex = arrangement.indexOf(student);
                int neighborIndex = arrangement.indexOf(neighbor);
                if (Math.abs(studentIndex - neighborIndex) > 1) {
                    if (studentIndex < neighborIndex) {
                        if (studentIndex + 1 < arrangement.size()) {
                            arrangement.add(studentIndex + 1, neighbor);
                            arrangement.remove(neighborIndex);
                        }
                    } else {
                        if (neighborIndex + 1 < arrangement.size()) {
                            arrangement.add(neighborIndex + 1, student);
                            arrangement.remove(studentIndex);
                        }
                    }
                }
            }
        }
    }
}
