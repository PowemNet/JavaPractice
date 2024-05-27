
//        Problem: Efficient Classroom Seating Arrangement
//        Problem Statement:
//        Create an algorithm for arranging students in a classroom to maximize student satisfaction based on their
//        seating preferences. Each student has a set of preferred neighbors, and your goal is to place them such that
//        the number of students sitting next to at least one preferred neighbor is maximized.
//
//        Develop a method that arranges students in a linear row of seats to maximize the number of satisfied seating
//        preferences.

//        Each student can list one or more other students they prefer to sit next to.
//        The classroom has a single row of seats.

//        Implement the ClassroomSeating class that takes a list of students and their preferences, and arranges them
//        to maximize preference satisfaction.
//
//        Function Signature:
//public class ClassroomSeating {
//    public List<String> arrangeSeats(Map<String, List<String>> preferences);
//}

package com.powem.inv.algos;

import java.util.*;

public class ClassRoomSeating {
    private Map<String, List<String>> preferences;
    private Set<String> placedStudents;
    private List<String> arrangement;

    public ClassRoomSeating(Map<String, List<String>> preferences) {
        if (preferences == null || preferences.isEmpty()) {
            throw new IllegalArgumentException("Invalid input");
        }
        this.preferences = preferences;
        this.placedStudents = new HashSet<>();
        this.arrangement = new ArrayList<>();
    }

    public List<String> arrangeSeats() {
        List<String> sortedStudents = new ArrayList<>(preferences.keySet());
        sortedStudents.sort((s1, s2) -> Integer.compare(preferences.get(s2).size(), preferences.get(s1).size()));

        for (String student : sortedStudents) {
            if (!placedStudents.contains(student)) {
                placeStudent(student);
            }
        }
        return arrangement;
    }

    private void placeStudent(String student) {
        if (placedStudents.contains(student)) {
            return;
        }

        placedStudents.add(student);

        List<String> prefs = preferences.get(student);
        for (String neighbor : prefs) {
            if (placedStudents.contains(neighbor)) {
                int index = arrangement.indexOf(neighbor);
                if (index > 0 && !placedStudents.contains(arrangement.get(index - 1))) {
                    arrangement.add(index, student);
                    return;
                } else if (index < arrangement.size() - 1 && !placedStudents.contains(arrangement.get(index + 1))) {
                    arrangement.add(index + 1, student);
                    return;
                }
            }
        }

        arrangement.add(student);

        for (String neighbor : prefs) {
            if (!placedStudents.contains(neighbor)) {
                placeStudent(neighbor);
            }
        }

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

//TESTS
//import com.powem.inv.algos.ClassRoomSeating;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class Main {
//    public static void main(String[] args) {
//        Map<String, List<String>> preferences1 = new HashMap<>();
//        preferences1.put("Alice", Arrays.asList("Bob"));
//        preferences1.put("Bob", Arrays.asList("Alice", "Charlie"));
//        preferences1.put("Charlie", Arrays.asList("Bob"));
//
//        ClassRoomSeating seating1 = new ClassRoomSeating(preferences1);
//        List<String> arrangement1 = seating1.arrangeSeats();
//
//        // TEST
//        assert arrangement1.indexOf("Alice") == arrangement1.indexOf("Bob") - 1
//                || arrangement1.indexOf("Alice") == arrangement1.indexOf("Bob") + 1;
//        // TEST_END
//
//        // TEST
//        assert arrangement1.indexOf("Bob") == arrangement1.indexOf("Charlie") - 1
//                || arrangement1.indexOf("Bob") == arrangement1.indexOf("Charlie") + 1;
//        // TEST_END
//
//        Map<String, List<String>> preferences2 = new HashMap<>();
//        preferences2.put("Alice", new ArrayList<>());
//        preferences2.put("Bob", new ArrayList<>());
//
//        // TEST
//        ClassRoomSeating seating2 = new ClassRoomSeating(preferences2);
//        List<String> arrangement2 = seating2.arrangeSeats();
//
//        assert arrangement2.contains("Alice") && arrangement2.contains("Bob");
//        // TEST_END
//
//        Map<String, List<String>> preferences3 = new HashMap<>();
//        preferences3.put("Alice", Arrays.asList("Bob"));
//        preferences3.put("Bob", Arrays.asList("Charlie"));
//        preferences3.put("Charlie", Arrays.asList("Alice"));
//
//        ClassRoomSeating seating3 = new ClassRoomSeating(preferences3);
//        List<String> arrangement3 = seating3.arrangeSeats();
//
//        // TEST
//        boolean circular = arrangement3.indexOf("Alice") == arrangement3.indexOf("Bob") - 1
//                || arrangement3.indexOf("Alice") == arrangement3.indexOf("Charlie") + 1;
//        assert circular;
//        // TEST_END
//
//        Map<String, List<String>> preferences4 = new HashMap<>();
//        preferences4.put("Alice", Arrays.asList("Bob"));
//        preferences4.put("Bob", Arrays.asList("Alice"));
//        preferences4.put("Charlie", Arrays.asList("David"));
//        preferences4.put("David", Arrays.asList("Charlie"));
//
//        ClassRoomSeating seating4 = new ClassRoomSeating(preferences4);
//        List<String> arrangement4 = seating4.arrangeSeats();
//
//        // TEST
//        assert arrangement4.contains("Alice") && arrangement4.contains("Charlie");
//        // TEST_END
//
//        Map<String, List<String>> preferences5 = new HashMap<>();
//        preferences5.put("Alice", new ArrayList<>());
//
//        // TEST
//        ClassRoomSeating seating5 = new ClassRoomSeating(preferences5);
//        List<String> arrangement5 = seating5.arrangeSeats();
//
//        assert arrangement5.contains("Alice");
//        // TEST_END
//
//        // TEST
//        try {
//            ClassRoomSeating seating6 = new ClassRoomSeating(null);
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        // TEST_END
//
//        // TEST
//        try {
//            ClassRoomSeating seating6 = new ClassRoomSeating(Map.of());
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        // TEST_END
//    }
//}

