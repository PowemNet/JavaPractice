package com.powem.inv.algos;
//Cryptocurrency Trend Analysis Tool
//Problem:
//Develop a Java class to analyze and predict trends in cryptocurrency prices based on historical data. The tool should help users identify potential buying or selling opportunities by analyzing patterns in price movements and trading volumes.
//
//Requirements:
//Class Name: CryptoTrendAnalyzer
//Method Signatures:
//public void addPriceData(String cryptoSymbol, double price, long timestamp)
//public String analyzeTrend(String cryptoSymbol)
//Functionality:
//addPriceData: Stores price data along with a timestamp for a given cryptocurrency symbol.
//analyzeTrend: Analyzes the price data to determine if the trend for the given cryptocurrency symbol is bullish, bearish, or stable.


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CryptoTrendAnalyzer {
    private Map<String, List<PriceData>> priceHistory;

    public CryptoTrendAnalyzer() {
        this.priceHistory = new HashMap<>();
    }

    public Map<String, List<PriceData>> getPriceHistory() {
        return priceHistory;
    }

    public void addPriceData(String cryptoSymbol, double price, long timestamp) {
        if (cryptoSymbol == null || cryptoSymbol.isEmpty()) {
            throw new IllegalArgumentException("Crypto symbol cannot be null or empty");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        priceHistory.putIfAbsent(cryptoSymbol, new ArrayList<>());
        priceHistory.get(cryptoSymbol).add(new PriceData(price, timestamp));
        priceHistory.get(cryptoSymbol).sort(Comparator.comparingLong(p -> p.timestamp));
    }

    public String analyzeTrend(String cryptoSymbol) {
        if (cryptoSymbol == null || cryptoSymbol.isEmpty() || !priceHistory.containsKey(cryptoSymbol)) {
            throw new IllegalArgumentException("Invalid or missing crypto symbol");
        }
        List<PriceData> data = priceHistory.get(cryptoSymbol);
        if (data.size() < 3) {
            return "Insufficient data";
        }

        // Simple moving average for the last 3 data points
        int size = data.size();
        double recentAverage = (data.get(size - 1).price + data.get(size - 2).price + data.get(size - 3).price) / 3;
        double previousAverage = (data.get(size - 2).price + data.get(size - 3).price + data.get(size - 4).price) / 3;

        if (recentAverage > previousAverage) {
            return "Bullish";
        } else if (recentAverage < previousAverage) {
            return "Bearish";
        } else {
            return "Stable";
        }
    }

    private static class PriceData {
        double price;
        long timestamp;

        PriceData(double price, long timestamp) {
            this.price = price;
            this.timestamp = timestamp;
        }
    }
}


//import com.powem.inv.algos.CryptoTrendAnalyzer;
//import com.powem.inv.algos.HighFrequencyTradingSimulator;
//import java.util.List;
//
//public class Main {
//    public static void main(String[] args) {
//        testPriceDataAddition();
//        testTrendAnalysis();
//        testInputValidation();
//    }
//
//    private static void testPriceDataAddition() {
//        CryptoTrendAnalyzer analyzer = new CryptoTrendAnalyzer();
//        analyzer.addPriceData("BTC", 50000.0, 1617753600); // Example timestamp
//        analyzer.addPriceData("BTC", 51000.0, 1617840000);
//        analyzer.addPriceData("BTC", 49500.0, 1617926400);
//
//        //TEST
//        assert analyzer.getPriceHistory().containsKey("BTC") : "Test Failed: BTC data should be recorded.";
//        assert analyzer.getPriceHistory().get("BTC").size() == 3 : "Test Failed: There should be three data points.";
//        System.out.println("Test Passed: Price data added successfully.");
//        //TEST_END
//    }
//
//    private static void testTrendAnalysis() {
//        CryptoTrendAnalyzer analyzer = new CryptoTrendAnalyzer();
//        analyzer.addPriceData("BTC", 47000.0, 1617753600);
//        analyzer.addPriceData("BTC", 48000.0, 1617840000);
//        analyzer.addPriceData("BTC", 49000.0, 1617926400);
//        analyzer.addPriceData("BTC", 50000.0, 1618012800);
//
//        //TEST
//        String trend = analyzer.analyzeTrend("BTC");
//        assert "Bullish".equals(trend) : "Test Failed: Trend should be Bullish.";
//        System.out.println("Test Passed: Trend analysis correct.");
//        //TEST_END
//    }
//
//    private static void testInputValidation() {
//        CryptoTrendAnalyzer analyzer = new CryptoTrendAnalyzer();
//
//        //TEST
//        try {
//            analyzer.addPriceData(null, 50000.0, 1617753600);
//            assert false : "Test Failed: Adding data with null symbol should throw exception.";
//        } catch (IllegalArgumentException e) {
//            System.out.println("Test Passed: Null symbol input validation succeeded.");
//        }
//        //TEST_END
//
//        //TEST
//        try {
//            analyzer.addPriceData("BTC", -1, 1617753600);
//            assert false : "Test Failed: Adding data with negative price should throw exception.";
//        } catch (IllegalArgumentException e) {
//            System.out.println("Test Passed: Negative price input validation succeeded.");
//        }
//        //TEST_END
//
//        //TEST
//        try {
//            analyzer.analyzeTrend("");
//            assert false : "Test Failed: Analyzing trend with empty symbol should throw exception.";
//        } catch (IllegalArgumentException e) {
//            System.out.println("Test Passed: Empty symbol trend analysis input validation succeeded.");
//        }
//        //TEST_END
//
//        //TEST
//        try {
//            analyzer.analyzeTrend("ETH"); // Assuming no data was added for ETH
//            assert false : "Test Failed: Analyzing trend for non-existent symbol should throw exception.";
//        } catch (IllegalArgumentException e) {
//            System.out.println("Test Passed: Non-existent symbol trend analysis validation succeeded.");
//        }
//        //TEST_END
//    }
//}




