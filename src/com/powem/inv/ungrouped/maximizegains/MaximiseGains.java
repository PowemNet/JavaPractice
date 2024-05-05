package com.powem.inv.ungrouped.maximizegains;
//original[easy]: https://leetcode.com/problems/best-time-to-buy-and-sell-stock/

//To know if it makes sense to study, a list of knowledge gains based on the day is provided.
//Create a function called maxKnowledgeGain which calculates the maximum knowledge which can be
//gained within a given number of days.
//If no knowledge can be gained in a cycle, return 0.
//for example: Input : knowledgeCycleNuggets : [8, 1, 3, 6]
// Output: 5. if you study on day 2, you'll have 5 nuggets of knowledge gained on day 4 (6-1)

public class MaximiseGains {

  public static int maxKnowledgeGain(int[] knowledgeCycleNuggets) {
    int minNugget = Integer.MAX_VALUE;
    int maxGains = 0;
    for (int i = 0; i < knowledgeCycleNuggets.length; i++) {
      if (knowledgeCycleNuggets[i] < minNugget) {
        minNugget = knowledgeCycleNuggets[i];
      } else if (knowledgeCycleNuggets[i] - minNugget > maxGains) {
        maxGains = knowledgeCycleNuggets[i] - minNugget;
      }
    }
    return maxGains;
  }
}

