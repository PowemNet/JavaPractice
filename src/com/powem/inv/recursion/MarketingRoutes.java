package com.powem.inv.recursion;
//original[easy]: https://leetcode.com/problems/climbing-stairs/

//To get to a certain amount of profit. a company has identified several strategies to use.
//Each strategy increases the profit earned by a given constant about, say k.
//Create a function called totalRoutes, which calculated the total number of combinations to get to the highest profit.
//The function takes n as the number of levels from start (0 profit) to finish. For each level, one can implement one strategy. At any
//time, a maximum of two strategies can be implemented.
//For example totalRoutes(2) given output of 2, which means there are 2 routes to get to the highest profit:
//Either implement one strategy to get to one level, followed by another strategy to get to the next (and final level)
//Or, implement two strategies at a time, to cover both levels at a go. These given a total of two routes taken.

public class MarketingRoutes {

    public static int totalRoutes(int n) {
      return useStrategy(0, n);
    }
    public static int useStrategy(int i, int n) {
      if (i > n) {
        return 0;
      }
      if (i == n) {
        return 1;
      }
      return useStrategy(i + 1, n) + useStrategy(i + 2, n);
    }
}

