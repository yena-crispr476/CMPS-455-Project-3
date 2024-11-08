public class Main {
    public static void main(String[] args) {

        try{
            if(args[0].equals("-S")){
                if (args.length==1){
                    System.out.println("invalid input...");
                    return;
                }
                switch(args[1]){
                    case "1":
                        //algToUse = Statics.algorithm.FCFS;
                        break;
                    case "2":
                        //algToUse = Statics.algorithm.RR;
                        if(args[3].equals("-C")){
                            //quantumTime = Integer.parseInt(args[2]);
                            //coresToUse = Integer.parseInt(args[4]);
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
                if (args.length>2){
                    if(args[2].equals("-C")) {
                        //coresToUse = Integer.parseInt(args[3]);
                    }
                }else if(args.length>3){
                    if (args[3].equals("-C")){
                        //coresToUse = Integer.parseInt(args[4]);
                    }
                }


            }
            else if(args[0].equals("-C")){
                //coresToUse = Integer.parseInt(args[1]);

                if(args[2].equals("-S")){
                    switch(args[3]) {
                        case "1":
                            //algToUse = Statics.algorithm.FCFS;
                            break;
                        case "2":
                            //algToUse = Statics.algorithm.RR;
                            if(args.length == 5){
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

            }
        } catch (Exception e) {
            System.out.println("Wrong input, try again | Ex: \"-S # -C #\" or \"-C # -S #\" | Place a secondary number \"-S 2 #\" for RR Burst Time");
        }
        //RunMain(algToUse, coresToUse, quantumTime);
    }
}