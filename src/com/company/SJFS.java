package com.company;
import java.util.ArrayList;

public class SJFS {
    int contextSwitch;
    int numberOfProcesses = 0;
    static int currentTime=0;
    Process[] processes;
    boolean continueScheduling = true;
    private ArrayList<Process> processQueue = new ArrayList<Process>();

    SJFS(Process[] arr, int ct) {
        contextSwitch = ct;
        processes = new Process[arr.length];
        for (int i = 0; i < arr.length; i++) {
            processes[i] = arr[i];
        }
    }

    Process minProcess;


    void addProcess(Process p) {
        processQueue.add(p);
        numberOfProcesses++;

    }
    public void Scheduler() {
        int minIndex = 0;
        int checkAllProcessIn=0;
        Process currentProcess= processes[0];
        while (currentTime<=currentProcess.getArrivalTime() || numberOfProcesses!=0 || checkAllProcessIn!=processes.length) {

            for (int i = 0; i < processes.length; i++) {
                if (currentTime == processes[i].getArrivalTime()) {
                    addProcess(processes[i]);
                    checkAllProcessIn++;
                }
            }
            if (!processQueue.isEmpty()) {
                int min = processQueue.get(0).getRemainingTime();
                for (int i = 0; i < numberOfProcesses; i++) {
                    Process tempProcess = processQueue.get(i);
                    if (tempProcess.getRemainingTime() <=min) {
                        min = tempProcess.getRemainingTime();
                        if(min==currentProcess.getRemainingTime())
                            minIndex = processQueue.indexOf(currentProcess);
                        else
                            minIndex = i;
                    }
                }

                if (currentProcess.getProcessName() != processQueue.get(minIndex).getProcessName() ) {
                    System.out.println("Switching..");
                    int count=0;
                    while(count!=contextSwitch ){
                        currentTime += 1;
                        for (int i = 0; i < processes.length; i++) {
                            if (currentTime == processes[i].getArrivalTime()) {
                                addProcess(processes[i]);
                                checkAllProcessIn++;
                            }
                        }
                        count++;
                    }
                }

                minProcess = processQueue.get(minIndex);
                currentProcess = minProcess;

                int executeTime = minProcess.getRemainingTime();
                executeTime--;
                minProcess.setRemainingTime(executeTime);

                System.out.println("Time: " + currentTime + " Process name: " + minProcess.getProcessName());

                if (minProcess.getRemainingTime() == 0) {
                    int finishTime = currentTime+1;
                    currentProcess.setWaitingTime(finishTime - currentProcess.getBurstTime() - currentProcess.getArrivalTime());
                    currentProcess.setTurnAroundTime(finishTime - currentProcess.getArrivalTime());
                    processQueue.remove(currentProcess);
                    numberOfProcesses--;
                    System.out.println("Process " + minProcess.getProcessName() + " Finished");
                }
            }
            else
                System.out.println("No process.");

            currentTime++;
        }
        double avgWaitingTime=0;
        double avgTurnAroundTime=0;
        System.out.println("Processes  Burst time  Waiting time  Turn around time");
        for(int i=0; i<processes.length;i++){
            System.out.println( processes[i].getProcessName() +"              "
                    + processes[i].getBurstTime() + "          "
                    + processes[i].getWaitingTime() + "                    "+
                    processes[i].getTurnAroundTime());
            avgWaitingTime+= processes[i].getWaitingTime();
            avgTurnAroundTime+= processes[i].getTurnAroundTime();
        }

        System.out.println("Average waiting time = "+ avgWaitingTime/processes.length);
        System.out.println("Average turn around time = "+ avgTurnAroundTime/processes.length);
    }


}

