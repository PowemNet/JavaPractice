package com.powem.inv.claude;
//Inspiration:
//

//Create a Java class called "LZWCompressor" that implements the Lempel-Ziv-Welch (LZW)
//compression algorithm. The class should have the following methods:
//
//compress(String input): Takes an input string and returns a list of integers representing
//the compressed data.
//decompress(List<Integer> compressed): Takes a list of integers representing the compressed
// data and returns the decompressed string.
//The LZW algorithm should build a dictionary dynamically during compression and use the
//dictionary for decompression. Handle any potential issues
//gracefully, such as reaching the maximum dictionary size or encountering invalid
//compressed data during decompression.

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class LZWCompressor {
    private static final int DICTIONARY_SIZE = 4096;

    public List<Integer> compress(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be null or empty");
        }

        Map<String, Integer> dictionary = initializeDictionary();
        List<Integer> compressed = new ArrayList<>();

        String current = "";
        for (char c : input.toCharArray()) {
            String next = current + c;
            if (dictionary.containsKey(next)) {
                current = next;
            } else {
                compressed.add(dictionary.get(current));
                if (dictionary.size() < DICTIONARY_SIZE) {
                    dictionary.put(next, dictionary.size());
                }
                current = Character.toString(c);
            }
        }

        if (!current.isEmpty()) {
            compressed.add(dictionary.get(current));
        }

        return compressed;
    }

    public String decompress(List<Integer> compressed) {
        if (compressed == null || compressed.isEmpty()) {
            throw new IllegalArgumentException("Compressed data cannot be null or empty");
        }

        Map<Integer, String> dictionary = initializeReverseDictionary();
        StringBuilder decompressed = new StringBuilder();

        int previous = compressed.get(0);
        if (!dictionary.containsKey(previous)) {
            throw new IllegalArgumentException("Invalid compressed data");
        }
        decompressed.append(dictionary.get(previous));

        for (int i = 1; i < compressed.size(); i++) {
            int current = compressed.get(i);
            String entry;

            if (dictionary.containsKey(current)) {
                entry = dictionary.get(current);
            } else if (current == dictionary.size()) {
                entry = dictionary.get(previous) + dictionary.get(previous).charAt(0);
            } else {
                throw new IllegalArgumentException("Invalid compressed data");
            }

            decompressed.append(entry);

            if (dictionary.size() < DICTIONARY_SIZE) {
                String newEntry = dictionary.get(previous) + entry.charAt(0);
                dictionary.put(dictionary.size(), newEntry);
            }

            previous = current;
        }

        return decompressed.toString();
    }

    private Map<String, Integer> initializeDictionary() {
        Map<String, Integer> dictionary = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            dictionary.put(Character.toString((char) i), i);
        }
        return dictionary;
    }

    private Map<Integer, String> initializeReverseDictionary() {
        Map<Integer, String> dictionary = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            dictionary.put(i, Character.toString((char) i));
        }
        return dictionary;
    }
}

//-- TESTS --

//import com.powem.inv.claude.LZWCompressor;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Main {
//    public static void main(String[] args) {
//        LZWCompressor compressor = new LZWCompressor();
//
//        // TEST
//        String input1 = "ABRACADABRA";
//        List<Integer> compressed1 = compressor.compress(input1);
//        String decompressed1 = compressor.decompress(compressed1);
//        assert input1.equals(decompressed1);
//        // TEST_END
//
//        // TEST
//        String input2 = "LZWLZWLZWLZWLZWLZWLZWLZWLZWLZWLZWLZWLZWLZWLZW";
//        List<Integer> compressed2 = compressor.compress(input2);
//        String decompressed2 = compressor.decompress(compressed2);
//        assert input2.equals(decompressed2);
//        // TEST_END
//
//        // TEST
//        String input3 = "";
//        try {
//            compressor.compress(input3);
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//
//        // TEST_END
//
//        // TEST
//        String input4 = null;
//        try {
//            compressor.compress(input4);
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        // TEST_END
//
//        // TEST
//        List<Integer> compressed5 = null;
//        try {
//            compressor.decompress(compressed5);
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        // TEST_END
//
//        // TEST
//        List<Integer> compressed6 = new ArrayList<>();
//        try {
//            compressor.decompress(compressed6);
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        // TEST_END
//
//        // TEST
//        List<Integer> compressed7 = List.of(1000);
//        try {
//            compressor.decompress(compressed7);
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        // TEST_END
//    }
//}





