import java.util.Random;
public class FCFS_Single implements Runnable {

    static Random random = new Random();
    int tID;  // Thread ID
    static int burstTime;                  // Random between 1,50
    static int numThreads;                 // Random between 1, 25

    public static void main (String [] args) {
        numThreads = random.nextInt(1, 26);

        for (int i = 0; i < numThreads; i++) {
            burstTime = random.nextInt(1, 51);

            FCFS_Single fs = new FCFS_Single(i, burstTime);
            Thread t1_FCFS = new Thread(fs);
            t1_FCFS.start();
        }

        System.out.println ("Number of Threads: " + numThreads);
    }

    public FCFS_Single (int tID, int BT) {
        this.tID = tID;
        this.burstTime = BT;
    }


    /*
        How to implement with task thread.
        Assumption:  I assume that  I can have the threads run in FCFS(run) then have them be in the task manager thread as a ready queue.
        From there I can probably have the taskManager(run) call some functions from FCFS until they are finsihed
    * */
    @Override
    public void run() {
        TaskThread taskThread = new TaskThread(tID, burstTime);
    }
}
