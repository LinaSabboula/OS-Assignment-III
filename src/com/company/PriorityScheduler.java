package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityScheduler {
    private Process[] processes;
    private ArrayList<Process> processQueue = new ArrayList<Process>();
    private int currentTime = 0;
    static int index = 0;
    private int remainingTime;
    private int processesCount;

    //arr should be sorted based on arrival time
    PriorityScheduler(Process[] arr){
        processes = new Process[arr.length];
        for(int i = 0;i < arr.length;i++){
            processes[i] = arr[i];
        }
        processesCount = arr.length;
    }
    void startScheduler(){
        while(processesCount != 0){
            if(currentTime == 0){
                addProcess(processes[0]);
                runProcess(processes[0]);
                while(currentTime != findNextProcess()) { //one process in queue
                    currentTime++;
                    int remainingTime = processQueue.get(0).getBurstTime();
                    remainingTime--;
                }
                //processQueue.get(index).setRemainingTime(remainingTime);
                if (processQueue.get(index).getRemainingTime() == 0)
                    processesCount--;
                addProcess(processes[index + 1]);
                if(index != findHighestPriority()){//higher priority exists in queue
                    processQueue.get(index).waitingTime++;
                }
            }
        }
    }
    void runProcess(Process p){}
    void addProcess(Process p){}

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
    int findNextProcess(){
        return processQueue.get(processQueue.size()).getArrivalTime();
    }
    Process extractProcess(int index){}

    void ageProcesses(){}

    double averageWaitingTime(){}

    double averageTurnAroundTime(){}

    int getWaitingTime(Process p){}

    int getTurnAroundTime(Process p){}

}
