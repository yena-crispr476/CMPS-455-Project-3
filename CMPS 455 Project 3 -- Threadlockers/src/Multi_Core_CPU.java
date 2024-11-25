import java.util.*;

import java.util.concurrent.*;

class Task implements Runnable {

    public final TaskThread taskThread;

    public Task(int id, int burstTime) {

        this.taskThread = new TaskThread(id, burstTime);

    }

    public void run() {

        // Start the thread for current task

        taskThread.start();

        try {

            taskThread.join(); // Wait for the thread to finish execution

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        }

    }

    public int getBurstTime() {

        return taskThread.getMaxBurstTime();

    }

}
public class TaskThread extends Thread {
    private final int id;
    private final int burstTime;
    private int remainingTime;
    private boolean isComplete = false;

    public TaskThread(int id, int burstTime) {
        this.id = id;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }

    public void run() {
        while (remainingTime > 0) {
            try {
                Thread.sleep(1000); // Simulate task execution for 1 second
                remainingTime -= 1;
                System.out.println("Task " + id + " executed for 1 second. Remaining time: " + remainingTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        isComplete = true;
    }

    public int getMaxBurstTime() {
        return burstTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getID() {
        return id;
    }

    public boolean getIsCompleted() {
        return isComplete;
    }
}
public class Task implements Runnable {
    public final TaskThread taskThread;

    public Task(int id, int burstTime) {
        this.taskThread = new TaskThread(id, burstTime);
    }

    public void run() {
        taskThread.start();
        try {
            taskThread.join(); // Wait for the thread to finish execution
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public int getBurstTime() {
        return taskThread.getMaxBurstTime();
    }
}
public class Dispatcher implements Runnable {
    private final List<Task> readyQueue;
    private final String algorithm;
    private final Semaphore semaphore;
    private final int timeQuantum;

    public Dispatcher(List<Task> readyQueue, String algorithm, Semaphore semaphore, int timeQuantum) {
        this.readyQueue = readyQueue;
        this.algorithm = algorithm;
        this.semaphore = semaphore;
        this.timeQuantum = timeQuantum;
    }

    public void run() {
        while (!readyQueue.isEmpty()) {
            try {
                semaphore.acquire(); // Acquire lock

                Task task = selectTask();

                if (task != null) {
                    if (algorithm.equals("FCFS")) {
                        // First-come, first-served
                        task.run();
                    } else if (algorithm.equals("RR")) {
                        // Round Robin
                        runTaskForTimeQuantum(task);
                    } else if (algorithm.equals("SJF")) {
                        // Shortest Job First
                        runTaskUntilCompletion(task);
                    } else if (algorithm.equals("NSJF")) {
                        // Non-Preemptive Shortest Job First
                        runTaskUntilCompletion(task);
                    } else if (algorithm.equals("PSJF")) {
                        // Preemptive Shortest Job First
                        runTaskForTimeQuantum(task);
                    }
                }

                semaphore.release(); // Release lock

                Thread.sleep(100); // Sleep for a moment before the next dispatch
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private Task selectTask() {
        switch (algorithm) {
            case "FCFS":
                return (Task) readyQueue.remove(0); // First-come, first-served
            case "RR":
                return (Task) readyQueue.remove(0); // Round Robin
            case "SJF":
                // Shortest Job First
                Task shortestTask = (Task) readyQueue.stream()
                        .min(Comparator.comparingInt(Task::getBurstTime))
                        .orElse(null);
                if (shortestTask != null) readyQueue.remove(shortestTask);
                return shortestTask;
            case "NSJF":
                // Non-Preemptive Shortest Job First
                Task shortestTaskNSJF = (Task) readyQueue.stream()
                        .min(Comparator.comparingInt(Task::getBurstTime))
                        .orElse(null);
                if (shortestTaskNSJF != null) readyQueue.remove(shortestTaskNSJF);
                return shortestTaskNSJF;
            case "PSJF":
                // Preemptive Shortest Job First
                Task shortestTaskPSJF = (Task) readyQueue.stream()
                        .min(Comparator.comparingInt(Task::getBurstTime))
                        .orElse(null);
                if (shortestTaskPSJF != null) readyQueue.remove(shortestTaskPS
public class Multi_Core_CPU {

    public static void main(String[] args) {

        List readyQueue = new ArrayList<>();

        Semaphore semaphore = new Semaphore(1);

        int[] burstTimes = {18, 7, 25, 42, 21};

        for (int i = 0; i < burstTimes.length; i++) {

            readyQueue.add(new Task(i + 1, burstTimes[i]));

        }

        String schedulingAlgorithm = args.length > 0 ? args[0] : "FCFS"; // Default algorithm

        Dispatcher dispatcher = new Dispatcher(readyQueue, schedulingAlgorithm, semaphore);

        new Thread(dispatcher).start(); // Start the dispatcher

    }

}