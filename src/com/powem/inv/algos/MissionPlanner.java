//Problem: Space Mission Planning Optimization
//Problem Statement:
//Design an advanced algorithm for optimizing the scheduling and resource allocation of multiple space missions. Each
// mission has a specific set of tasks that must be completed in sequence, and each task requires certain resources
// (e.g., astronauts, equipment) for a specified duration. The goal is to minimize the overall mission completion
// time while ensuring efficient resource utilization and avoiding conflicts.
//
//Objective:
//Develop a method that schedules the tasks for multiple space missions, optimizes resource allocation, and minimizes
// overall mission completion time. The solution should handle complex dependencies between tasks and ensure that
// resource constraints are respected.
//
//Each mission consists of a sequence of tasks.
//Each task requires specific resources for a given duration.
//Resources are limited and shared across multiple missions.
//Tasks within a mission must be completed in sequence, but tasks from different missions can be interleaved.
//The algorithm should dynamically adjust the schedule based on resource availability and task dependencies.
//

//Implement the MissionPlanner class that schedules tasks for multiple space missions, optimizes resource allocation,
// and minimizes overall mission completion time.
//
//Function Signature Suggestion:
//public class MissionPlanner {
//    public MissionPlanner(List<Mission> missions, List<Resource> resources);
//    public List<ScheduledTask> generateSchedule();
//}


package com.powem.inv.algos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class MissionPlanner {
  private final List<Mission> missions;
  private final List<Resource> resources;
  private final Map<String, Integer> resourceAvailability;
  private final List<ScheduledTask> schedule;

  public MissionPlanner(List<Mission> missions, List<Resource> resources) {
    this.missions = missions;
    this.resources = resources;
    this.resourceAvailability = new HashMap<>();
    for (Resource resource : resources) {
      resourceAvailability.put(resource.name, resource.quantity);
    }
    this.schedule = new ArrayList<>();
  }

  public List<ScheduledTask> generateSchedule() {
    PriorityQueue<Task> taskQueue = new PriorityQueue<>(Comparator.comparingInt(task -> task.endTime));
    for (Mission mission : missions) {
      taskQueue.addAll(mission.tasks);
    }

    while (!taskQueue.isEmpty()) {
      Task currentTask = taskQueue.poll();
      if (canAllocateResources(currentTask)) {
        allocateResources(currentTask);
        scheduleTask(currentTask);
      } else {
        taskQueue.offer(currentTask);
      }
    }

    return schedule;
  }

  private boolean canAllocateResources(Task task) {
    for (Map.Entry<String, Integer> entry : task.resourceRequirements.entrySet()) {
      String resourceName = entry.getKey();
      int requiredAmount = entry.getValue();
      if (resourceAvailability.getOrDefault(resourceName, 0) < requiredAmount) {
        return false;
      }
    }
    return true;
  }

  private void allocateResources(Task task) {
    for (Map.Entry<String, Integer> entry : task.resourceRequirements.entrySet()) {
      String resourceName = entry.getKey();
      int requiredAmount = entry.getValue();
      resourceAvailability.put(resourceName, resourceAvailability.get(resourceName) - requiredAmount);
    }
  }

  private void scheduleTask(Task task) {
    schedule.add(new ScheduledTask(task.missionId, task.taskId, task.startTime, task.endTime, new HashMap<>(task.resourceRequirements)));
    releaseResources(task);
  }

  private void releaseResources(Task task) {
    for (Map.Entry<String, Integer> entry : task.resourceRequirements.entrySet()) {
      String resourceName = entry.getKey();
      int requiredAmount = entry.getValue();
      resourceAvailability.put(resourceName, resourceAvailability.get(resourceName) + requiredAmount);
    }
  }

  public static class Mission {
    String missionId;
    List<Task> tasks;

    public Mission(String missionId, List<Task> tasks) {
      this.missionId = missionId;
      this.tasks = tasks;
    }
  }

  public static class Task {
    String missionId;
    String taskId;
    int startTime;
    int endTime;
    Map<String, Integer> resourceRequirements;

    public Task(String missionId, String taskId, int startTime, int endTime, Map<String, Integer> resourceRequirements) {
      this.missionId = missionId;
      this.taskId = taskId;
      this.startTime = startTime;
      this.endTime = endTime;
      this.resourceRequirements = resourceRequirements;
    }
  }

  public static class Resource {
    public String name;
    public int quantity;

    public Resource(String name, int quantity) {
      this.name = name;
      this.quantity = quantity;
    }
  }

  public static class ScheduledTask {
    String missionId;
    String taskId;
    public int startTime;
    public int endTime;
    public Map<String, Integer> allocatedResources;

    public ScheduledTask(String missionId, String taskId, int startTime, int endTime, Map<String, Integer> allocatedResources) {
      this.missionId = missionId;
      this.taskId = taskId;
      this.startTime = startTime;
      this.endTime = endTime;
      this.allocatedResources = allocatedResources;
    }
  }
}

