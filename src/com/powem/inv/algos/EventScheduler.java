package com.powem.inv.algos;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

//    Problem: Event Scheduler System
//    Problem Statement:
//    Design an event scheduler that manages and triggers timed events based on system time. Each event has a specific
//    action to be executed after a certain delay in milliseconds.
//
//    Develop a system that can schedule events, manage delay efficiently, and execute an action
//    (represented as a command or a method call) when the time is appropriate.
//
//    The system should be capable of adding events with a specified delay and action.
//    Once an event's delay time has passed (relative to its scheduling time), the system should execute its action.
//
//    Implement the EventScheduler class with methods to add events and process them efficiently as time progresses.
//
//    Function Signature:
//    public class EventScheduler {
//    public void scheduleEvent(Runnable action, long delayMillis);
//    public void run();
//}

public class EventScheduler {
    private final PriorityBlockingQueue<ScheduledEvent> eventQueue;

    public EventScheduler() {
        this.eventQueue = new PriorityBlockingQueue<>();
    }

    public void scheduleEvent(Runnable action, long delayMillis) {
        long triggerTime = System.currentTimeMillis() + delayMillis;
        eventQueue.add(new ScheduledEvent(action, triggerTime));
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                ScheduledEvent event = eventQueue.peek();
                if (event != null && event.triggerTime <= System.currentTimeMillis()) {
                    event = eventQueue.poll();
                    if (event != null) {
                        new Thread(event.action).start(); // Execute action in a new thread
                    }
                }
                TimeUnit.MILLISECONDS.sleep(10); // Sleep to reduce CPU usage
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore the interrupted status
            }
        }
    }

    private static class ScheduledEvent implements Comparable<ScheduledEvent> {
        private Runnable action;
        private long triggerTime;

        public ScheduledEvent(Runnable action, long triggerTime) {
            this.action = action;
            this.triggerTime = triggerTime;
        }

        @Override
        public int compareTo(ScheduledEvent other) {
            return Long.compare(this.triggerTime, other.triggerTime);
        }
    }
}


//import com.powem.inv.algos.EventScheduler;
//
//public class Main {
//    public static void main(String[] args) throws InterruptedException {
//
//        EventScheduler scheduler = new EventScheduler();
//        long start = System.currentTimeMillis();
//
//        scheduler.scheduleEvent(() -> assertDelayPassed(1000, start), 1000);
//        scheduler.scheduleEvent(() -> assertDelayPassed(2000, start), 2000);
//        scheduler.scheduleEvent(() -> assertDelayPassed(3000, start), 3000);
//
//        Thread schedulerThread = new Thread(scheduler::run);
//        schedulerThread.start();
//
//        Thread.sleep(4000);
//        schedulerThread.interrupt();
//    }
//
//    private static void assertDelayPassed(long expectedDelay, long start) {
//        long elapsed = System.currentTimeMillis() - start;
//        //TEST
//        assert elapsed >= expectedDelay : "Task did not wait " + expectedDelay + "ms, elapsed time: " + elapsed + "ms";
//        //TEST_END
//    }
//}