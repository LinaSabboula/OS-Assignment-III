import java.util.ArrayList;

public class RR {

    static int proNum = 0;
    static String processExecutionOrder = "";
    static int totalWaiting = 0;
    static int totalTurnaround = 0;
    static int minIndex = 0;
    static int curTime = 0;

    static private ArrayList<Process> processes = new ArrayList<Process>();

    static void RR(Process[] p, int quant, int csTime){
        ArrayList<Process> queue = new ArrayList<Process>();
        boolean continueScheduling = true;
        for (int i=0; i<p.length; i++){
            processes.add(p[i]);
            proNum++;

        }
        if (!processes.isEmpty()){
            for (int j=0; j<processes.size(); j++){
                if (processes.get(j).getArrivalTime() < processes.get(0).getArrivalTime()){
                    minIndex = j;
                }
            }
            queue.add(processes.get(minIndex));
            processes.remove(minIndex);
        }
        while (continueScheduling){

            int i = 0;

            while (continueScheduling){

                if (queue.get(i).getBurstTime() > quant){
                    System.out.println("Serving process: " + queue.get(i).getProcessName() + " ...");
                    System.out.println("Waiting time is: " + queue.get(i).getWaitingTime() + " ...");
                    System.out.println("Burst time is: " + queue.get(i).getBurstTime() + " ...");
                    System.out.println("Didin't finish processing, switching process ...");
                    processExecutionOrder += queue.get(i).getProcessName() + " > ";
                    curTime += queue.get(i).getBurstTime() + csTime;
                    while (!processes.isEmpty()){
                        for (int j=0; j<processes.size(); j++){
                            if (processes.get(j).getArrivalTime() < processes.get(0).getArrivalTime()){
                                minIndex = j;
                            }
                        }
                        if (curTime >= processes.get(minIndex).getArrivalTime()){
                            queue.add(processes.get(minIndex));
                            queue.get((queue.size()-1)).setWaitingTime(((queue.size()-1)*quant + (queue.size()-1)*csTime) - queue.get((queue.size()-1)).getArrivalTime());
                            queue.get((queue.size()-1)).setTurnAroundTime(queue.get((queue.size()-1)).getWaitingTime() + queue.get((queue.size()-1)).getBurstTime());
                            processes.remove(minIndex);
                        }
                        else
                            break;
                    }
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
                    if (queue.size() == 1){
                        curTime += queue.get(i).getBurstTime();
                    }
                    else if (queue.size() > 1){
                        curTime += queue.get(i).getBurstTime() + csTime;
                    }
                    while (!processes.isEmpty()){
                        for (int j=0; j<processes.size(); j++){
                            if (processes.get(j).getArrivalTime() < processes.get(0).getArrivalTime()){
                                minIndex = j;
                            }
                        }
                        if (curTime >= processes.get(minIndex).getArrivalTime()){
                            queue.add(processes.get(minIndex));
                            queue.get((queue.size()-1)).setWaitingTime(((queue.size()-1)*quant + (queue.size()-1)*csTime) - queue.get((queue.size()-1)).getArrivalTime());
                            queue.get((queue.size()-1)).setTurnAroundTime(queue.get((queue.size()-1)).getWaitingTime() + queue.get((queue.size()-1)).getBurstTime());
                            processes.remove(minIndex);
                        }
                        else
                            break;
                    }
                    queue.remove(i);
                }

                if (queue.isEmpty()){continueScheduling = false;}
            }
            int avgWaitingTime = totalWaiting/proNum;
            int avgTurnaroundTime = totalTurnaround/proNum;
            System.out.println("Process execution order is: " + processExecutionOrder);
            System.out.println("Average Waiting time is: " + avgWaitingTime);
            System.out.println("Average Turnaround time is: " + avgTurnaroundTime);
        }
    }
}
