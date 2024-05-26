package com.powem.inv.algos;
//High-Frequency Trading Algorithm Simulation
//Problem:
//Design a Java class to simulate a high-frequency trading (HFT) algorithm that optimizes buy and sell orders based on real-time market data. This algorithm should analyze incoming trade data streams to detect arbitrage opportunities across multiple markets and execute trades to maximize profit efficiently.
//
//Requirements:
//Class Name: HighFrequencyTradingSimulator
//Method Signatures:
//public void processMarketData(String marketId, String symbol, double price)
//public List<TradeAction> analyzeOpportunities()
//public void executeTrade(TradeAction tradeAction)
//Functionality:
//processMarketData: Updates the system with the latest price for a given stock symbol in a specific market.
//analyzeOpportunities: Analyzes the current market data to identify arbitrage opportunities where buying in one market and selling in another can yield profit.
//    executeTrade: Executes a trade action based on the opportunities identified.


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HighFrequencyTradingSimulator {
    private Map<String, Map<String, Double>> marketData; // MarketID -> (Symbol -> Price)

    public HighFrequencyTradingSimulator() {
        this.marketData = new HashMap<>();
    }

    public Map<String, Map<String, Double>> getMarketData() {
        return this.marketData;
    }

    public void processMarketData(String marketId, String symbol, double price) {
        if (marketId == null || marketId.isEmpty() || symbol == null || symbol.isEmpty() || price <= 0) {
            throw new IllegalArgumentException("Invalid input for market data");
        }
        marketData.putIfAbsent(marketId, new HashMap<>());
        marketData.get(marketId).put(symbol, price);
    }

    public List<TradeAction> analyzeOpportunities() {
        List<TradeAction> opportunities = new ArrayList<>();
        for (String market1 : marketData.keySet()) {
            for (String market2 : marketData.keySet()) {
                if (!market1.equals(market2)) {
                    for (String symbol : marketData.get(market1).keySet()) {
                        if (marketData.get(market2).containsKey(symbol)) {
                            double price1 = marketData.get(market1).get(symbol);
                            double price2 = marketData.get(market2).get(symbol);
                            if (price1 < price2) {
                                opportunities.add(new TradeAction(market1, market2, symbol, price1, price2));
                            }
                        }
                    }
                }
            }
        }
        return opportunities;
    }

    public void executeTrade(TradeAction tradeAction) {
        if (tradeAction == null) {
            throw new IllegalArgumentException("Trade action cannot be null");
        }
        System.out.println("Executing trade: Buy " + tradeAction.symbol + " in " + tradeAction.buyMarket
            + " at " + tradeAction.buyPrice + " and sell in " + tradeAction.sellMarket
            + " at " + tradeAction.sellPrice);
    }

    public static class TradeAction {
        String buyMarket, sellMarket, symbol;
        double buyPrice, sellPrice;

        public TradeAction(String buyMarket, String sellMarket, String symbol, double buyPrice, double sellPrice) {
            this.buyMarket = buyMarket;
            this.sellMarket = sellMarket;
            this.symbol = symbol;
            this.buyPrice = buyPrice;
            this.sellPrice = sellPrice;
        }

        public String getSymbol() {
            return this.symbol;
        }

        public String getBuyMarket() {
            return this.buyMarket;
        }

        public String getSellMarket() {
            return this.sellMarket;
        }
    }
}

