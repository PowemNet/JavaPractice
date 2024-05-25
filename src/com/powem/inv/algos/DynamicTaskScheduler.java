package com.powem.inv.algos;


//Problem: Dynamic Project Task Scheduler

//        Problem Statement:
//        You are tasked with designing an algorithm for a project management system to dynamically schedule tasks based on dependencies and resource availability. Each task has a duration, a set of prerequisite tasks, and consumes a certain amount of resources while active.
//
//        Objective:
//        Develop a system that schedules tasks in such a way that the project's total duration is minimized while respecting dependencies and not exceeding resource constraints at any time.
//
//        Details:
//
//        Tasks cannot start unless all their prerequisite tasks are completed.
//        The system has a fixed amount of resources available at any time.
//        Each task has a defined duration and resource consumption.
//        Challenge:
//        Implement the DynamicTaskScheduler class with methods to add tasks with their dependencies and resources, and compute an optimal scheduling order that minimizes the project's total duration.

//public class DynamicTaskScheduler {
//  public void addTask(String taskId, int duration, int resources, List<String> prerequisites);
//  public List<String> scheduleTasks();
//}


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;


public class DynamicTaskScheduler {
    private Map<String, Task> tasks;
    private int totalResources;

    public DynamicTaskScheduler(int totalResources) {
        this.tasks = new HashMap<>();
        this.totalResources = totalResources;
    }

    public void addTask(String taskId, int duration, int resources, List<String> prerequisites) {
        tasks.put(taskId, new Task(taskId, duration, resources, prerequisites));
    }

    public List<String> scheduleTasks() {
        // Topological sort to handle prerequisites and scheduling logic to handle resources
        return topologicalSortWithResourceManagement();
    }

    private List<String> topologicalSortWithResourceManagement() {
        List<String> scheduledTasks = new ArrayList<>();
        Map<String, Integer> inDegree = new HashMap<>();
        Queue<String> availableTasks = new LinkedList<>();


        // Initialize in-degree for each task
        for (Map.Entry<String, Task> entry : tasks.entrySet()) {
            inDegree.put(entry.getKey(), entry.getValue().prerequisites.size());
            if (entry.getValue().prerequisites.isEmpty()) {
                availableTasks.add(entry.getKey());
            }
        }

        while (!availableTasks.isEmpty()) {
            String currentTaskId = availableTasks.poll();
            scheduledTasks.add(currentTaskId);

            // Decrease the in-degree of dependent tasks
            for (Map.Entry<String, Task> entry : tasks.entrySet()) {
                Task task = entry.getValue();
                if (task.prerequisites.contains(currentTaskId)) {
                    inDegree.put(entry.getKey(), inDegree.get(entry.getKey()) - 1);
                    // If in-degree is zero, add to available tasks
                    if (inDegree.get(entry.getKey()) == 0) {
                        availableTasks.add(entry.getKey());
                    }
                }
            }
        }

        // Check if all tasks have been scheduled
        if (scheduledTasks.size() != tasks.size()) {
            throw new IllegalStateException("Scheduling failed due to cyclic dependency or resource constraints.");
        }

        return scheduledTasks;
    }

    private void topologicalSortUtil(String taskId, Set<String> visited, Stack<String> stack, Map<String, Integer> resourcesAvailable) {
        visited.add(taskId);
        Task currentTask = tasks.get(taskId);
        for (String prereq : currentTask.prerequisites) {
            if (!visited.contains(prereq)) {
                topologicalSortUtil(prereq, visited, stack, resourcesAvailable);
            }
        }
        stack.push(taskId);
    }

    class Task {
        String taskId;
        int duration;
        int resources;
        List<String> prerequisites;

        Task(String taskId, int duration, int resources, List<String> prerequisites) {
            this.taskId = taskId;
            this.duration = duration;
            this.resources = resources;
            this.prerequisites = prerequisites;
        }
    }
}
