public class TaskThread extends Thread{
    private int id;
    private int burstTime;
    private int currentBurstTime = 0;
    private boolean isComplete = false;

    TaskThread(int id, int burstTime) {
        this.id = id;
        this.burstTime = burstTime;
    }

    public void run(int allottedBurst, int coreID) {
        try {
            int a = allottedBurst + this.currentBurstTime;

            while (this.currentBurstTime != this.burstTime) {
                if (this.currentBurstTime >= a) {
                    return;
                }

                ++this.currentBurstTime;
                System.out.println("Using CPU:" + coreID + "; Task " + this.id + " ran on burst " + this.currentBurstTime);
            }

            this.isComplete = true;
        }catch (Exception e){
            System.out.println("Error with Task execution - ID:" + id);
        }
    }

    public int getCurrentBurstTime() {
        return this.currentBurstTime;
    }

    public int getMaxBurstTime() {
        return this.burstTime;
    }

    public int getID() {
        return this.id;
    }

    public boolean getIsCompleted() {
        return this.isComplete;
    }
}

