package com.powem.inv.claude;
//Inspiration: Use of Jaccard Similarity formula to anyalse test.

//Create a Java class called "LiteraryAnalyzer" that performs sentiment analysis on a given piece of literature.
// The class should have the following methods:
//
//analyzeSentiment(String text): Analyzes the sentiment of the given text and returns a sentiment score between -1
//(very negative) and 1 (very positive). Use a predefined list of positive and negative words to calculate the sentiment
//score based on the frequency and intensity of the words present in the text.
//positive words: "happy", "joy", "love", "wonderful", "excellent"
//negative words: "sad", "angry", "hate", "terrible", "awful"
//
//getTopKeywords(String text, int n): Extracts the top N most frequent keywords from the given text, excluding
//stopwords (common words like "the", "and", "is"). Use a predefined list of stopwords and a HashMap to keep track
//of word frequencies. Return the top N keywords as a list of strings.
//stop words: "the", "and", "is", "a", "an", "in", "of"
//
//compareAuthors(String author1, String author2): Compares the writing styles of two authors by analyzing the
//sentiment and keyword usage in their texts. Use the analyzeSentiment and getTopKeywords methods  and a JaccardSimilarity formula
// to calculate the average sentiment scores and compare the top keywords for each author. Return a similarity score between
//0 and 1, where 1 indicates highly similar writing styles.


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LiteraryAnalyzer {
    private static final List<String> POSITIVE_WORDS = Arrays.asList("happy", "joy", "love", "wonderful", "excellent");
    private static final List<String> NEGATIVE_WORDS = Arrays.asList("sad", "angry", "hate", "terrible", "awful");
    private static final List<String> STOPWORDS = Arrays.asList("the", "and", "is", "a", "an", "in", "of");

    public double analyzeSentiment(String text) {
        validateText(text);

        String[] words = text.toLowerCase().split("\\W+");
        int positiveCount = 0;
        int negativeCount = 0;

        for (String word : words) {
            if (POSITIVE_WORDS.contains(word)) {
                positiveCount++;
            } else if (NEGATIVE_WORDS.contains(word)) {
                negativeCount++;
            }
        }

        int totalCount = positiveCount + negativeCount;
        if (totalCount == 0) {
            return 0.0;
        }

        return (positiveCount - negativeCount) / (double) totalCount;
    }

    public List<String> getTopKeywords(String text, int n) {
        validateText(text);

        String[] words = text.toLowerCase().split("\\W+");
        Map<String, Integer> frequencyMap = new HashMap<>();

        for (String word : words) {
            if (!STOPWORDS.contains(word)) {
                frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
            }
        }

        List<String> keywords = new ArrayList<>(frequencyMap.keySet());
        keywords.sort((a, b) -> frequencyMap.get(b) - frequencyMap.get(a));

        return keywords.subList(0, Math.min(n, keywords.size()));
    }

    public double compareAuthors(String author1, String author2) {
        validateAuthor(author1);
        validateAuthor(author2);

        String text1 = getAuthorTextViaApi(author1);
        String text2 = getAuthorTextViaApi(author2);

        double sentimentScore1 = analyzeSentiment(text1);
        double sentimentScore2 = analyzeSentiment(text2);
        double sentimentSimilarity = 1 - Math.abs(sentimentScore1 - sentimentScore2);

        List<String> keywords1 = getTopKeywords(text1, 10);
        List<String> keywords2 = getTopKeywords(text2, 10);
        double keywordSimilarity = calculateJaccardSimilarity(keywords1, keywords2);

        return (sentimentSimilarity + keywordSimilarity) / 2.0;
    }

    private double calculateJaccardSimilarity(List<String> list1, List<String> list2) {
        Set<String> intersection = new HashSet<>(list1);
        intersection.retainAll(list2);

        Set<String> union = new HashSet<>(list1);
        union.addAll(list2);

        return intersection.size() / (double) union.size();
    }

    private void validateText(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Input text cannot be null or empty");
        }
    }

    private void validateAuthor(String author) {
        if (author == null || author.isEmpty()) {
            throw new IllegalArgumentException("Author name cannot be null or empty");
        }
    }

    private String getAuthorTextViaApi(String author) {
        return "Sample text response from API for " + author;
    }
}

//-- TESTS

//import com.powem.inv.claude.LiteraryAnalyzer;
//import java.util.Arrays;
//import java.util.List;
//
//public class Main {
//    public static void main(String[] args) {
//        LiteraryAnalyzer analyzer = new LiteraryAnalyzer();
//
//        // TEST
//        String text1 = "This is a wonderful and happy book. It brings joy and love to the reader.";
//        double sentiment1 = analyzer.analyzeSentiment(text1);
//        assert sentiment1 > 0.0;
//        // TEST_END
//
//        // TEST
//        String text2 = "The story is terrible and awful. It makes the reader feel sad and angry.";
//        double sentiment2 = analyzer.analyzeSentiment(text2);
//        assert sentiment2 < 0.0;
//        // TEST_END
//
//        // TEST
//        String text3 = "This is a neutral text without any positive or negative words.";
//        double sentiment3 = analyzer.analyzeSentiment(text3);
//        assert sentiment3 == 0.0;
//        // TEST_END
//
//        // TEST
//        String text4 = "The cat sat on the mat. It was a sunny day. The bird flew by.";
//        List<String> keywords4 = analyzer.getTopKeywords(text4, 3);
//        List<String> expectedKeywords4 = Arrays.asList("mat", "cat", "sat");
//        assert keywords4.containsAll(expectedKeywords4) && expectedKeywords4.containsAll(keywords4);
//        // TEST_END
//
//        // TEST
//        try {
//            analyzer.analyzeSentiment("");
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert e.getMessage().equals("Input text cannot be null or empty");
//        }
//        // TEST_END
//
//        // TEST
//        try {
//            analyzer.getTopKeywords("", 5);
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert e.getMessage().equals("Input text cannot be null or empty");
//        }
//        // TEST_END
//
//        // TEST
//        try {
//            analyzer.compareAuthors("", "Author2");
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert e.getMessage().equals("Author name cannot be null or empty");
//        }
//        // TEST_END
//
//        // TEST
//        double similarity1 = analyzer.compareAuthors("Author1", "Author1");
//        assert similarity1 == 1.0;
//        // TEST_END
//
//        // TEST
//        double similarity2 = analyzer.compareAuthors("Author1", "Author2");
//        assert similarity2 >= 0.0 && similarity2 <= 1.0;
//        // TEST_END
//    }
//}




