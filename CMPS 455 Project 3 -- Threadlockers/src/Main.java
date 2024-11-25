

public class Main {

   static int quantum;
   static int numCores;
    public static  int checkInput_Quantum (String input) {
        try {
            int value = Integer.parseInt(input);
            if (value >= 2 && value <=10) {
                return value;
            }
        }catch (NumberFormatException e) {
            System.out.println("Invalid input. Please select a number between 2 - 10.");
        }
        return -1;
    }
    public static  int checkInput_Core (String input) {
        try {
            if (input.equals(" ")) {
                return 0;
            }
            int value = Integer.parseInt(input);
            if (value >= 1 && value <=4) {
                return value;
            }
        }catch (NumberFormatException e) {
            System.out.println("Invalid input. Please select a number between 1 - 4.");
        }
        return -1;
    }


    public static void main(String[] args) {
//        quantum = random.nextInt(2, 11);

        try {
            if (args[0].equals("-S")) {
                if (args.length == 1) {
                    System.out.println("invalid input...");
                    return;
                }
                switch (args[1]) {
                    case "1":
                        Dispatcher_Single dispatcher_FCFS = new Dispatcher_Single(Dispatcher_Single.ready_Queue, 0,Dispatcher_Single.queueSem,1, 1);
                        Thread fcfsThread = new Thread(dispatcher_FCFS);
                        fcfsThread.start();
                        //algToUse = Statics.algorithm.FCFS;

                        break;
                    case "2":
                       quantum = checkInput_Quantum(args[2]);


                        if (args[3].equals("-C")) {
                            numCores = checkInput_Core(args[4]);
                        }
                       if (quantum >= 2 && quantum <=10) {
                           Dispatcher_Single dispatcher_RR = new Dispatcher_Single(Dispatcher_Single.ready_Queue, quantum, Dispatcher_Single.queueSem, 2, numCores);
                           Thread rrThread = new Thread(dispatcher_RR);
                           rrThread.start();
                       }
                       else {
                           System.out.println("Invalid input: Please input a number between 2-10.");
                       }

                        break;
                    case "3":
                        //algToUse = Statics.algorithm.NSJF;
                        Dispatcher_Single dispatcherNSJF = new Dispatcher_Single(Dispatcher_Single.ready_Queue, Dispatcher_Single.quantum, Dispatcher_Single.queueSem, 3,1);
                        Thread nsjfThread = new Thread(dispatcherNSJF);
                        nsjfThread.start();
                        break;
                    case "4":
                        //algToUse = Statics.algorithm.PSJF;
                        Dispatcher_Single dispatcherPSJF = new Dispatcher_Single(Dispatcher_Single.ready_Queue, Dispatcher_Single.quantum, Dispatcher_Single.queueSem, 4,1);
                        Thread psjfThread = new Thread(dispatcherPSJF);
                        psjfThread.start();
                        break;
                    default:
                        System.out.println("Wrong Input for Task #");
                }
                if (args.length > 2) {
                    if (args[2].equals("-C")) {
                        numCores = checkInput_Core(args[3]);
                    }
                } else if (args.length > 3) {
                    if (args[3].equals("-C")) {
                        numCores = checkInput_Core(args[4]);
                    }
                }


            } else if (args[0].equals("-C")) {
                numCores = checkInput_Core(args[1]);

                if (args[2].equals("-S")) {
                    switch (args[3]) {
                        case "1":
                            Dispatcher_Single dispatcher_FCFS = new Dispatcher_Single(Dispatcher_Single.ready_Queue, 0,Dispatcher_Single.queueSem,1, numCores);
                            Thread fcfsThread = new Thread(dispatcher_FCFS);
                            fcfsThread.start();
                            break;
                        case "2":
                            if (quantum >= 2 && quantum <=10) {
                                Dispatcher_Single dispatcher_RR = new Dispatcher_Single(Dispatcher_Single.ready_Queue, quantum, Dispatcher_Single.queueSem, 2, numCores);
                                Thread rrThread = new Thread(dispatcher_RR);
                                rrThread.start();
                            }
                            else {
                                System.out.println("Invalid input: Please input a number between 2-10.");
                            }
                            break;
                        case "3":
                            Dispatcher_Single dispatcherNSJF = new Dispatcher_Single(Dispatcher_Single.ready_Queue, Dispatcher_Single.quantum, Dispatcher_Single.queueSem, 3,numCores);
                            Thread nsjfThread = new Thread(dispatcherNSJF);
                            nsjfThread.start();
                            break;
                        case "4":
                            Dispatcher_Single dispatcherPSJF = new Dispatcher_Single(Dispatcher_Single.ready_Queue, Dispatcher_Single.quantum, Dispatcher_Single.queueSem, 4,numCores);
                            Thread psjfThread = new Thread(dispatcherPSJF);
                            psjfThread.start();
                            break;
                        default:
                            System.out.println("Wrong Input for Task #");
                    }
                }
                //Dispatcher(Dispatcher.readyqueue, args[/*Argument number*/], Dispatcher.semaphore);

            }
        } catch (Exception e) {
            System.out.println("Wrong input, try again | Ex: \"-S # -C #\" or \"-C # -S #\" | Place a secondary number \"-S 2 #\" for RR Burst Time");
        }
        //RunMain(algToUse, coresToUse, quantumTime);
    }


}