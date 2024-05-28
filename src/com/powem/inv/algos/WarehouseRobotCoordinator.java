//Problem: Multi-Robot Coordination for Warehouse Automation
//Problem Statement:
//Develop a system for coordinating multiple robots in a warehouse to optimize the
//retrieval and storage of items. The system should manage real-time task assignments, avoid
//collisions, and optimize the overall efficiency of the warehouse operations. The robots should
//also be able to communicate and coordinate with each other to avoid congestion and deadlocks.
//
//Create a system that coordinates multiple robots to efficiently manage warehouse tasks, avoiding
//collisions, optimizing task completion times, and enabling inter-robot communication for
//coordination.
//
//The warehouse is represented as a grid.
//Each robot has a unique ID and can move in four directions (up, down, left, right).
//The system should support adding robots, assigning tasks, and managing robot movements.
//Implement collision avoidance, task optimization, and communication algorithms to avoid
//congestion and deadlocks.
//Function Signature:
//public class AdvancedWarehouseRobotCoordinator {
//    public void addRobot(int robotId, int x, int y);
//    public void assignTask(int robotId, int targetX, int targetY);
//    public void moveRobots();
//    public Map<Integer, int[]> getRobotPositions();
//    public void communicate();
//}


package com.powem.inv.algos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WarehouseRobotCoordinator {
  private Map<Integer, Robot> robots = new HashMap<>();
  private Set<String> occupiedPositions = new HashSet<>();
  private Map<Integer, int[]> tasks = new HashMap<>();

  public void addRobot(int robotId, int x, int y) {
    if (robots.containsKey(robotId) || occupiedPositions.contains(x + "," + y)) {
      throw new IllegalArgumentException("Robot with this ID already exists or position is occupied");
    }
    robots.put(robotId, new Robot(robotId, x, y));
    occupiedPositions.add(x + "," + y);
  }

  public void assignTask(int robotId, int targetX, int targetY) {
    if (!robots.containsKey(robotId)) {
      throw new IllegalArgumentException("Robot not found");
    }
    tasks.put(robotId, new int[]{targetX, targetY});
  }

  public void moveRobots() {
    for (int robotId : robots.keySet()) {
      Robot robot = robots.get(robotId);
      int[] target = tasks.getOrDefault(robotId, new int[]{robot.x, robot.y});
      occupiedPositions.remove(robot.x + "," + robot.y);
      robot.move(target[0], target[1]);
      occupiedPositions.add(robot.x + "," + robot.y);
    }
  }

  public Map<Integer, int[]> getRobotPositions() {
    Map<Integer, int[]> positions = new HashMap<>();
    for (Robot robot : robots.values()) {
      positions.put(robot.id, new int[]{robot.x, robot.y});
    }
    return positions;
  }

  public void communicate() {
    for (Robot robot : robots.values()) {
      for (Robot other : robots.values()) {
        if (robot.id != other.id && robot.isNearby(other)) {
          robot.avoidCollision(other);
        }
      }
    }
  }

  private class Robot {
    int id;
    int x, y;

    Robot(int id, int x, int y) {
      this.id = id;
      this.x = x;
      this.y = y;
    }

    void move(int targetX, int targetY) {
      if (x < targetX) x++;
      else if (x > targetX) x--;
      else if (y < targetY) y++;
      else if (y > targetY) y--;
    }

    boolean isNearby(Robot other) {
      return Math.abs(this.x - other.x) + Math.abs(this.y - other.y) == 1;
    }

    void avoidCollision(Robot other) {
      if (this.x == other.x) {
        if (this.y < other.y) this.y--;
        else this.y++;
      } else if (this.y == other.y) {
        if (this.x < other.x) this.x--;
        else this.x++;
      }
    }
  }
}

//import com.powem.inv.algos.WarehouseRobotCoordinator;
//import java.util.Arrays;
//import java.util.Map;
//
//public class Main {
//  public static void main(String[] args) {
//    WarehouseRobotCoordinator coordinator = new WarehouseRobotCoordinator();
//
//    /// TEST
//    coordinator.addRobot(1, 0, 0);
//    coordinator.addRobot(2, 5, 5);
//    coordinator.assignTask(1, 2, 2);
//    coordinator.assignTask(2, 3, 3);
//
//    Map<Integer, int[]> positions = coordinator.getRobotPositions();
//    assert Arrays.equals(positions.get(1), new int[]{0, 0});
//    assert Arrays.equals(positions.get(2), new int[]{5, 5});
//    // TEST_END
//
//    // TEST
//    try {
//      coordinator.addRobot(3, 0, 0);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      coordinator.addRobot(1, 6, 6);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    coordinator.moveRobots();
//    positions = coordinator.getRobotPositions();
//    assert Arrays.equals(positions.get(1), new int[]{1, 0}) || Arrays.equals(positions.get(1), new int[]{0, 1});
//    assert Arrays.equals(positions.get(2), new int[]{4, 5}) || Arrays.equals(positions.get(2), new int[]{5, 4});
//    // TEST_END
//
//    // TEST
//    coordinator.assignTask(1, 5, 5);
//    coordinator.assignTask(2, 5, 5);
//    for (int i = 0; i < 5; i++) {
//      coordinator.moveRobots();
//      coordinator.communicate();
//    }
//    positions = coordinator.getRobotPositions();
//    assert !Arrays.equals(positions.get(1), positions.get(2));
//    // TEST_END
//
//    // TEST
//    try {
//      coordinator.assignTask(3, 2, 2);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    coordinator.addRobot(3, 1, 1);
//    coordinator.addRobot(4, 4, 4);
//    coordinator.assignTask(3, 6, 6);
//    coordinator.assignTask(4, 0, 0);
//
//    for (int i = 0; i < 10; i++) {
//      coordinator.moveRobots();
//      coordinator.communicate();
//    }
//
//    positions = coordinator.getRobotPositions();
//    assert !Arrays.equals(positions.get(3), positions.get(4));
//    assert positions.get(3)[0] != positions.get(4)[0] || positions.get(3)[1] != positions.get(4)[1];
//    // TEST_END
//
//    // TEST
//    Map<Integer, int[]> initialPositions = coordinator.getRobotPositions();
//    for (int i = 0; i < 10; i++) {
//      coordinator.moveRobots();
//    }
//    positions = coordinator.getRobotPositions();
//    assert !initialPositions.equals(positions);
//    // TEST_END
//
//    // TEST
//    coordinator.assignTask(1, 7, 7);
//    coordinator.assignTask(2, 7, 7);
//    for (int i = 0; i < 10; i++) {
//      coordinator.moveRobots();
//      coordinator.communicate();
//    }
//    positions = coordinator.getRobotPositions();
//    assert !Arrays.equals(positions.get(1), positions.get(2));
//    // TEST_END
//  }
//}
