import java.util.ArrayList;

public class RR {

    static int proNum = 0;
    static String processExecutionOrder = "";
    static int totalWaiting = 0;
    static int totalTurnaround = 0;
    static int minIndex = 0;

    static private ArrayList<Process> processes = new ArrayList<Process>();

    static void RR(Process[] p, int quant, int csTime){
        ArrayList<Process> queue = new ArrayList<Process>();
        boolean continueScheduling = true;
        for (int i=0; i<p.length; i++){
            processes.add(p[i]);
            proNum++;

        }
        while (!processes.isEmpty()){
            for (int j=0; j<processes.size(); j++){
                if (processes.get(j).getArrivalTime() < processes.get(0).getArrivalTime()){
                    minIndex = j;
                }
            }
            queue.add(processes.get(minIndex));
            queue.get(minIndex).setWaitingTime(queue.get(minIndex).getArrivalTime());
            processes.remove(minIndex);
        }

        for (int i=0; i<queue.size(); i++){
            queue.get(i).setWaitingTime(i*quant - queue.get(i).getArrivalTime() + i*csTime);
            queue.get(i).setTurnAroundTime(queue.get(i).getWaitingTime() + queue.get(i).getBurstTime());
//            queue.get(i).setWaitingTime((i)* quant);
//            queue.get(i).setTurnAroundTime(queue.get(i).getWaitingTime())
//            queueSize++;
        }

        int i = 0;
        while (continueScheduling){
            if (queue.get(i).getBurstTime() > quant){
                System.out.println("Serving process: " + queue.get(i).getProcessName() + " ...");
                System.out.println("Waiting time is: " + queue.get(i).getWaitingTime() + " ...");
                System.out.println("Burst time is: " + queue.get(i).getBurstTime() + " ...");
                System.out.println("Didin't finish processing, switching process ...");
                processExecutionOrder += queue.get(i).getProcessName() + " > ";
                queue.add(queue.get(i));
                queue.remove(queue.get(i));
                queue.get(queue.size()-1).setWaitingTime(queue.get(queue.size()-1).getWaitingTime() + (queue.size()-1)*quant + (queue.size()-1)*csTime);
                queue.get(queue.size()-1).setTurnAroundTime(queue.get(queue.size()-1).getTurnAroundTime() + queue.get(queue.size()-1).getWaitingTime());
                queue.get(queue.size()-1).setBurstTime(queue.get(queue.size()-1).getBurstTime() - quant);
            }
            else if (queue.get(i).getBurstTime() <= quant){
                totalWaiting += queue.get(i).getWaitingTime();
                totalTurnaround += queue.get(i).getTurnAroundTime();
                System.out.println("Serving process: " + queue.get(i).getProcessName() + " ...");
                System.out.println("Waiting time is: " + queue.get(i).getWaitingTime() + " ...");
                System.out.println("Turnaround time is: " + queue.get(i).getTurnAroundTime() + " ...");
                System.out.println("Burst time is: " + queue.get(i).getBurstTime() + " ...");
                processExecutionOrder += queue.get(i).getProcessName() + " > ";
                System.out.println("Process finished processing ...");
                queue.remove(i);
                if (queue.isEmpty()){continueScheduling = false;}

            }
//            for (int i=0; i<queue.size(); i++){
//                if (queue.get(i).getBurstTime() > quant){
//                    if (queue.size()==1){
//                        System.out.println("Serving process: " + queue.get(i).getProcessName() + " ...");
//                        System.out.println("Waiting time is: " + queue.get(i).getWaitingTime() + " ...");
//                        System.out.println("Didin't finish processing, switching process ...");
//                        processExecutionOrder += queue.get(i).getProcessName() + " > ";
//                        queue.get(i).setTurnAroundTime(queue.get(i).getTurnAroundTime() + quant);
//                        queue.get(i).setBurstTime((queue.get(i).getBurstTime()) - (quant));
//                        queue.add(queue.get(i));
////                        queue.remove(queue.get(i));
//                    }
//                    else if (queue.size()!=1){
//                        System.out.println("Serving process: " + queue.get(i).getProcessName() + " ...");
//                        System.out.println("Waiting time is: " + queue.get(i).getWaitingTime() + " ...");
//                        System.out.println("Didin't finish processing, switching process ...");
//                        processExecutionOrder += queue.get(i).getProcessName() + " > ";
//                        queue.get(i).setWaitingTime(queue.get(i).getWaitingTime() + (i)* quant);
//                        queue.get(i).setTurnAroundTime(queue.get(i).getTurnAroundTime() + quant);
//                        queue.get(i).setBurstTime((queue.get(i).getBurstTime()) - (quant));
//                        queue.add(queue.get(i));
////                        queue.remove(queue.get(i));
//                    }
//                }
//                else if (queue.get(i).getBurstTime() <= quant){
//                    queue.get(i).setTurnAroundTime(queue.get(i).getTurnAroundTime() + queue.get(i).getBurstTime());
//                    System.out.println("Serving process: " + queue.get(i).getProcessName() + " ...");
//                    System.out.println("Waiting time is: " + queue.get(i).getWaitingTime() + " ...");
//                    System.out.println("Turnaround time is: " + queue.get(i).getTurnAroundTime() + " ...");
//                    System.out.println("Process finished processing ...");
//                    totalWaiting += queue.get(i).getWaitingTime();
//                    totalTurnaround += queue.get(i).getTurnAroundTime();
//                    processExecutionOrder += queue.get(i).getProcessName() + " > ";
//                    queue.get(i).setBurstTime(0);
//                    queue.remove(i);
//                    if (queue.isEmpty()){continueScheduling = false;}
//                }
//            }
        }
        int avgWaitingTime = totalWaiting/proNum;
        int avgTurnaroundTime = totalTurnaround/proNum;
        System.out.println("Process execution order is: " + processExecutionOrder);
        System.out.println("Average Waiting time is: " + avgWaitingTime);
        System.out.println("Average Turnaround time is: " + avgTurnaroundTime);
    }
}
