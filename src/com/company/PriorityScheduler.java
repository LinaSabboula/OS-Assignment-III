package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

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
                    currentTime++;
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

                if (currentTime == processes[nextProgress].getArrivalTime()) { //new process arrive
                    addProcess(processes[nextProgress]);
                    if (nextProgress != processes.length - 1)
                        nextProgress++;
                    }
            } //higher priority exists
            currentProcess = findHighestPriority();
        } //no more processes

        calculateTurnAroundTime();
        printWaitingTime();
        printTurnAroundTime();
        printAvgWait();
        printAvgTurnAround();
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
        System.out.print(" " + currentProcess.getProcessName() + " is running...\n");
    }

    void addProcess(Process p){
        processQueue.add(p);
    }

    void removeProcess(Process p){
        System.out.print("Time: "+ currentTime);
        System.out.print(" "+ currentProcess.getProcessName()+ " is running and finishing...\n");
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
                processQueue.get(i).setProcessPriority(processQueue.get(i).getProcessPriority() - 1);
            }
        }
    }

    void printWaitingTime(){
        System.out.print("Waiting times for each process: ");
        for(int i = 0;i < processes.length;i++){
            System.out.print(processes[i].getProcessName() + ": " + processes[i].getWaitingTime()+ " ");
        }
    }

    void printTurnAroundTime(){
        System.out.print("\nTurn around times times for each process: ");
        for(int i = 0;i < processes.length;i++){
            System.out.print(processes[i].getProcessName() + ": " + processes[i].getTurnAroundTime() + " ");
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
