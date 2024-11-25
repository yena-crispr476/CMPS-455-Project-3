

public class Main {

   static int quantum;
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
                        Dispatcher_Single dispatcher_FCFS = new Dispatcher_Single(Dispatcher_Single.ready_Queue, 0,Dispatcher_Single.queueSem,1);
                        Thread fcfsThread = new Thread(dispatcher_FCFS);
                        fcfsThread.start();
                        //algToUse = Statics.algorithm.FCFS;

                        break;
                    case "2":
                       quantum = checkInput_Quantum(args[2]);
                       if (quantum >= 2 && quantum <=10) {
                           Dispatcher_Single dispatcher_RR = new Dispatcher_Single(Dispatcher_Single.ready_Queue, quantum, Dispatcher_Single.queueSem, 2);
                           Thread rrThread = new Thread(dispatcher_RR);
                           rrThread.start();
                       }
                       else {
                           System.out.println("Invalid input: Please input a number between 2-10.");
                       }

                        if (args[3].equals("-C")) {
                            //quantumTime = Integer.parseInt(args[2]);
                            //coresToUse = Integer.parseInt(args[4]);
                        }
                        break;
                    case "3":
                        //algToUse = Statics.algorithm.NSJF;
                        Dispatcher_Single dispatcherNSJF = new Dispatcher_Single(Dispatcher_Single.ready_Queue, Dispatcher_Single.quantum, Dispatcher_Single.queueSem, 3);
                        Thread nsjfThread = new Thread(dispatcherNSJF);
                        nsjfThread.start();
                        break;
                    case "4":
                        //algToUse = Statics.algorithm.PSJF;
                        Dispatcher_Single dispatcherPSJF = new Dispatcher_Single(Dispatcher_Single.ready_Queue, Dispatcher_Single.quantum, Dispatcher_Single.queueSem, 4);
                        Thread psjfThread = new Thread(dispatcherPSJF);
                        psjfThread.start();
                        break;
                    default:
                        System.out.println("Wrong Input for Task #");
                }
                if (args.length > 2) {
                    if (args[2].equals("-C")) {
                        //coresToUse = Integer.parseInt(args[3]);
                    }
                } else if (args.length > 3) {
                    if (args[3].equals("-C")) {
                        //coresToUse = Integer.parseInt(args[4]);
                    }
                }


            } else if (args[0].equals("-C")) {
                //coresToUse = Integer.parseInt(args[1]);

                if (args[2].equals("-S")) {
                    switch (args[3]) {
                        case "1":
                            //algToUse = Statics.algorithm.FCFS;
                            break;
                        case "2":
                            //algToUse = Statics.algorithm.RR;
                            if (args.length == 5) {
                                //quantumTime = Integer.parseInt(args[4]);
                            }
                            break;
                        case "3":
                            //algToUse = Statics.algorithm.NSJF;
                            break;
                        case "4":
                            //algToUse = Statics.algorithm.PSJF;
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