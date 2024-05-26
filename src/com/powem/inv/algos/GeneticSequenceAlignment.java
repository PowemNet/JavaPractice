package com.powem.inv.algos;
//Genetic Sequence Alignment
//Problem:
//Develop a Java method to perform a simple genetic sequence alignment using a basic scoring mechanism. This method will help in comparing two DNA sequences to determine their similarity, which is a fundamental task in bioinformatics for identifying evolutionary relationships.
//
//    Requirements:
//Method Signature:
//public int alignSequences(String seq1, String seq2)
//Functionality:
//This method returns the maximum alignment score between two DNA sequences, considering match, mismatch, and gap penalties.
//Scoring System:
//Match: +2 points
//Mismatch: -1 point
//Gap: -2 points (inserting a gap in one of the sequences)

public class GeneticSequenceAlignment {

    public int alignSequences(String seq1, String seq2) {
        int m = seq1.length();
        int n = seq2.length();
        int[][] dp = new int[m + 1][n + 1];

        // Initialize dp table for gaps
        for (int i = 1; i <= m; i++) {
            dp[i][0] = dp[i - 1][0] - 2;
        }
        for (int j = 1; j <= n; j++) {
            dp[0][j] = dp[0][j - 1] - 2;
        }

        // Fill the dp table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int matchScore = (seq1.charAt(i - 1) == seq2.charAt(j - 1)) ? 2 : -1;
                dp[i][j] = Math.max(dp[i - 1][j - 1] + matchScore, Math.max(dp[i - 1][j] - 2, dp[i][j - 1] - 2));
            }
        }

        return dp[m][n];
    }
}

//Explanation:
//This implementation uses a dynamic programming approach to calculate the maximum
//alignment score for two DNA sequences. The dp array is used to store the scores, where dp[i][j]
//represents the best score for the first i characters of seq1 and the first j characters of seq2.
//Each cell in the dp table is filled by considering the best of three possible scenarios: matching/mismatching
//the current characters of seq1 and seq2, introducing a gap in seq1, or introducing a gap in seq2.


//import com.powem.inv.algos.GeneticSequenceAlignment;
//
//public class Main {
//    public static void main(String[] args) {
//        GeneticSequenceAlignment alignment = new GeneticSequenceAlignment();
//
//        // Test matching sequences
//        assert alignment.alignSequences("ACTG", "ACTG") == 8 : "Test Failed: Perfect match sequences did not return correct score.";
//
//        // Test sequences with mismatches (corrected expected score)
//        assert alignment.alignSequences("ACTG", "ACCG") == 5 : "Test Failed: Sequences with one mismatch did not return correct score.";
//
//        // Test sequences with one gap
//        assert alignment.alignSequences("ACTG", "ACT") == 4 : "Test Failed: Sequences with one gap did not return correct score.";
//
//        // Test sequences with multiple gaps
//        assert alignment.alignSequences("ACTG", "A") == -4 : "Test Failed: Sequences with multiple gaps did not return correct score.";
//
//        // Test completely different sequences
//        assert alignment.alignSequences("AAAA", "TTTT") == -4 : "Test Failed: Completely different sequences did not return correct score.";
//
//    }
//}






