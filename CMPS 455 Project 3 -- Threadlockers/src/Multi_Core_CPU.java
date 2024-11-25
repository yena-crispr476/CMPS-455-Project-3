//import java.util.*;
//
//import java.util.concurrent.*;
//
//class Task implements Runnable {
//    public final TaskThread taskThread;
//
//    public Task(int id, int burstTime) {
//        this.taskThread = new TaskThread(id, burstTime);
//    }
//
//    @Override
//    public void run() {
//        // Start the thread for current task
//        taskThread.start();
//
//        try {
//            taskThread.join(); // Wait for the thread to finish execution
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
//
//    public int getBurstTime() {
//        return taskThread.getMaxBurstTime();
//    }
//}
//
//public class Dispatcher implements Runnable {
//    private final Queue<Task> readyQueue;
//
//    private final Semaphore semaphore;
//    private final int timeQuantum;
//
//    public Dispatcher(Queue<Task> readyQueue, int algorithm, Semaphore semaphore, int timeQuantum) {
//        this.readyQueue = readyQueue;
//        this.semaphore = semaphore;
//        this.timeQuantum = timeQuantum;
//    }
//
//    @Override
//    public void run() {
//        while (!readyQueue.isEmpty()) {
//            try {
//                semaphore.acquire(); // Acquire lock
//
//                Task task = selectTask();
//                if (task != null) {
//
//                }
//
//                semaphore.release(); // Release lock
//                Thread.sleep(100); // Sleep for a moment before the next dispatch
//
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        }
//    }
//
//    private Task selectTask() {
//        // Logic for selecting task based on the scheduling algorithm
//        Task selectedTask = null;
//        switch (dispatcherSingle.getAlgorithmChoice()) {
//            case 1:
//                selectedTask = readyQueue.poll(); // First-come, first-served
//                break;
//            case 2:
//                selectedTask = readyQueue.poll(); // Round Robin
//                break;
//            case 3:
//            case 4:
//                selectedTask = readyQueue.stream()
//                        .min(Comparator.comparingInt(Task::getBurstTime))
//                        .orElse(null); // Shortest Job First (and variants)
//                if (selectedTask != null) readyQueue.remove(selectedTask);
//                break;
//        }
//        return selectedTask;
//    }
//}
//public class Multi_Core_CPU {
//
//    public static void main(String[] args) {
//        List<Task> readyQueue = new ArrayList<>();
//        Semaphore semaphore = new Semaphore(1);
//
//        int[] burstTimes = {18, 7, 25, 42, 21};
//
//        for (int i = 0; i < burstTimes.length; i++) {
//            readyQueue.add(new Task(i + 1, burstTimes[i]));
//        }
//
//        String schedulingAlgorithm = args.length > 0 ? args[0] : "FCFS"; // Default algorithm
//        Dispatcher dispatcher = new Dispatcher(new LinkedList<>(readyQueue), schedulingAlgorithm, semaphore, 5);
//
//        new Thread(dispatcher).start(); // Start the dispatcher
//    }
//}