//import com.powem.inv.algos.HighFrequencyTradingSimulator;
//import java.util.List;
//
//public class Main {
//    public static void main(String[] args) {
//        testMarketDataProcessing();
//        testArbitrageOpportunityAnalysis();
//        testTradeExecution();
//        testInputValidation();
//    }
//
//    private static void testMarketDataProcessing() {
//        HighFrequencyTradingSimulator simulator = new HighFrequencyTradingSimulator();
//        simulator.processMarketData("NYSE", "AAPL", 150.00);
//        simulator.processMarketData("NASDAQ", "AAPL", 150.50);
//
//        //TEST
//        assert simulator.getMarketData().get("NYSE").get("AAPL") == 150.00 : "Test Failed: NYSE AAPL price incorrect.";
//        assert simulator.getMarketData().get("NASDAQ").get("AAPL") == 150.50 : "Test Failed: NASDAQ AAPL price incorrect.";
//        System.out.println("Test Passed: Market data processed correctly.");
//        //TEST_END
//    }
//
//    private static void testArbitrageOpportunityAnalysis() {
//        HighFrequencyTradingSimulator simulator = new HighFrequencyTradingSimulator();
//        simulator.processMarketData("NYSE", "AAPL", 149.00);
//        simulator.processMarketData("NASDAQ", "AAPL", 150.00);
//        simulator.processMarketData("NYSE", "GOOGL", 1000.00);
//        simulator.processMarketData("NASDAQ", "GOOGL", 995.00);
//
//        //TEST
//        List<HighFrequencyTradingSimulator.TradeAction> opportunities = simulator.analyzeOpportunities();
//        boolean foundAAPLArbitrage = opportunities.stream().anyMatch(o -> o.getSymbol().equals("AAPL") && o.getBuyMarket().equals("NYSE") && o.getSellMarket().equals("NASDAQ"));
//        boolean foundGOOGLArbitrage = opportunities.stream().anyMatch(o -> o.getSymbol().equals("GOOGL") && o.getBuyMarket().equals("NASDAQ") && o.getSellMarket().equals("NYSE"));
//        assert foundAAPLArbitrage : "Test Failed: AAPL arbitrage opportunity not detected.";
//        assert foundGOOGLArbitrage : "Test Failed: GOOGL arbitrage opportunity not detected.";
//        System.out.println("Test Passed: Arbitrage opportunities analyzed correctly.");
//        //TEST_END
//    }
//
//    private static void testTradeExecution() {
//        HighFrequencyTradingSimulator simulator = new HighFrequencyTradingSimulator();
//        HighFrequencyTradingSimulator.TradeAction action = new HighFrequencyTradingSimulator.TradeAction("NYSE", "NASDAQ", "AAPL", 149.00, 150.00);
//
//        //TEST
//        try {
//            simulator.executeTrade(action);
//            System.out.println("Test Passed: Trade executed successfully.");
//        } catch (Exception e) {
//            System.out.println("Test Failed: Trade should execute without errors.");
//        }
//        //TEST_END
//    }
//
//    private static void testInputValidation() {
//        HighFrequencyTradingSimulator simulator = new HighFrequencyTradingSimulator();
//
//        //TEST
//        try {
//            simulator.processMarketData(null, "AAPL", 150.00);
//            assert false : "Test Failed: Processing with null marketId should throw exception.";
//        } catch (IllegalArgumentException e) {
//            System.out.println("Test Passed: Null marketId input validation succeeded.");
//        }
//        //TEST_END
//
//        //TEST
//        try {
//            simulator.processMarketData("NYSE", "", 150.00);
//            assert false : "Test Failed: Processing with empty symbol should throw exception.";
//        } catch (IllegalArgumentException e) {
//            System.out.println("Test Passed: Empty symbol input validation succeeded.");
//        }
//        //TEST_END
//
//        //TEST
//        try {
//            simulator.processMarketData("NYSE", "AAPL", -1);
//            assert false : "Test Failed: Processing with negative price should throw exception.";
//        } catch (IllegalArgumentException e) {
//            System.out.println("Test Passed: Negative price input validation succeeded.");
//        }
//        //TEST_END
//
//        //TEST
//        try {
//            simulator.executeTrade(null);
//            assert false : "Test Failed: Executing null trade action should throw exception.";
//        } catch (IllegalArgumentException e) {
//            System.out.println("Test Passed: Null trade action input validation succeeded.");
//        }
//        //TEST_END
//    }
//}


