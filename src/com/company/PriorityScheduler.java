package com.company;

import java.util.ArrayList;


public class PriorityScheduler {
    private Process[] processes;
    private ArrayList<Process> processQueue = new ArrayList<Process>();
    private int currentTime = 0;
    private Process currentProcess;
    private int processesCount;
    private int nextProgress;

    //arr should be sorted based on arrival time
    PriorityScheduler(Process[] arr){
        processes = new Process[arr.length];
        for(int i = 0;i < arr.length;i++){
            processes[i] = arr[i];
        }
        processesCount = arr.length;
        processQueue.add(processes[0]);
        currentTime = processes[0].getArrivalTime();
        currentProcess = processes[0];
        nextProgress = 1;
    }

    void startScheduler(){
        while (processesCount != 0){
            while(currentProcess == findHighestPriority()) {
                if (currentProcess != null){
                    runProcess();
                    checkNextArrival();
                    currentTime++;
                    checkNextArrival();
                    currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);
                    for (int i = 0; i < processQueue.size(); i++) {
                        if (currentProcess != processQueue.get(i))
                            processQueue.get(i).setWaitingTime(processQueue.get(i).getWaitingTime() + 1);
                    }
                    ageProcesses();

                    if (currentProcess.getRemainingTime() == 0) {
                        processesCount--;
                        removeProcess(currentProcess);
                    }
                }
                else currentTime++; //no process running


            } //higher priority exists
            currentProcess = findHighestPriority();
        } //no more processes

        calculateTurnAroundTime();
        printWaitingTime();
        printTurnAroundTime();
        printAvgWait();
        printAvgTurnAround();
    }
    void checkNextArrival(){
        if (currentTime == processes[nextProgress].getArrivalTime()) { //new process arrive
            addProcess(processes[nextProgress]);
            if (nextProgress != processes.length - 1)
                nextProgress++;
        }
    }
    void printAvgWait(){
        double avgWait = averageWaitingTime();
        System.out.println("\nAverage waiting time: " + avgWait);
    }

    void printAvgTurnAround(){
        double avgTurnAround = averageTurnAroundTime();
        System.out.println("Average turnaround time: " + avgTurnAround);
    }

    void runProcess(){
        System.out.print("Time: "+ currentTime);
        System.out.print(" " + currentProcess.getProcessName()
                + " is running with priority " + currentProcess.getProcessPriority()+ "\n");
    }

    void addProcess(Process p){
        processQueue.add(p);
        System.out.println("         " + p.getProcessName() + " arrived at time "+ currentTime
                + " with priority " + p.getProcessPriority());
    }

    void removeProcess(Process p){
        System.out.print("Time: "+ currentTime);
        System.out.print(" "+ currentProcess.getProcessName()+
                " is running and finishing with priority " + currentProcess.getProcessPriority()+ "\n");
        processQueue.remove(p);
    }

    Process findHighestPriority() {
        if (processQueue.size() != 0) {
            Process temp = processQueue.get(0);
            int min = processQueue.get(0).getProcessPriority();
            for (int i = 0; i < processQueue.size(); i++) {
                if ((processQueue.get(i).getProcessPriority()) < min) {
                    min = processQueue.get(i).getProcessPriority();
                    temp = processQueue.get(i);
                }
            }
            return temp;
        }
        return null;
    }

    void ageProcesses(){
        for(int i = 0;i < processQueue.size();i++){
            if(processQueue.get(i).getWaitingTime() > 5){
                if(processQueue.get(i) != currentProcess
                //   && processQueue.get(i).getProcessPriority() != 0
                ) {
                    System.out.print("         Process "+ processQueue.get(i).getProcessName() + " priority changed from " + processQueue.get(i).getProcessPriority());
                    processQueue.get(i).setProcessPriority(processQueue.get(i).getProcessPriority() - 1);
                    System.out.println(" to " + processQueue.get(i).getProcessPriority() + " at time " + currentTime);
                }
            }
        }
    }

    void printWaitingTime(){
        System.out.println("Waiting times for each process: ");
        for(int i = 0;i < processes.length;i++){
            System.out.println(processes[i].getProcessName() + ": " + processes[i].getWaitingTime()+ " ");
        }
    }

    void printTurnAroundTime(){
        System.out.println("Turn around times times for each process: ");
        for(int i = 0;i < processes.length;i++){
            System.out.println(processes[i].getProcessName() + ": " + processes[i].getTurnAroundTime() + " ");
        }
    }

    void calculateTurnAroundTime(){
        for(int i = 0;i < processes.length;i++){
            processes[i].setTurnAroundTime(processes[i].getWaitingTime() + processes[i].getBurstTime());
        }
    }
    double averageWaitingTime(){
        double avgWait = 0;
        for(int i =0;i < processes.length;i++){
            avgWait += processes[i].getWaitingTime();
        }
        return avgWait/processes.length;
    }

    double averageTurnAroundTime(){
        double avgTurnAround = 0;
        for(int i =0;i < processes.length;i++){
            avgTurnAround += processes[i].getTurnAroundTime();
        }
        return avgTurnAround/processes.length;
    }

}
