package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityScheduler {
    private Process[] processes;
    private ArrayList<Process> processQueue = new ArrayList<Process>();
    int currentTime = 0;
    int waitingTime = 0;
    int turnAroundTime = 0;
    static int index = 0;
    int processesCount;

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

        }
    }
    void addProcess(){}

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
        return processQueue.get(index).getArrivalTime();
    }
    Process extractProcess(int index){}

    void ageProcesses(){}

    double averageWaitingTime(){}

    double averageTurnAroundTime(){}

    int getWaitingTime(Process p){}

    int getTurnAroundTime(Process p){}

}
