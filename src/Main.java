//import com.powem.inv.algos.CommunityDetector;
//import com.powem.inv.algos.DynamicTaskScheduler;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
//public class Main {
//  public static void main(String[] args) {
//    testSimpleDependency();
//    testResourceConstraint();
//    testMultipleDependencies();
//    testNoResourcesLeft();
//    testAllTasksSchedulable();
//
//  }
//
//  private static void testSimpleDependency() {
//    DynamicTaskScheduler scheduler = new DynamicTaskScheduler(10);
//    scheduler.addTask("A", 2, 2, Arrays.asList());
//    scheduler.addTask("B", 1, 1, Arrays.asList("A"));
//
//    //TEST
//    List<String> result = scheduler.scheduleTasks();
//    assert result.equals(Arrays.asList("A", "B")) : "Failed: Tasks should be scheduled in order A -> B";
//    System.out.println("Test Passed: Simple Dependency");
//    //TEST_END
//  }
//
//  // Test handling of resource constraints
//  private static void testResourceConstraint() {
//    DynamicTaskScheduler scheduler = new DynamicTaskScheduler(3);
//    scheduler.addTask("A", 3, 2, Arrays.asList());
//    scheduler.addTask("B", 2, 2, Arrays.asList());
//
//    //TEST
//    List<String> result = scheduler.scheduleTasks();
//    assert result.size() == 2 && !result.get(0).equals(result.get(1)) : "Failed: Task B should wait until A completes due to resource limit";
//    System.out.println("Test Passed: Resource Constraint");
//    //TEST_END
//  }
//
//  // Test multiple dependencies
//  private static void testMultipleDependencies() {
//    DynamicTaskScheduler scheduler = new DynamicTaskScheduler(10);
//    scheduler.addTask("A", 3, 3, Arrays.asList());
//    scheduler.addTask("B", 2, 3, Arrays.asList("A"));
//    scheduler.addTask("C", 1, 2, Arrays.asList("A"));
//    scheduler.addTask("D", 1, 1, Arrays.asList("B", "C"));
//
//    //TEST
//    List<String> result = scheduler.scheduleTasks();
//    assert result.indexOf("D") > result.indexOf("B") && result.indexOf("D") > result.indexOf("C") : "Failed: D should be after B and C";
//    System.out.println("Test Passed: Multiple Dependencies");
//    //TEST_END
//  }
//
//  // Test when no resources are left to allocate
//  private static void testNoResourcesLeft() {
//    DynamicTaskScheduler scheduler = new DynamicTaskScheduler(1);
//    scheduler.addTask("A", 1, 1, Arrays.asList());
//    scheduler.addTask("B", 1, 2, Arrays.asList());
//
//    //TEST
//    try {
//      List<String> result = scheduler.scheduleTasks();
//      assert !result.contains("B") : "Failed: B should not be scheduled due to insufficient resources";
//      System.out.println("Test Passed: No Resources Left");
//    } catch (Exception e) {
//      System.out.println("Test Failed: Exception caught - " + e.getMessage());
//    }
//    //TEST_END
//  }
//
//  // Test scenario where all tasks can be scheduled without delay
//  private static void testAllTasksSchedulable() {
//    DynamicTaskScheduler scheduler = new DynamicTaskScheduler(20);
//    scheduler.addTask("A", 2, 5, Arrays.asList());
//    scheduler.addTask("B", 2, 5, Arrays.asList("A"));
//    scheduler.addTask("C", 2, 5, Arrays.asList("B"));
//
//    //TEST
//    List<String> result = scheduler.scheduleTasks();
//    assert result.equals(Arrays.asList("A", "B", "C")) : "Failed: All tasks should be scheduled without delay";
//    System.out.println("Test Passed: All Tasks Schedulable");
//    //TEST_END
//  }
//}

import com.powem.inv.algos.ClassRoomSeating;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
  public static void main(String[] args) {
    testBasicPreferences();
    testNoPreferences();
    testCircularPreferences();
    testUnconnectedGroups();
    testSingleStudent();
  }

  // Test simple linear preferences
  private static void testBasicPreferences() {
    Map<String, List<String>> preferences = new HashMap<>();
    preferences.put("Alice", Arrays.asList("Bob"));
    preferences.put("Bob", Arrays.asList("Alice", "Charlie"));
    preferences.put("Charlie", Arrays.asList("Bob"));

    ClassRoomSeating seating = new ClassRoomSeating(preferences);
    List<String> arrangement = seating.arrangeSeats();

    //TEST
    assert arrangement.indexOf("Alice") == arrangement.indexOf("Bob") - 1 || arrangement.indexOf("Alice") == arrangement.indexOf("Bob") + 1 : "Alice should be next to Bob";
    assert arrangement.indexOf("Bob") == arrangement.indexOf("Charlie") - 1 || arrangement.indexOf("Bob") == arrangement.indexOf("Charlie") + 1 : "Bob should be next to Charlie";
    System.out.println("Test Passed: Basic preferences are satisfied.");
    //TEST_END
  }

  // Test with no preferences
  private static void testNoPreferences() {
    Map<String, List<String>> preferences = new HashMap<>();
    preferences.put("Alice", new ArrayList<>());
    preferences.put("Bob", new ArrayList<>());

    ClassRoomSeating seating = new ClassRoomSeating(preferences);
    List<String> arrangement = seating.arrangeSeats();

    //TEST
    assert arrangement.contains("Alice") && arrangement.contains("Bob") : "All students should be in the arrangement.";
    System.out.println("Test Passed: No preferences handled correctly.");
    //TEST_END
  }

  // Test circular preferences
  private static void testCircularPreferences() {
    Map<String, List<String>> preferences = new HashMap<>();
    preferences.put("Alice", Arrays.asList("Bob"));
    preferences.put("Bob", Arrays.asList("Charlie"));
    preferences.put("Charlie", Arrays.asList("Alice"));

    ClassRoomSeating seating = new ClassRoomSeating(preferences);
    List<String> arrangement = seating.arrangeSeats();

    //TEST
    boolean circular = arrangement.indexOf("Alice") == arrangement.indexOf("Bob") - 1 || arrangement.indexOf("Alice") == arrangement.indexOf("Charlie") + 1;
    assert circular : "Circular preferences should form a closed loop.";
    System.out.println("Test Passed: Circular preferences are satisfied.");
    //TEST_END
  }

  // Test unconnected groups
  private static void testUnconnectedGroups() {
    Map<String, List<String>> preferences = new HashMap<>();
    preferences.put("Alice", Arrays.asList("Bob"));
    preferences.put("Bob", Arrays.asList("Alice"));
    preferences.put("Charlie", Arrays.asList("David"));
    preferences.put("David", Arrays.asList("Charlie"));

    ClassRoomSeating seating = new ClassRoomSeating(preferences);
    List<String> arrangement = seating.arrangeSeats();

    //TEST
    assert arrangement.contains("Alice") && arrangement.contains("Charlie") : "All groups should be included in the arrangement.";
    System.out.println("Test Passed: Unconnected groups are correctly placed.");
    //TEST_END
  }

  // Test single student scenario
  private static void testSingleStudent() {
    Map<String, List<String>> preferences = new HashMap<>();
    preferences.put("Alice", new ArrayList<>());

    ClassRoomSeating seating = new ClassRoomSeating(preferences);
    List<String> arrangement = seating.arrangeSeats();

    //TEST
    assert arrangement.contains("Alice") : "Single student should be in the arrangement.";
    System.out.println("Test Passed: Single student handled correctly.");
    //TEST_END
  }
}

