package com.powem.inv.algos;
//Inspiration: Automation of cellular level operations
//
//Create a Java class called "CellularAutomaton" that simulates a one-dimensional cellular automaton based
// on a set of rules. The class should have the following methods:
//
//simulate(int[] initialState, int[][] rules, int numGenerations): Simulates the cellular automaton for
// a given number of generations based on the initial state and the rules. The method should take an integer
// array representing the initial state of the cells (0 for dead, 1 for alive), a 2D integer array representing
// the rules (where the first dimension represents the current state and its neighbors, and the second
// dimension represents the next state), and the number of generations to simulate. The method should return a
// 2D integer array representing the state of the cells at each generation.

//getAliveCellCount(int[] state): Counts the number of alive cells in a given state. The method should take an
//integer array representing the state of the cells and return the count of alive cells.
//Handle any potential errors, such as providing invalid initial state, rules, or number of generations,
// by throwing appropriate exceptions with meaningful error messages.

public class CellularAutomaton {
    public int[][] simulate(int[] initialState, int[][] rules, int numGenerations) {
        validateInput(initialState, rules, numGenerations);

        int numCells = initialState.length;
        int[][] generations = new int[numGenerations][numCells];
        generations[0] = initialState;

        for (int gen = 1; gen < numGenerations; gen++) {
            int[] prevGeneration = generations[gen - 1];
            int[] currentGeneration = new int[numCells];

            for (int i = 0; i < numCells; i++) {
                int left = i == 0 ? 0 : prevGeneration[i - 1];
                int center = prevGeneration[i];
                int right = i == numCells - 1 ? 0 : prevGeneration[i + 1];

                int ruleIndex = (left << 2) | (center << 1) | right;
                currentGeneration[i] = rules[ruleIndex][1];
            }

            generations[gen] = currentGeneration;
        }

        return generations;
    }

    public int getAliveCellCount(int[] state) {
        if (state == null) {
            throw new IllegalArgumentException("invalid input");
        }
        int count = 0;
        for (int cell : state) {
            if (cell == 1) {
                count++;
            }
        }
        return count;
    }

    private void validateInput(int[] initialState, int[][] rules, int numGenerations) {
        if (initialState == null || initialState.length == 0) {
            throw new IllegalArgumentException("Initial state cannot be null or empty");
        }

        if (rules == null || rules.length != 8 || rules[0].length != 2) {
            throw new IllegalArgumentException("Invalid rules");
        }

        if (numGenerations <= 0) {
            throw new IllegalArgumentException("Number of generations must be positive");
        }
    }
}

//TEST--

//import com.powem.inv.algos.CellularAutomaton;
//
//public class Main {
//    public static void main(String[] args) {
//        CellularAutomaton automaton = new CellularAutomaton();
//
//        // TEST
//        int[] initialState = {0, 1, 0, 0, 1, 0, 1, 1, 0};
//        int[][] rules = {
//            {0, 0}, {1, 1}, {0, 1}, {1, 1},
//            {0, 0}, {1, 0}, {0, 1}, {1, 0}
//        };
//        int numGenerations = 5;
//        int[][] generations = automaton.simulate(initialState, rules, numGenerations);
//        assert generations.length == numGenerations;
//        // TEST_END
//
//        // TEST
//        assert generations[0].length == initialState.length;
//        // TEST_END
//
//        // TEST
//        int[] state = {0, 1, 0, 1, 1, 0, 0, 1};
//        int aliveCellCount = automaton.getAliveCellCount(state);
//        assert aliveCellCount == 4;
//        // TEST_END
//
//        // TEST
//        try {
//            automaton.simulate(null, rules, numGenerations);
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        // TEST_END
//
//        // TEST
//        try {
//            automaton.simulate(initialState, new int[][]{{0}, {1}}, numGenerations);
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        // TEST_END
//
//        // TEST
//        try {
//            automaton.simulate(initialState, rules, -1);
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        // TEST_END
//
//        // TEST
//        try {
//            automaton.getAliveCellCount(null);
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        // TEST_END
//    }
//}

