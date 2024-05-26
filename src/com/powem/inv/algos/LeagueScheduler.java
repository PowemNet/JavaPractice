//Problem: Advanced Sports Team Scheduling
//Problem Statement:
//Design an algorithm for scheduling a sports league's matches. The league consists of multiple teams, and the
// goal is to create a schedule that ensures fairness, minimizes travel distances, and accommodates venue availability
// and broadcast preferences.
//
//Objective:
//Develop a method that generates an optimized schedule for the league's matches, taking into account travel distances,
// venue availability, and broadcast preferences. The solution should ensure that each team plays against every other
// team at least once, with a balanced home and away distribution.
//
//The league consists of n teams.
//Each team has a home venue with specific availability constraints.
//Travel distances between venues are provided.
//Broadcast preferences specify preferred times for high-profile matches.
//The algorithm should generate a schedule that balances home and away games, minimizes travel, and respects venue and
// broadcast constraints.
//
// Implement the LeagueScheduler class that generates an optimized schedule for the league's matches.
//Function Signature Suggestion:
//public class LeagueScheduler {
//    public LeagueScheduler(int numberOfTeams, int[][] travelDistances, Map<Integer, List<Integer>> venueAvailability,
//    List<BroadcastPreference> broadcastPreferences);
//    public List<Match> generateSchedule();
//}

package com.powem.inv.algos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LeagueScheduler {
  private final int numberOfTeams;
  private final int[][] travelDistances;
  private final Map<Integer, List<Integer>> venueAvailability;
  private final List<BroadcastPreference> broadcastPreferences;
  private final List<Match> schedule;

  public LeagueScheduler(int numberOfTeams, int[][] travelDistances, Map<Integer, List<Integer>> venueAvailability, List<BroadcastPreference> broadcastPreferences) {
    if (numberOfTeams <= 1 || travelDistances == null || travelDistances.length != numberOfTeams || venueAvailability == null || broadcastPreferences == null) {
      throw new IllegalArgumentException("Invalid input");
    }
    this.numberOfTeams = numberOfTeams;
    this.travelDistances = travelDistances;
    this.venueAvailability = venueAvailability;
    this.broadcastPreferences = broadcastPreferences;
    this.schedule = new ArrayList<>();
  }

  public List<Match> generateSchedule() {
    for (int home = 0; home < numberOfTeams; home++) {
      for (int away = 0; away < numberOfTeams; away++) {
        if (home != away) {
          schedule.add(new Match(home, away, -1)); // -1 indicates unscheduled
        }
      }
    }

    assignInitialTimes();

    optimizeScheduleUsingSimulatedAnnealing();

    return schedule;
  }

  private void assignInitialTimes() {
    int currentTime = 0;
    for (Match match : schedule) {
      match.time = currentTime++;
    }
  }

  private void optimizeScheduleUsingSimulatedAnnealing() {
    double temperature = 1000;
    double coolingRate = 0.995;
    Random random = new Random();

    List<Match> currentSolution = new ArrayList<>(schedule);
    List<Match> bestSolution = new ArrayList<>(schedule);
    double bestCost = calculateCost(bestSolution);

    while (temperature > 1) {
      List<Match> newSolution = new ArrayList<>(currentSolution);

      int index1 = random.nextInt(newSolution.size());
      int index2 = random.nextInt(newSolution.size());

      int tempTime = newSolution.get(index1).time;
      newSolution.get(index1).time = newSolution.get(index2).time;
      newSolution.get(index2).time = tempTime;

      double currentCost = calculateCost(currentSolution);
      double newCost = calculateCost(newSolution);

      if (acceptanceProbability(currentCost, newCost, temperature) > random.nextDouble()) {
        currentSolution = new ArrayList<>(newSolution);
      }

      if (newCost < bestCost) {
        bestSolution = new ArrayList<>(newSolution);
        bestCost = newCost;
      }

      temperature *= coolingRate;
    }

    schedule.clear();
    schedule.addAll(bestSolution);
  }

  private double acceptanceProbability(double currentCost, double newCost, double temperature) {
    if (newCost < currentCost) {
      return 1.0;
    }
    return Math.exp((currentCost - newCost) / temperature);
  }

  private double calculateCost(List<Match> solution) {
    double cost = 0;

    for (Match match : solution) {
      cost += travelDistances[match.home][match.away];
    }

    for (BroadcastPreference preference : broadcastPreferences) {
      boolean preferenceMet = false;
      for (Match match : solution) {
        if ((match.home == preference.team1 && match.away == preference.team2) ||
                (match.home == preference.team2 && match.away == preference.team1)) {
          if (match.time == preference.preferredTime) {
            preferenceMet = true;
            break;
          }
        }
      }
      if (!preferenceMet) {
        cost += 100;
      }
    }

    for (int team = 0; team < numberOfTeams; team++) {
      List<Integer> availableTimes = venueAvailability.get(team);
      for (Match match : solution) {
        if (match.home == team && !availableTimes.contains(match.time)) {
          cost += 50;
        }
      }
    }

    return cost;
  }

  public static class Match {
    public int home;
    public int away;
    public int time;

    public Match(int home, int away, int time) {
      this.home = home;
      this.away = away;
      this.time = time;
    }

    @Override
    public String toString() {
      return "Match{" +
              "home=" + home +
              ", away=" + away +
              ", time=" + time +
              '}';
    }
  }

  public static class BroadcastPreference {
    int team1;
    int team2;
    int preferredTime;

    public BroadcastPreference(int team1, int team2, int preferredTime) {
      this.team1 = team1;
      this.team2 = team2;
      this.preferredTime = preferredTime;
    }
  }
}

//TESTS
//import com.powem.inv.algos.LeagueScheduler;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class Main {
//  public static void main(String[] args) {
//    int numberOfTeams = 4;
//    int[][] travelDistances = {
//            {0, 10, 20, 30},
//            {10, 0, 15, 25},
//            {20, 15, 0, 35},
//            {30, 25, 35, 0}
//    };
//    Map<Integer, List<Integer>> venueAvailability = new HashMap<>();
//    venueAvailability.put(0, Arrays.asList(1, 2, 3));
//    venueAvailability.put(1, Arrays.asList(1, 2, 3));
//    venueAvailability.put(2, Arrays.asList(1, 2, 3));
//    venueAvailability.put(3, Arrays.asList(1, 2, 3));
//    List<LeagueScheduler.BroadcastPreference> broadcastPreferences = Arrays.asList(
//            new LeagueScheduler.BroadcastPreference(0, 1, 2),
//            new LeagueScheduler.BroadcastPreference(2, 3, 1)
//    );
//
//    LeagueScheduler scheduler = new LeagueScheduler(numberOfTeams, travelDistances, venueAvailability, broadcastPreferences);
//    List<LeagueScheduler.Match> schedule = scheduler.generateSchedule();
//
//    // TEST
//    assert schedule.size() == (numberOfTeams * (numberOfTeams - 1));
//    // TEST END
//
//    // TEST
//    assert schedule.stream().allMatch(match -> match.time != -1);
//    // TEST END
//
//    // TEST
//    assert schedule.stream().allMatch(match -> match.home != match.away);
//    // TEST END
//
//    // TEST
//    assert schedule.stream().allMatch(match -> match.home >= 0 && match.home < numberOfTeams);
//    // TEST END
//
//    // TEST
//    assert schedule.stream().allMatch(match -> match.away >= 0 && match.away < numberOfTeams);
//    // TEST END
//  }
//}
