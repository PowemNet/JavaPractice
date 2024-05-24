package com.powem.inv.algos;
//Real-Time Stream Anomaly Detection
//Problem Statement:
//Design an algorithm to monitor a real-time stream of numerical data
//and identify anomalies based on a dynamically updating threshold. The threshold is defined
//as a function of the median of the last N numbers seen in the stream.
//
//The system should be able to efficiently calculate the median of the last N numbers at any
//point in time and use this median to determine if the latest number in the stream is an anomaly.
//
//
//An anomaly is defined as a number that is either more than twice or less than half of the median of the last N numbers.
//The stream is potentially infinite, so the solution must handle large volumes of data with minimal latency.
//
//Implement the StreamAnomalyDetector class that can continuously receive numbers and alert if a
//number is considered an anomaly based on the described conditions.
//
//Function signature and helper methods:

//public class StreamAnomalyDetector {

//  public StreamAnomalyDetector(int windowSize);

//  public void addNumber(int number);

//  public boolean isAnomaly(int number);

//  public double getMedian();

//}


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class StreamAnomalyDetector {
  private PriorityQueue<Integer> maxHeap;
  private PriorityQueue<Integer> minHeap;
  private int windowSize;
  private Queue<Integer> window;

  public StreamAnomalyDetector(int windowSize) {
    this.windowSize = windowSize;
    this.maxHeap = new PriorityQueue<>(Collections.reverseOrder());
    this.minHeap = new PriorityQueue<>();
    this.window = new LinkedList<>();
  }

  public void addNumber(int number) {
    if (window.size() >= windowSize) {
      int out = window.poll();
      if (out <= maxHeap.peek()) {
        maxHeap.remove(out);
      } else {
        minHeap.remove(out);
      }
      balanceHeaps();
    }

    if (maxHeap.isEmpty() || number <= maxHeap.peek()) {
      maxHeap.add(number);
    } else {
      minHeap.add(number);
    }

    balanceHeaps();
    window.add(number);
  }

  public boolean isAnomaly(int number) {
    double median = getMedian();
    return (number > 2 * median || number < 0.5 * median);
  }

  public double getMedian() {
    if (maxHeap.size() > minHeap.size()) {
      return maxHeap.peek();
    } else {
      return (maxHeap.peek() + minHeap.peek()) / 2.0;
    }
  }

  private void balanceHeaps() {
    if (maxHeap.size() > minHeap.size() + 1) {
      minHeap.add(maxHeap.poll());
    } else if (minHeap.size() > maxHeap.size()) {
      maxHeap.add(minHeap.poll());
    }
  }
}

//import com.powem.inv.algos.ResourceDistributor;
//import com.powem.inv.algos.StreamAnomalyDetector;
//import java.util.Collections;
//import java.util.LinkedList;
//import java.util.List;
//
//public class Main {
//  public static void main(String[] args) {
//    testMedianCalculation();
//    testAnomalyDetection();
//    testContinuousInput();
//  }
//
//
//  private static void testMedianCalculation() {
//    StreamAnomalyDetector detector = new StreamAnomalyDetector(5);
//    int[] numbers = {5, 3, 8, 1, 4};
//
//    for (int number : numbers) {
//      detector.addNumber(number);
//    }
//
//    //TEST
//    double median = detector.getMedian();
//    assert median == 4;
//    //TEST_END
//  }
//
//  private static void testAnomalyDetection() {
//    StreamAnomalyDetector detector = new StreamAnomalyDetector(5);
//    int[] numbers = {10, 12, 15, 11, 14};
//
//    for (int number : numbers) {
//      detector.addNumber(number);
//    }
//
//    //TEST
//    boolean isAnomalyHigh = detector.isAnomaly(30);
//    assert isAnomalyHigh;
//    //TEST_END
//
//    //TEST
//    boolean isAnomalyLow = detector.isAnomaly(5);
//    assert isAnomalyLow;
//    //TEST_END
//  }
//
//  private static void testContinuousInput() {
//    StreamAnomalyDetector detector = new StreamAnomalyDetector(3);
//    int[] numbers = {5, 10, 5, 5, 10, 5, 5, 10};
//
//    LinkedList<Integer> lastThree = new LinkedList<>();
//
//    for (int num : numbers) {
//      detector.addNumber(num);
//      lastThree.add(num); // Add current number to the list
//      if (lastThree.size() > 3) {
//        lastThree.removeFirst(); // Ensure only the last three numbers are in the list
//      }
//
//      //TEST
//      double median = detector.getMedian();
//      double expectedMedian = calculateMedian(lastThree);
//      assert Math.abs(median - expectedMedian) < 0.01 : "Median calculation error, expected " + expectedMedian + " but got " + median;
//      System.out.println("Test Passed: Expected Median " + expectedMedian + ", Calculated Median " + median);
//      //TEST_END
//    }
//  }
//
//  private static double calculateMedian(List<Integer> numbers) {
//    if (numbers.size() == 0) {
//      throw new IllegalArgumentException("No numbers to calculate median");
//    }
//    Collections.sort(numbers);
//    int middle = numbers.size() / 2;
//    if (numbers.size() % 2 == 0) {
//      return (numbers.get(middle - 1) + numbers.get(middle)) / 2.0;
//    } else {
//      return numbers.get(middle);
//    }
//  }
//}



