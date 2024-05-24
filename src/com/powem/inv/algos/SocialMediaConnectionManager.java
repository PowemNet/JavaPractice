package com.powem.inv.algos;
//Advanced Social Media Connection Management
//Objective:
//Develop a system using Java to manage user connections on a social media platform,
// efficiently handling queries about friendships and shared connections using graph data structures.
//
//You are tasked with building a robust system for a social media platform that
// manages user relationships. The system should allow for efficiently checking direct
// friendships, discovering friends of friends, and finding common connections between users.
//
//Create the SocialMediaConnectionManager class that uses a HashMap to represent a network
// of users and their connections. The class should support adding new friendships, checking
// if two users are directly connected, and finding both indirect connections and common friends.
//
//Detailed Method Requirements:
//
//addConnection(String userId1, String userId2): Establishes a bidirectional friendship between two users.
//
//boolean areFriends(String userId1, String userId2): Checks if two users are directly connected.
//
//Set<String> getFriendsOfFriends(String userId): Returns a set of users who are two degrees of separation away.
//
//Set<String> getCommonConnections(String userId1, String userId2): Finds common friends between two users.


import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SocialMediaConnectionManager {
  private Map<String, Set<String>> userConnections;

  public SocialMediaConnectionManager() {
    userConnections = new HashMap<>();
  }

  public void addConnection(String userId1, String userId2) {
    if (userId1 == null || userId1.isEmpty() || userId2 == null || userId2.isEmpty()) {
      throw new IllegalArgumentException("User IDs cannot be null or empty");
    }
    userConnections.computeIfAbsent(userId1, k -> new HashSet<>()).add(userId2);
    userConnections.computeIfAbsent(userId2, k -> new HashSet<>()).add(userId1);
  }

  public boolean areFriends(String userId1, String userId2) {
    if (userId1 == null || userId1.isEmpty() || userId2 == null || userId2.isEmpty()) {
      throw new IllegalArgumentException("User IDs cannot be null or empty");
    }
    Set<String> friends = userConnections.get(userId1);
    return friends != null && friends.contains(userId2);
  }

  public Set<String> getFriendsOfFriends(String userId) {
    if (userId == null || userId.isEmpty()) {
      throw new IllegalArgumentException("User ID cannot be null or empty");
    }
    Set<String> result = new HashSet<>();
    Set<String> directFriends = userConnections.getOrDefault(userId, Collections.emptySet());
    for (String friend : directFriends) {
      Set<String> friendsOfFriend = userConnections.getOrDefault(friend, Collections.emptySet());
      result.addAll(friendsOfFriend);
    }
    result.remove(userId);
    return result;
  }

  public Set<String> getCommonConnections(String userId1, String userId2) {
    if (userId1 == null || userId1.isEmpty() || userId2 == null || userId2.isEmpty()) {
      throw new IllegalArgumentException("User IDs cannot be null or empty");
    }
    Set<String> friends1 = userConnections.getOrDefault(userId1, Collections.emptySet());
    Set<String> friends2 = userConnections.getOrDefault(userId2, Collections.emptySet());
    Set<String> common = new HashSet<>(friends1);
    common.retainAll(friends2);
    return common;
  }
}

//import java.util.Set;

//public class Main {
//  public static void main(String[] args) {
//    SocialMediaConnectionManager manager = new SocialMediaConnectionManager();
//
//    //TEST
//    manager.addConnection("user1", "user2");
//    manager.addConnection("user2", "user3");
//    //TEST_END
//
//    //TEST
//    try {
//      manager.addConnection("user4", null);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    assert manager.areFriends("user1", "user2");
//    //TEST_END
//
//    //TEST
//    assert !manager.areFriends("user1", "user3");
//    //TEST_END
//
//    //TEST
//    try {
//      manager.areFriends("user1", null);
//      ;
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    try {
//      manager.areFriends("", "user2");
//      ;
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    Set<String> friendsOfFriends = manager.getFriendsOfFriends("user1");
//    assert friendsOfFriends.contains("user3");
//    //TEST_END
//
//    //TEST
//    try {
//      manager.getFriendsOfFriends(null);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    manager.addConnection("user3", "user4");
//    manager.addConnection("user2", "user4");
//    Set<String> commonConnections = manager.getCommonConnections("user2", "user3");
//    assert commonConnections.contains("user4");
//  }
//}
