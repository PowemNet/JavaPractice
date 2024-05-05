package com.powem.inv.unionfind;
//original[medium]: https://leetcode.com/problems/the-earliest-moment-when-everyone-become-friends/description/

// Build a credit card fraud prevention system which detects possible fraud once
// transactionIds belonging to a given user are detected to be connected, yet triggered from different
// locations.
//
// The function, called isFraudDetected will take an input of a 2D array of items. Each item will be an array with a timestamp,
// transactionId1 and transactionId2. To check that the transactionIds are related,
// use a UnionFind data structure to find relationships. Create the UnionFind class.

// Implementation:

// Given n combinations, if all n transactions make a connection,
// then the function should return true (fraud is detected).
// Else it should return false.
//
// Constraint:
// 1- The timestamps are in chronological order, starting from 1
// 2- Combinations (n) have to be 3 or more

import java.util.Arrays;
import java.util.Comparator;

public class UnionFraudDetector {

  public static boolean isFraudDetected(int[][] combinations, int n) {
    if (n < 3) {
      throw new IllegalArgumentException("n must be greater or equal to 3");
    }

    if (combinations == null || combinations.length == 0) {
      throw new IllegalArgumentException("Combinations must not be null or empty");
    }

    int groupCount = n;
    UnionFind uf = new UnionFind(n);
    Integer timestampWhenAllConnectionsAreMade = null;

    Arrays.sort(combinations, new Comparator<int[]>() {
      @Override
      public int compare(int[] combination1, int[] combination2) {
        Integer timestamp1 = combination1[0];
        Integer timestamp2 = combination2[0];
        return timestamp1.compareTo(timestamp2);
      }
    });

    for (int[] combination : combinations) {
      int timestamp = combination[0], transaction1 = combination[1], transaction2 = combination[2];

      if (uf.union(transaction1, transaction2)) {
        groupCount -= 1;
      }

      if (groupCount == 1) {
        timestampWhenAllConnectionsAreMade = timestamp;
      }
    }

    if (timestampWhenAllConnectionsAreMade == null) {
      return false;
    }
    return true;
  }
}

class UnionFind {

  private int[] group;
  private int[] rank;

  public UnionFind(int size) {
    this.group = new int[size];
    this.rank = new int[size];
    for (int transaction = 0; transaction < size; ++transaction) {
      this.group[transaction] = transaction;
      this.rank[transaction] = 0;
    }
  }

  public boolean union(int a, int b) {
    int groupA = this.find(a);
    int groupB = this.find(b);
    boolean isMerged = false;

    if (groupA == groupB) {
      return isMerged;
    }

    isMerged = true;
    if (this.rank[groupA] > this.rank[groupB]) {
      this.group[groupB] = groupA;
    } else if (this.rank[groupA] < this.rank[groupB]) {
      this.group[groupA] = groupB;
    } else {
      this.group[groupA] = groupB;
      this.rank[groupB] += 1;
    }
    return isMerged;
  }

  public int find(int transaction) {
    if (this.group[transaction] != transaction) {
      this.group[transaction] = this.find(this.group[transaction]);
    }
    return this.group[transaction];
  }
}
