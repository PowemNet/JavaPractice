package com.powem.inv.algos;
//Genetic Sequence Alignment
//Problem:
//Develop a Java method to perform a simple genetic sequence alignment using a basic scoring
//mechanism. This method will help in comparing two DNA sequences to determine their similarity,
//which is a fundamental task in bioinformatics for identifying evolutionary relationships.
//
//Requirements:

//Method Signature:

//public int alignSequences(String seq1, String seq2)

//Functionality:
//This method returns the maximum alignment score between two DNA sequences, considering
//match, mismatch, and gap penalties.
//Scoring System:
//Match: +2 points
//Mismatch: -1 point
//Gap: -2 points (inserting a gap in one of the sequences)

public class GeneticSequenceAlignment {

    public int alignSequences(String seq1, String seq2) {
        if(seq1 == null || seq1.equals("") || seq2 == null || seq2.equals("")) {
            throw new IllegalArgumentException("Invalid input");
        }
        int m = seq1.length();
        int n = seq2.length();
        int[][] sequenceMatrix = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            sequenceMatrix[i][0] = sequenceMatrix[i - 1][0] - 2;
        }
        for (int j = 1; j <= n; j++) {
            sequenceMatrix[0][j] = sequenceMatrix[0][j - 1] - 2;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int matchScore = (seq1.charAt(i - 1) == seq2.charAt(j - 1)) ? 2 : -1;
                sequenceMatrix[i][j] = Math.max(sequenceMatrix[i - 1][j - 1] + matchScore, Math.max(sequenceMatrix[i - 1][j] - 2, sequenceMatrix[i][j - 1] - 2));
            }
        }

        return sequenceMatrix[m][n];
    }
}

//
//import com.powem.inv.algos.GeneticSequenceAlignment;
//
//public class Main {
//    public static void main(String[] args) {
//        GeneticSequenceAlignment alignment = new GeneticSequenceAlignment();
//
//        //TEST
//        assert alignment.alignSequences("ACTG", "ACTG") == 8;
//        //TEST_END
//
//        //TEST
//        assert alignment.alignSequences("ACTG", "ACCG") == 5;
//        //TEST_END
//
//        //TEST
//        assert alignment.alignSequences("ACTG", "ACT") == 4;
//        //TEST_END
//
//        //TEST
//        assert alignment.alignSequences("ACTG", "A") == -4;
//        //TEST_END
//
//        //TEST
//        assert alignment.alignSequences("AAAA", "TTTT") == -4;
//        //TEST_END
//
//        //TEST
//        try {
//            alignment.alignSequences(null, "TTTT");
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        //TEST_END
//
//        //TEST
//        try {
//            alignment.alignSequences("", "TTTT");
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        //TEST_END
//    }
//}






