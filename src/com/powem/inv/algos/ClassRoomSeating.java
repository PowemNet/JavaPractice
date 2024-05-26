package com.powem.inv.algos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

//import com.powem.inv.algos.ClassRoomSeating;
//
//        import java.util.ArrayList;
//        import java.util.Arrays;
//        import java.util.HashMap;
//        import java.util.List;
//        import java.util.Map;
//
//public class Main {
//    public static void main(String[] args) {
//        testBasicPreferences();
//        testNoPreferences();
//        testCircularPreferences();
//        testUnconnectedGroups();
//        testSingleStudent();
//    }
//
//    private static void testBasicPreferences() {
//        Map<String, List<String>> preferences = new HashMap<>();
//        preferences.put("Alice", Arrays.asList("Bob"));
//        preferences.put("Bob", Arrays.asList("Alice", "Charlie"));
//        preferences.put("Charlie", Arrays.asList("Bob"));
//
//        ClassRoomSeating seating = new ClassRoomSeating(preferences);
//        List<String> arrangement = seating.arrangeSeats();
//
//        //TEST
//        assert arrangement.indexOf("Alice") == arrangement.indexOf("Bob") - 1 || arrangement.indexOf("Alice") == arrangement.indexOf("Bob") + 1;
//        //TEST_END
//
//        //TEST
//        assert arrangement.indexOf("Bob") == arrangement.indexOf("Charlie") - 1 || arrangement.indexOf("Bob") == arrangement.indexOf("Charlie") + 1;
//        //TEST_END
//    }
//    private static void testNoPreferences() {
//        Map<String, List<String>> preferences = new HashMap<>();
//        preferences.put("Alice", new ArrayList<>());
//        preferences.put("Bob", new ArrayList<>());
//
//        ClassRoomSeating seating = new ClassRoomSeating(preferences);
//        List<String> arrangement = seating.arrangeSeats();
//
//        //TEST
//        assert arrangement.contains("Alice") && arrangement.contains("Bob");
//        //TEST_END
//    }
//
//    private static void testCircularPreferences() {
//        Map<String, List<String>> preferences = new HashMap<>();
//        preferences.put("Alice", Arrays.asList("Bob"));
//        preferences.put("Bob", Arrays.asList("Charlie"));
//        preferences.put("Charlie", Arrays.asList("Alice"));
//
//        ClassRoomSeating seating = new ClassRoomSeating(preferences);
//        List<String> arrangement = seating.arrangeSeats();
//
//        //TEST
//        boolean circular = arrangement.indexOf("Alice") == arrangement.indexOf("Bob") - 1 || arrangement.indexOf("Alice") == arrangement.indexOf("Charlie") + 1;
//        assert circular : "Circular preferences should form a closed loop.";
//        //TEST_END
//    }
//
//    private static void testUnconnectedGroups() {
//        Map<String, List<String>> preferences = new HashMap<>();
//        preferences.put("Alice", Arrays.asList("Bob"));
//        preferences.put("Bob", Arrays.asList("Alice"));
//        preferences.put("Charlie", Arrays.asList("David"));
//        preferences.put("David", Arrays.asList("Charlie"));
//
//        ClassRoomSeating seating = new ClassRoomSeating(preferences);
//        List<String> arrangement = seating.arrangeSeats();
//
//        //TEST
//        assert arrangement.contains("Alice") && arrangement.contains("Charlie");
//        //TEST_END
//    }
//
//    private static void testSingleStudent() {
//        Map<String, List<String>> preferences = new HashMap<>();
//        preferences.put("Alice", new ArrayList<>());
//
//        ClassRoomSeating seating = new ClassRoomSeating(preferences);
//        List<String> arrangement = seating.arrangeSeats();
//
//        //TEST
//        assert arrangement.contains("Alice");
//        //TEST_END
//    }
//}