//TESTS
//import com.powem.inv.algos.MissionPlanner;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
//public class Main {
//  public static void main(String[] args) {
//    List<MissionPlanner.Resource> resources = Arrays.asList(
//            new MissionPlanner.Resource("Astronaut", 5),
//            new MissionPlanner.Resource("Equipment", 10)
//    );
//
//    List<MissionPlanner.Task> mission1Tasks = Arrays.asList(
//            new MissionPlanner.Task("Mission1", "Task1", 0, 5, Map.of("Astronaut", 2, "Equipment", 3)),
//            new MissionPlanner.Task("Mission1", "Task2", 5, 10, Map.of("Astronaut", 2, "Equipment", 2))
//    );
//    List<MissionPlanner.Task> mission2Tasks = Arrays.asList(
//            new MissionPlanner.Task("Mission2", "Task1", 0, 4, Map.of("Astronaut", 3, "Equipment", 4)),
//            new MissionPlanner.Task("Mission2", "Task2", 4, 8, Map.of("Astronaut", 3, "Equipment", 3))
//    );
//
//    List<MissionPlanner.Mission> missions = Arrays.asList(
//            new MissionPlanner.Mission("Mission1", mission1Tasks),
//            new MissionPlanner.Mission("Mission2", mission2Tasks)
//    );
//
//    MissionPlanner planner = new MissionPlanner(missions, resources);
//    List<MissionPlanner.ScheduledTask> schedule = planner.generateSchedule();
//
//    // TEST
//    assert schedule.size() == 4;
//    // TEST END
//
//    boolean noOverlap = true;
//    for (int i = 0; i < schedule.size(); i++) {
//      for (int j = i + 1; j < schedule.size(); j++) {
//        MissionPlanner.ScheduledTask task1 = schedule.get(i);
//        MissionPlanner.ScheduledTask task2 = schedule.get(j);
//        if (task1.endTime > task2.startTime && task2.endTime > task1.startTime) {
//          for (String resource : task1.allocatedResources.keySet()) {
//            if (task1.allocatedResources.get(resource) + task2.allocatedResources.getOrDefault(resource, 0) > resources.stream().filter(r -> r.name.equals(resource)).findFirst().get().quantity) {
//              noOverlap = false;
//              break;
//            }
//          }
//        }
//      }
//    }
//
//    //TEST
//    assert noOverlap;
//    // TEST END
//
//    boolean validAllocations = true;
//    for (MissionPlanner.ScheduledTask task : schedule) {
//      for (String resource : task.allocatedResources.keySet()) {
//        if (task.allocatedResources.get(resource) > resources.stream().filter(r -> r.name.equals(resource)).findFirst().get().quantity) {
//          validAllocations = false;
//          break;
//        }
//      }
//    }
//
//    //TEST
//    assert validAllocations;
//    // TEST END
//  }
//}
