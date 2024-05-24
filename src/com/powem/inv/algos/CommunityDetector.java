package com.powem.inv.algos;
//Day 10: Community Detection in Social Networks
//Objective:
//Develop a system that uses graph algorithms to identify and segment communities within a social
//network based on user interactions and relationships.
//
//Create a system for a social network that can detect and categorize different communities
//or clusters of users based on their interactions and friendships. This system should help in targeted advertising,
//content delivery, and enhancing user engagement by promoting community-specific activities and content.
//
//Implement the CommunityDetector class that uses graph structures to represent the social network and applies the Louvain
//method for community detection.
//
//Helper Method Requirements:
//
//addUser(String userId, List<String> friends): Adds a user and their list of friends to the network.
//
//Map<String, Integer> detectCommunities(): Applies the Louvain method to detect communities and returns a mapping of user IDs
//to their respective community identifiers.
//
//List<String> getCommunityMembers(int communityId): Retrieves a list of all users belonging to a specific community.


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class CommunityDetector {
  private Map<String, Set<String>> userGraph;
  private Map<String, Integer> communityMap;

  public CommunityDetector() {
    userGraph = new HashMap<>();
    communityMap = new HashMap<>();
  }

  public void addUser(String userId, List<String> friends) {
    if (userId == null || userId.isEmpty() || friends == null) {
      throw new IllegalArgumentException("Inputs cannot be null or empty.");
    }
    userGraph.putIfAbsent(userId, new HashSet<>());
    for (String friend : friends) {
      if (friend == null || friend.isEmpty()) {
        continue;
      }
      userGraph.get(userId).add(friend);
      userGraph.putIfAbsent(friend, new HashSet<>());
      userGraph.get(friend).add(userId);
    }
  }

  public Map<String, Integer> detectCommunities() {
    int communityId = 0;
    for (String user : userGraph.keySet()) {
      if (!communityMap.containsKey(user)) {
        communityMap.put(user, communityId);
        exploreCommunity(user, communityId);
        communityId++;
      }
    }
    return communityMap;
  }

  private void exploreCommunity(String user, int communityId) {
    Queue<String> queue = new LinkedList<>();
    queue.add(user);

    while (!queue.isEmpty()) {
      String currentUser = queue.poll();
      for (String friend : userGraph.get(currentUser)) {
        if (!communityMap.containsKey(friend)) {
          communityMap.put(friend, communityId);
          queue.add(friend);
        }
      }
    }
  }

  public List<String> getCommunityMembers(int communityId) {
    if (communityId < 0) {
      throw new IllegalArgumentException("invalid communityId");
    }
    List<String> members = new ArrayList<>();
    for (Map.Entry<String, Integer> entry : communityMap.entrySet()) {
      if (entry.getValue() == communityId) {
        members.add(entry.getKey());
      }
    }
    return members;
  }
}

//import com.powem.inv.algos.CommunityDetector;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
//public class Main {
//  public static void main(String[] args) {
//    CommunityDetector detector = new CommunityDetector();
//
//    //TEST
//    detector.addUser("Alice", Arrays.asList("Bob", "Charlie"));
//    detector.addUser("Bob", Arrays.asList("Alice", "David"));
//    detector.addUser("Charlie", Arrays.asList("Alice"));
//    detector.addUser("David", Arrays.asList("Bob"));
//    detector.addUser("Eve", Arrays.asList("Frank"));
//    detector.addUser("Frank", Arrays.asList("Eve"));
//
//    Map<String, Integer> communities = detector.detectCommunities();
//    assert communities.get("Alice").equals(communities.get("Charlie"));
//    //TEST_END
//
//    //TEST
//    assert communities.get("Alice").equals(communities.get("David"));
//    //TEST_END
//
//    //TEST
//    assert !communities.get("Alice").equals(communities.get("Eve"));
//    //TEST_END
//
//    //TEST
//    detector.addUser("Isolated", new ArrayList<>());
//    assert detector.detectCommunities().containsKey("Isolated");
//    //TEST_END
//
//    //TEST
//    try {
//      detector.addUser(null, Arrays.asList("Bob"));
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    try {
//      detector.addUser("Tom", null);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    List<String> community0 = detector.getCommunityMembers(0);
//    assert community0.contains("Alice") && community0.contains("Bob");
//    //TEST_END
//
//    //TEST
//    try {
//      detector.getCommunityMembers(-1);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//  }
//}
