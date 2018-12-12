package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityScheduler {
    private Process[] processes;
    private ArrayList<Process> processQueue = new ArrayList<Process>();
    private int currentTime = 0;
    private int currentProcessIndex = 0;
    private Process currentProcess;
    private int remainingTime;
    private int processesCount;

    //arr should be sorted based on arrival time
    PriorityScheduler(Process[] arr){
        processes = new Process[arr.length];
        for(int i = 0;i < arr.length;i++){
            processes[i] = arr[i];
        }
        processesCount = arr.length;
        currentTime = processes[0].getArrivalTime();
        currentProcess = processes[0];
        currentProcessIndex = 0;
    }
    Process getQueuedProcess(int index){
        return processQueue.get(index);
    }
    void startScheduler(){
        while(processesCount != 0) {
            while(currentTime < nextProcessArrival()) {
                runProcess(currentProcess);
                currentTime++;
                currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);
                for (int i = 0; i < processQueue.size(); i++) {
                    processQueue.get(i).setWaitingTime(processQueue.get(i).getWaitingTime() + 1);
                }
            }if(currentProcess.getRemainingTime() > 0)
                addProcess(currentProcess); //add unfinished process at end of queue
            else{ //if finished
                processesCount--;
            }
           // addProcess(getProcessbyIndex(currentProcessIndex + 1)); //add the next process into queue
            addProcess(getQueuedProcess(currentProcessIndex + 1));
            currentProcessIndex = findHighestPriority();
            currentProcess = processQueue.get(currentProcessIndex);
            processQueue.remove(currentProcess);
        }
    }

    void runProcess(Process p){}
    void addProcess(Process p){
        processQueue.add(p);
    }

    int findHighestPriority(){
        int min = processQueue.get(0).getProcessPriority();
        int priorityIndex = 0;
        for(int i = 1;i < processQueue.size();i++){
            if((processQueue.get(i).getProcessPriority()) < min){
                min = processQueue.get(i).getProcessPriority();
                priorityIndex = i;
            }
        }
        return priorityIndex;
    }
    int nextProcessArrival(){
        return processes[currentProcessIndex + 1].getArrivalTime();
    }
//    Process extractProcess(int index){}
//
//    void ageProcesses(){}
//
//    double averageWaitingTime(){}
//
//    double averageTurnAroundTime(){}
//
//    int getWaitingTime(Process p){}
//
//    int getTurnAroundTime(Process p){}

}
