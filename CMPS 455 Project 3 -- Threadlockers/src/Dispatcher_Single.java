import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Dispatcher_Single implements Runnable{         // Will act as the ready queue for the algorithms. Must be Semaphore or lock protected.
                                                            // TaskThread will act as a "CPU"

    int clockCycles;
    int burstTime;
    private int quantum;
    Semaphore queueSem;
    TaskThread taskthread;
    static Random random = new Random();


    Queue<TaskThread> ready_Queue = new LinkedList<>();

    public Dispatcher_Single (Queue <TaskThread> queue, int quantum, Semaphore sem) {
        this.ready_Queue = queue;
        this.quantum = quantum;
        this.queueSem = sem;
    }

    /*
    * Best to run the different algorithms in the Dispatcher class instead of as separate classes.
    * */

    public void FCFS_Single () {    // Completes processes to completetion as they come in the queue. FIFO

    }

    public void RR_Single (int timeQuantum) {  // Complete in a FIFO manner, but each process executes until the time quantum is completed. Then place the thread back into the CPU for later execution

    }

    public void threadCreation () {
        int tID;  // Thread ID
        int burstTime;                  // Random between 1,50
        int numThreads;                 // Random between 1, 25

        numThreads = random.nextInt(1,26);

        System.out.println ("Number of Threads: " + numThreads);
        for (int i = 0; i < numThreads; i++) {
            burstTime = random.nextInt(1, 50);
            taskthread = new TaskThread(i, burstTime);
            Thread t = new Thread(taskthread);
            t.start();
            ready_Queue.add(taskthread);

            System.out.println("Main Thread \t | Creating process thread" + i);
        }
    }

    public void displayQueue_i () {
        System.out.println("----------Ready Queue----------");
        int i = 0;
        for (TaskThread task: ready_Queue) {
            System.out.println("Id: " + i + " Max Burst " + task.getMaxBurstTime() + " Current Burst: " + task.getCurrentBurstTime());
            i++;
        }

    }
    
    @Override
    public void run() {
        threadCreation();
        displayQueue_i();
        int algorithmNumber = 0;
        switch (algorithmNumber) {
            case 1:
                FCFS_Single();
                break;
            case 2:
                RR_Single(3);
                break;
            default:
                System.out.println("Hello");
        }

    }
}
