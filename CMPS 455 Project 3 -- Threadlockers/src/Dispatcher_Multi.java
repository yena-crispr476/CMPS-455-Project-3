//import java.util.LinkedList;
//import java.util.PriorityQueue;
//import java.util.Queue;
//import java.util.Random;
//import java.util.concurrent.Semaphore;
//
//public class Dispatcher_Multi {
//    private int algorithm_Choice;
//    static int quantum;
//    static Semaphore queueSem = new Semaphore(1);
//    static Semaphore processSem = new Semaphore(1);
//    TaskThread task;
//    static Random random = new Random();
//
//
//    static Queue<TaskThread> ready_Queue = new LinkedList<>();
//
//    public Dispatcher_Single (Queue <TaskThread> queue, int quantum, Semaphore sem, int algorithmChoice) {
//        ready_Queue = queue;
//        this.quantum = quantum;
//        this.queueSem = sem;
//        this.algorithm_Choice = algorithmChoice;
//    }
//
//    public void FCFS_Single () {    // Completes processes to completion as they come in the queue. FIFO
//        System.out.println("Dispatcher 0 \t | Running FCFS algorithm..");
//        while (!ready_Queue.isEmpty()) {
//            try {
//                queueSem.acquireUninterruptibly();
//                TaskThread task = ready_Queue.poll();
//
//                if (task != null) {
//                    System.out.println("Proc. Thread " + task.getID() + "\t | On CPU: 0, MB = " + task.getMaxBurstTime() + ", CB = " + task.getCurrentBurstTime() + ", BT = " + task.getMaxBurstTime() + ", BG = " + task.getMaxBurstTime());
//                }
//                queueSem.release();
//
//                processSem.acquireUninterruptibly();
//                for (int i = 0; i < task.getMaxBurstTime(); i++) {
//                    //System.out.println("Proc Thread" + task.getID() + "\t | Using CPU 0; On Burst " + task.getCurrentBurstTime() + ".");
//
//                    task.run(1, 0);
//                }
//                processSem.release();
//
//                System.out.println();
//
//            } catch (Exception e) {
//                System.out.println("The FCFS algorithm couldn't execute completely");
//            }
//
//        }
//        System.out.println("Main Thread \t | Exiting");
//    }
//
//    public void RR_Single () {  // Complete in a FIFO manner, but each process executes until the time quantum is completed. Then place the thread back into the CPU for later execution
//        System.out.println("Dispatcher 0 \t | Running Round Robin algorithm. Time Quantum: " + quantum);
//
//        while (!ready_Queue.isEmpty()) {
//            try {
//                queueSem.acquireUninterruptibly();
//                TaskThread task = ready_Queue.poll();
//                System.out.println("Proc. Thread " + task.getID() + "\t | On CPU: 0, MB = " + task.getMaxBurstTime() + ", CB = " + task.getCurrentBurstTime() + ", BT = " + task.getMaxBurstTime() + ", BG = " + task.getMaxBurstTime());
//                queueSem.release();
//                if (task != null) {
//                    task.run(quantum, 0);
//
//
//                    if (!task.getIsCompleted()) {
//                        processSem.acquireUninterruptibly();
//                        ready_Queue.add(task);
//                        processSem.release();
//                    }
//                    else {
//                        System.out.println("Proc. Thread " + task.getID() + "\t | Completed execution.");
//                    }
//                    System.out.println();
//                } else {
//                    System.out.println("Proc. Thread " + task.getID() + "\t | Completed execution.");
//                }
//
//
//            } catch (Exception e) {
//                System.out.println();
//            }
//        }
//    }
//
//    // NSJF implementation
//    public void NSJF_Single() {
//        System.out.println("Dispatcher 0 \t | Running Non-Preemptive Shortest Job First (NSJF) algorithm...");
//
//        while (!ready_Queue.isEmpty()) {
//            try {
//                queueSem.acquireUninterruptibly();
//
//                // Sort ready queue by burst time (ascending)
//                TaskThread task = ready_Queue.stream()
//                        .min((t1, t2) -> Integer.compare(t1.getMaxBurstTime(), t2.getMaxBurstTime()))
//                        .orElse(null);
//
//                if (task != null) {
//                    ready_Queue.remove(task);
//                    System.out.println("Proc. Thread " + task.getID() + "\t | On CPU: 0, MB = " + task.getMaxBurstTime() + ", CB = " + task.getCurrentBurstTime() + ", BT = " + task.getMaxBurstTime() + ", BG = " + task.getMaxBurstTime());
//                }
//
//                queueSem.release();
//
//                // Run the task to completion
//                processSem.acquireUninterruptibly();
//                task.run(task.getMaxBurstTime(), 0);  // run the full burst time
//                processSem.release();
//                System.out.println();
//
//            } catch (Exception e) {
//                System.out.println("The NSJF algorithm couldn't execute completely");
//            }
//        }
//        System.out.println("Main Thread \t | Exiting NSJF");
//    }
//
//    // PSJF implementation
//    public void PSJF_Single() {
//        System.out.println("Dispatcher 0 \t | Running Preemptive Shortest Job First (PSJF) algorithm...");
//
//        PriorityQueue<TaskThread> priorityQueue = new PriorityQueue<>((t1, t2) -> Integer.compare(t1.getMaxBurstTime(), t2.getMaxBurstTime()));
//        long lastArrivalTime = System.currentTimeMillis(); // Track the last time we added a new task
//
//        // Loop while there are tasks in the ready queue or in the priority queue
//        while (!ready_Queue.isEmpty() || !priorityQueue.isEmpty()) {
//            try {
//                // Periodically add a new task to the ready queue
//                if (System.currentTimeMillis() - lastArrivalTime > 1000) { // New task arrives every 1000 ms
//                    int burstTime = random.nextInt(1, 51);  // Random burst time between 1 and 50
//                    TaskThread newTask = new TaskThread(ready_Queue.size(), burstTime); // Create new task
//                    ready_Queue.add(newTask);  // Add to the ready queue
//                    lastArrivalTime = System.currentTimeMillis();  // Update last arrival time
//
//                    System.out.println("Main Thread \t | New task arrived: Task " + newTask.getID() + " with Burst Time: " + burstTime);
//                }
//
//                // Move tasks from the ready queue to the priority queue
//                queueSem.acquireUninterruptibly();
//                priorityQueue.addAll(ready_Queue);
//                ready_Queue.clear();
//                queueSem.release();
//
//                // Pick the task with the shortest burst time
//                TaskThread task = priorityQueue.poll();
//
//                if (task != null) {
//                    System.out.println("Proc. Thread " + task.getID() + "\t | On CPU: 0, MB = " + task.getMaxBurstTime() + ", CB = " + task.getCurrentBurstTime() + ", BT = " + task.getMaxBurstTime() + ", BG = " + task.getMaxBurstTime());
//                }
//
//                // Preemptively process the task until it completes or quantum expires
//                processSem.acquireUninterruptibly();
//                task.run(task.getMaxBurstTime(), 0);  // Run the task to completion (preemptively)
//                processSem.release();
//                System.out.println();
//
//            } catch (Exception e) {
//                System.out.println("The PSJF algorithm couldn't execute completely");
//            }
//        }
//
//        System.out.println("Main Thread \t | Exiting PSJF");
//    }
//
//    public void threadCreation () {
//
//        int burstTime;                  // Random between 1,50
//        int numThreads;                 // Random between 1, 25
//
//        numThreads = random.nextInt(1,26);
//
//        System.out.println ("Number of Threads: " + numThreads);
//        for (int i = 0; i < numThreads; i++) {
//            burstTime = random.nextInt(1, 50);
//            task = new TaskThread(i, burstTime);
//            Thread t = new Thread(task);
//            t.start();
//            ready_Queue.add(task);
//
//            System.out.println("Main Thread \t | Creating process thread " + i);
//        }
//    }
//
//    public void displayQueue_i () {
//        System.out.println("----------Ready Queue-----------");
//        int i = 0;
//        for (TaskThread task: ready_Queue) {
//            System.out.println("Id: " + i + " Max Burst " + task.getMaxBurstTime() + " Current Burst: " + task.getCurrentBurstTime());
//            i++;
//        }
//        System.out.println("--------------------------------");
//
//    }
//
//    @Override
//    public void run() {
//        threadCreation();
//        //reportThreadCreation();
//        System.out.println();
//        displayQueue_i();
//        System.out.println();
//
//        switch (algorithm_Choice) {
//            case 1:
//                long start_FCFS = System.currentTimeMillis();
//                FCFS_Single();
//                long end_FCFS = System.currentTimeMillis();
//                System.out.println("Total Completion Time: " + (end_FCFS - start_FCFS) + " milli-seconds.");
//                break;
//            case 2:
//                long start_RR = System.currentTimeMillis();
//                RR_Single();
//                long end_RR = System.currentTimeMillis();
//                System.out.println("Total Completion Time: " + (end_RR - start_RR) + " milli-seconds.");
//                break;
//            case 3:
//                long start_NPSJF = System.currentTimeMillis();
//                NSJF_Single();
//                long end_NPSJF = System.currentTimeMillis();
//                System.out.println("Total Completion Time: " + (end_NPSJF - start_NPSJF) + " milli-seconds.");
//                break;
//            case 4:
//                long start_PSJF = System.currentTimeMillis();
//                PSJF_Single();
//                long end_PSJF = System.currentTimeMillis();
//                System.out.println("Total Completion Time: " + (end_PSJF - start_PSJF) + " milli-seconds.");
//            default:
//                System.out.println("");
//        }
//
//    }
//
//    /*public static void main(String [] args) {
//        Dispatcher_Single test = new Dispatcher_Single(ready_Queue, quantum, queueSem);
//        Thread t = new Thread(test);
//        t.start();
//    }*/
//
//    public void reportThreadCreation () {
//        int burstTime;
//        int timeQuantum = 5;
//
//        TaskThread t1 = new TaskThread(1, 18);
//        TaskThread t2 = new TaskThread(2, 7);
//        TaskThread t3 = new TaskThread(3, 25);
//        TaskThread t4 = new TaskThread(4, 42);
//        TaskThread t5 = new TaskThread(5, 21);
//
//        Thread uno = new Thread(t1);
//        Thread dos = new Thread(t2);
//        Thread tres = new Thread(t3);
//        Thread quatro = new Thread(t4);
//        Thread cinco = new Thread(t5);
//
//        uno.start();
//        dos.start();
//        tres.start();
//        quatro.start();
//        cinco.start();
//
//        ready_Queue.add(t1);
//        ready_Queue.add(t2);
//        ready_Queue.add(t3);
//        ready_Queue.add(t4);
//        ready_Queue.add(t5);
//
//        System.out.println("Main Thread \t | Creating process thread " + 1);
//        System.out.println("Main Thread \t | Creating process thread " + 2);
//        System.out.println("Main Thread \t | Creating process thread " + 3);
//        System.out.println("Main Thread \t | Creating process thread " + 4);
//        System.out.println("Main Thread \t | Creating process thread " + 5);
//
//
//
//    }
//}
//
