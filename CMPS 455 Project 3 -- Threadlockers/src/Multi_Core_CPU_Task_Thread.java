import java.util.*;

import java.util.concurrent.*;

class Task implements Runnable {

    private final TaskThread taskThread;

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

class Dispatcher implements Runnable {

    private final List readyQueue;

    private final String algorithm;

    private final Semaphore semaphore;

    public Dispatcher(List readyQueue, String algorithm, Semaphore semaphore) {

        this.readyQueue = readyQueue;

        this.algorithm = algorithm;

        this.semaphore = semaphore;

    }

    public void run() {

        while (!readyQueue.isEmpty()) {

            try {

                semaphore.acquire(); // Acquire lock

                Runnable task = selectTask();

                if (task != null) {

                    new Thread(task).start(); // Run the selected task in a new thread

                }

                semaphore.release(); // Release lock

                Thread.sleep(100); // Sleep for a moment before the next dispatch

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

            }

        }

    }

    private Runnable selectTask() {

        if (algorithm.equals("FCFS")) {

            return (Runnable) readyQueue.remove(0); // First-come, first-served

        } else if (algorithm.equals("RR")) {

            // Implement Round Robin logic, managing time quantums

            // Pseudo code example:

            Task task = (Task) readyQueue.remove(0);

            // Perform task execution for a time quantum

            return task;

        } else if (algorithm.equals("NSJF")) {

            // Select the task with the shortest burst time

            Task shortestTask = (Task) readyQueue.stream()

                    .map(task -> (Task) task)

                    .min(Comparator.comparingInt(Task::getBurstTime))

                    .orElse(null);

            if (shortestTask != null) readyQueue.remove(shortestTask);

            return shortestTask;

        }

        return null;

    }

}

public class Multi_Core_CPU_Task_Thread {

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