
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

package com.powem.inv.algos;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class EventScheduler {
    private final PriorityBlockingQueue<ScheduledEvent> eventQueue;
    private final Map<Runnable, ScheduledEvent> eventMap;
    private final ExecutorService executorService;

    public EventScheduler() {
        this.eventQueue = new PriorityBlockingQueue<>();
        this.eventMap = new ConcurrentHashMap<>();
        this.executorService = Executors.newCachedThreadPool();
    }

    public void scheduleEvent(Runnable action, long delayMillis) {
        if (action == null) {
            throw new IllegalArgumentException("Action cannot be null");
        }
        if (delayMillis < 0) {
            throw new IllegalArgumentException("Delay must be non-negative");
        }
        long triggerTime = System.currentTimeMillis() + delayMillis;
        ScheduledEvent event = new ScheduledEvent(action, triggerTime);
        eventQueue.add(event);
        eventMap.put(action, event);
    }

    public void scheduleRecurringEvent(Runnable action, long initialDelayMillis, long periodMillis) {
        if (action == null) {
            throw new IllegalArgumentException("Action cannot be null");
        }
        if (initialDelayMillis < 0 || periodMillis <= 0) {
            throw new IllegalArgumentException("Delays must be positive");
        }
        long triggerTime = System.currentTimeMillis() + initialDelayMillis;
        RecurringScheduledEvent event = new RecurringScheduledEvent(action, triggerTime, periodMillis);
        eventQueue.add(event);
        eventMap.put(action, event);
    }

    public void cancelEvent(Runnable action) {
        if (action == null) {
            throw new IllegalArgumentException("Action cannot be null");
        }
        ScheduledEvent event = eventMap.remove(action);
        if (event != null) {
            eventQueue.remove(event);
        }
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                ScheduledEvent event = eventQueue.peek();
                if (event != null && event.triggerTime <= System.currentTimeMillis()) {
                    event = eventQueue.poll();
                    if (event != null) {
                        executorService.submit(event.action);
                        if (event instanceof RecurringScheduledEvent) {
                            RecurringScheduledEvent recurringEvent = (RecurringScheduledEvent) event;
                            recurringEvent.triggerTime = System.currentTimeMillis() + recurringEvent.periodMillis;
                            eventQueue.add(recurringEvent);
                        }
                    }
                }
                TimeUnit.MILLISECONDS.sleep(10); // Sleep to reduce CPU usage
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore the interrupted status
            }
        }
    }

    public void shutdown() {
        executorService.shutdownNow();
    }

    private static class ScheduledEvent implements Comparable<ScheduledEvent> {
        protected final Runnable action;
        protected long triggerTime;

        public ScheduledEvent(Runnable action, long triggerTime) {
            this.action = action;
            this.triggerTime = triggerTime;
        }

        @Override
        public int compareTo(ScheduledEvent other) {
            return Long.compare(this.triggerTime, other.triggerTime);
        }
    }

    private static class RecurringScheduledEvent extends ScheduledEvent {
        private final long periodMillis;

        public RecurringScheduledEvent(Runnable action, long triggerTime, long periodMillis) {
            super(action, triggerTime);
            this.periodMillis = periodMillis;
        }
    }
}

//TESTS
//import com.powem.inv.algos.EventScheduler;
//
//public class Main {
//    public static void main(String[] args) throws InterruptedException {
//        EventScheduler scheduler = new EventScheduler();
//        long start = System.currentTimeMillis();
//
//        scheduler.scheduleEvent(() -> assertDelayPassed(1000, start), 1000);
//        scheduler.scheduleEvent(() -> assertDelayPassed(2000, start), 2000);
//        scheduler.scheduleEvent(() -> assertDelayPassed(3000, start), 3000);
//
//        scheduler.scheduleRecurringEvent(() -> assertRecurringDelayPassed(500, start), 500, 500);
//
//        Thread schedulerThread = new Thread(scheduler::run);
//        schedulerThread.start();
//
//        Thread.sleep(4000);
//        schedulerThread.interrupt();
//        scheduler.shutdown();
//
//        scheduler = new EventScheduler();
//        schedulerThread = new Thread(scheduler::run);
//        schedulerThread.start();
//
//        Runnable eventToCancel = () -> assertDelayPassed(1000, start);
//        scheduler.scheduleEvent(eventToCancel, 1000);
//        scheduler.cancelEvent(eventToCancel);
//
//        Thread.sleep(2000);
//        schedulerThread.interrupt();
//        scheduler.shutdown();
//
//        try {
//            scheduler.scheduleEvent(null, 1000);
//            //TEST
//            assert false;
//            //TEST END
//        } catch (IllegalArgumentException e) {
//            // TEST
//            assert true;
//            // TEST END
//        }
//
//        try {
//            scheduler.scheduleEvent(() -> System.out.println("This should not be printed"), -1000);
//
//            //TEST
//            assert false;
//            //TEST END
//        } catch (IllegalArgumentException e) {
//            // TEST
//            assert true;
//            // TEST END
//        }
//
//        try {
//            scheduler.scheduleRecurringEvent(null, 1000, 500);
//            //TEST
//            assert false;
//            //TEST END
//        } catch (IllegalArgumentException e) {
//            // TEST
//            assert true;
//            // TEST END
//        }
//
//        try {
//            scheduler.scheduleRecurringEvent(() -> System.out.println("This should not be printed"), 1000, -500);
//            //TEST
//            assert false;
//            //TEST END
//        } catch (IllegalArgumentException e) {
//            // TEST
//            assert true;
//            // TEST END
//        }
//
//        try {
//            scheduler.cancelEvent(null);
//            //TEST
//            assert false;
//            //TEST END
//        } catch (IllegalArgumentException e) {
//            // TEST
//            assert true;
//            // TEST END
//        }
//    }
//
//    private static void assertDelayPassed(long expectedDelay, long start) {
//        long elapsed = System.currentTimeMillis() - start;
//        // TEST
//        assert elapsed >= expectedDelay;
//        // TEST END
//    }
//
//    private static void assertRecurringDelayPassed(long period, long start) {
//        long elapsed = System.currentTimeMillis() - start;
//        long periodsElapsed = elapsed / period;
//        // TEST
//        assert periodsElapsed >= 1;
//        // TEST END
//    }
//}
