package com.powem.inv.algos;
//Create a Java class called "ProteinSequenceAnalyzer" that analyzes protein sequences.
//The class should have the following methods:
//
//countAminoAcids(String sequence): Counts the occurrence of each amino acid in the given protein sequence.
// The method should take a protein sequence as a string (e.g., "ACDEFGHIKLMNPQRSTVWY") and return a map
// with amino acids as keys and their counts as values. in this case, a valid sequence starts with "ACDEFGHIKLMNPQRSTVWY".
//
//getHydrophobicityScore(String sequence): Calculates the hydrophobicity score of the given protein sequence
// based on the Kyte-Doolittle scale. The method should take a protein sequence as a string and return the
// average hydrophobicity score as a double.


import java.util.HashMap;
import java.util.Map;

public class ProteinSequenceAnalyzer {
    private static final Map<Character, Double> HYDROPHOBICITY_SCALE = new HashMap<>();

    static {
        HYDROPHOBICITY_SCALE.put('A', 1.8);
        HYDROPHOBICITY_SCALE.put('C', 2.5);
        HYDROPHOBICITY_SCALE.put('D', -3.5);
        HYDROPHOBICITY_SCALE.put('E', -3.5);
        HYDROPHOBICITY_SCALE.put('F', 2.8);
        HYDROPHOBICITY_SCALE.put('G', -0.4);
        HYDROPHOBICITY_SCALE.put('H', -3.2);
        HYDROPHOBICITY_SCALE.put('I', 4.5);
        HYDROPHOBICITY_SCALE.put('K', -3.9);
        HYDROPHOBICITY_SCALE.put('L', 3.8);
        HYDROPHOBICITY_SCALE.put('M', 1.9);
        HYDROPHOBICITY_SCALE.put('N', -3.5);
        HYDROPHOBICITY_SCALE.put('P', -1.6);
        HYDROPHOBICITY_SCALE.put('Q', -3.5);
        HYDROPHOBICITY_SCALE.put('R', -4.5);
        HYDROPHOBICITY_SCALE.put('S', -0.8);
        HYDROPHOBICITY_SCALE.put('T', -0.7);
        HYDROPHOBICITY_SCALE.put('V', 4.2);
        HYDROPHOBICITY_SCALE.put('W', -0.9);
        HYDROPHOBICITY_SCALE.put('Y', -1.3);
    }

    public Map<Character, Integer> countAminoAcids(String sequence) {
        if (sequence == null || sequence.isEmpty() || !isValidSequence(sequence)) {
            throw new IllegalArgumentException("Invalid protein sequence: " + sequence);
        }

        Map<Character, Integer> aminoAcidCounts = new HashMap<>();
        for (char aminoAcid : sequence.toCharArray()) {
            aminoAcidCounts.put(aminoAcid, aminoAcidCounts.getOrDefault(aminoAcid, 0) + 1);
        }

        return aminoAcidCounts;
    }

    public double getHydrophobicityScore(String sequence) {
        if (sequence == null || sequence.isEmpty() || !isValidSequence(sequence)) {
            throw new IllegalArgumentException("Invalid protein sequence: " + sequence);
        }

        double totalScore = 0.0;
        for (char aminoAcid : sequence.toCharArray()) {
            totalScore += HYDROPHOBICITY_SCALE.get(aminoAcid);
        }

        return totalScore / sequence.length();
    }

    private boolean isValidSequence(String sequence) {
        return sequence.matches("[ACDEFGHIKLMNPQRSTVWY]+");
    }
}


//TESTS

//import com.powem.inv.algos.ProteinSequenceAnalyzer;
//import java.util.Map;
//
//public class Main {
//    public static void main(String[] args) {
//        ProteinSequenceAnalyzer analyzer = new ProteinSequenceAnalyzer();
//
//        // TEST
//        String sequence1 = "ACDEFGHIKLMNPQRSTVWY";
//        Map<Character, Integer> aminoAcidCounts1 = analyzer.countAminoAcids(sequence1);
//        assert aminoAcidCounts1.size() == 20;
//        // TEST_END
//
//        // TEST
//        assert aminoAcidCounts1.get('A') == 1;
//        // TEST_END
//
//        // TEST
//        assert aminoAcidCounts1.get('C') == 1;
//        // TEST_END
//
//        // TEST
//        String sequence2 = "AAACCCGGGLLL";
//        double hydrophobicityScore = analyzer.getHydrophobicityScore(sequence2);
//        assert hydrophobicityScore == 1.925;
//        // TEST_END
//
//        // TEST
//        try {
//            analyzer.countAminoAcids("123");
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        // TEST_END
//
//        // TEST
//        try {
//            analyzer.countAminoAcids("");
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        // TEST_END
//
//        // TEST
//        try {
//            analyzer.countAminoAcids(null);
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        // TEST_END
//
//        // TEST
//        try {
//            analyzer.getHydrophobicityScore("456");
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        // TEST_END
//
//        // TEST
//        try {
//            analyzer.getHydrophobicityScore("");
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        // TEST_END
//
//        // TEST
//        try {
//            analyzer.getHydrophobicityScore(null);
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        // TEST_END
//    }
//}


