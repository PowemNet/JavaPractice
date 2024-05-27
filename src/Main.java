import com.powem.inv.algos.CellularAutomaton;

public class Main {
    public static void main(String[] args) {
        CellularAutomaton automaton = new CellularAutomaton();

        // TEST
        int[] initialState = {0, 1, 0, 0, 1, 0, 1, 1, 0};
        int[][] rules = {
            {0, 0}, {1, 1}, {0, 1}, {1, 1},
            {0, 0}, {1, 0}, {0, 1}, {1, 0}
        };
        int numGenerations = 5;
        int[][] generations = automaton.simulate(initialState, rules, numGenerations);
        assert generations.length == numGenerations;
        // TEST_END

        // TEST
        assert generations[0].length == initialState.length;
        // TEST_END

        // TEST
        int[] state = {0, 1, 0, 1, 1, 0, 0, 1};
        int aliveCellCount = automaton.getAliveCellCount(state);
        assert aliveCellCount == 4;
        // TEST_END

        // TEST
        try {
            automaton.simulate(null, rules, numGenerations);
            assert false;
        } catch (IllegalArgumentException e) {
            assert true;
        }
        // TEST_END

        // TEST
        try {
            automaton.simulate(initialState, new int[][]{{0}, {1}}, numGenerations);
            assert false;
        } catch (IllegalArgumentException e) {
            assert true;
        }
        // TEST_END

        // TEST
        try {
            automaton.simulate(initialState, rules, -1);
            assert false;
        } catch (IllegalArgumentException e) {
            assert true;
        }
        // TEST_END

        // TEST
        try {
            automaton.getAliveCellCount(null);
            assert false;
        } catch (IllegalArgumentException e) {
            assert true;
        }
        // TEST_END
    }
}