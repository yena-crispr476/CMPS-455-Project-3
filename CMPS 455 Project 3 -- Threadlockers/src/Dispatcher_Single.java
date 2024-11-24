import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Dispatcher_Single implements Runnable{         // Will act as the ready queue for the algorithms. Must be Semaphore or lock protected.
                                                            // TaskThread will act as a "CPU"

    int clockCycles;
    int burstTime;
    private static int quantum;
    static Semaphore queueSem = new Semaphore(1);
    static Semaphore processSem = new Semaphore(1);
    TaskThread task;
    static Random random = new Random();


    static Queue<TaskThread> ready_Queue = new LinkedList<>();

    public Dispatcher_Single (Queue <TaskThread> queue, int quantum, Semaphore sem) {
        this.ready_Queue = queue;
        this.quantum = quantum;
        this.queueSem = sem;
    }

    /*
    * Best to run the different algorithms in the Dispatcher class instead of as separate classes.
    * Peek looks at the first item in the queue
    * Poll looks at item and removes it from the queue.
    * */

    public void FCFS_Single () {    // Completes processes to completion as they come in the queue. FIFO
        System.out.println("Dispatcher 0 \t | Running FCFS algorithm..");
        while (!ready_Queue.isEmpty()) {
            try {
                queueSem.acquireUninterruptibly();
                TaskThread task = ready_Queue.poll();

                if (task != null) {
                    System.out.println("Proc. Thread " + task.getID() + "\t | On CPU: 0, MB = " + task.getMaxBurstTime() + ", CB = " + task.getCurrentBurstTime() + ", BT = " + task.getMaxBurstTime() + ", BG = " + task.getMaxBurstTime());
                }
                queueSem.release();

                processSem.acquireUninterruptibly();
                for (int i = 0; i < task.getMaxBurstTime(); i++) {
                    //System.out.println("Proc Thead" + task.getID() + "\t | Using CPU 0; On Burst " + task.getCurrentBurstTime() + ".");

                    task.run(1, 0);
                }
                processSem.release();

                System.out.println();

            } catch (Exception e) {
                System.out.println("The FCFS algorithm couldn't execute completely");
            }


            // One instance in the queue
                /*queueSem.acquireUninterruptibly();  // Aquire Semaphore for queue
                ready_Queue.peek(); //Peeks at the item to get the information before removing
                System.out.println("Proc. Thread " + task.getID()+  " \t | On CPU: 0, MB= " + task.getMaxBurstTime() + ", CB= " + ready_Queue.peek().getCurrentBurstTime() + ", BT = " + task.getMaxBurstTime() + ", BG = " + task.getMaxBurstTime() );
                if (task != null) {
                    task.run(task.getMaxBurstTime(), 0);
                }*/

        }
        System.out.println("Main Thread \t | Exiting");
    }

    public void RR_Single (int timeQuantum) {  // Complete in a FIFO manner, but each process executes until the time quantum is completed. Then place the thread back into the CPU for later execution
        System.out.println("Dispatcher 0 \t | Running Round Robin algorithm. Time Quantum: " + timeQuantum);

        while (!ready_Queue.isEmpty()) {
            try {
                queueSem.acquireUninterruptibly();
                TaskThread task = ready_Queue.poll();
                System.out.println("Proc. Thread " + task.getID() + "\t | On CPU: 0, MB = " + task.getMaxBurstTime() + ", CB = " + task.getCurrentBurstTime() + ", BT = " + task.getMaxBurstTime() + ", BG = " + task.getMaxBurstTime());
                queueSem.release();
                if (task != null) {
                    task.run(timeQuantum, 0);


                    if (!task.getIsCompleted()) {
                        processSem.acquireUninterruptibly();
                        ready_Queue.add(task);
                        processSem.release();
                    }
                    else {
                        System.out.println("Proc. Thread " + task.getID() + "\t | Completed execution.");
                    }
                    System.out.println();
                } else {
                    System.out.println("Proc. Thread " + task.getID() + "\t | Completed execution.");
                }



               /* for (int i = 0; i < (timeQuantum); i++) {
                    *//*System.out.println("Proc. Thread " + task.getID() +
                            "\t | Using CPU 0; On Burst " + task.getCurrentBurstTime() + ".");*//*
                    task.run(timeQuantum, 0);
                    if (task.getCurrentBurstTime() < task.getMaxBurstTime()) {
                        ready_Queue.add(task); // Re-add task to the queue if incomplete
                    } else {
                        System.out.println("Proc. Thread " + task.getID() + "\t | Completed execution.");
                    }
                    queueSem.release();
                }*/
                ;

            } catch (Exception e) {
                System.out.println();
            }
        }
    }

    public void threadCreation () {
        int tID;  // Thread ID
        int burstTime;                  // Random between 1,50
        int numThreads;                 // Random between 1, 25

        numThreads = random.nextInt(1,26);

        System.out.println ("Number of Threads: " + numThreads);
        for (int i = 0; i < numThreads; i++) {
            burstTime = random.nextInt(1, 50);
            task = new TaskThread(i, burstTime);
            Thread t = new Thread(task);
            t.start();
            ready_Queue.add(task);

            System.out.println("Main Thread \t | Creating process thread " + i);
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
        System.out.println();
        displayQueue_i();
        System.out.println();
        int algorithmNumber = 2;
        switch (algorithmNumber) {
            case 1:
                FCFS_Single();
                break;
            case 2:
                RR_Single(3);
                break;
            case 3:
                // NPSJF
                break;
            case 4:
                //PSJF
            default:
                System.out.println("");
        }

    }

    public static void main(String [] args) {
        Dispatcher_Single test = new Dispatcher_Single(ready_Queue, quantum, queueSem);
        Thread t = new Thread(test);
        t.start();
    }
}
