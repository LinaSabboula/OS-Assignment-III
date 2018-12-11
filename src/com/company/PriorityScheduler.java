package com.company;

import java.util.ArrayList;

public class PriorityScheduler {
    private ArrayList<Process> processArray = new ArrayList<Process>();
    int currentTime = 0;
    int waitingTime = 0;
    int turnAroundTime = 0;

    void addProcess(Process p){}

    int findHighestPriority(){}

    Process extractProcess(int index){}

    void ageProcesses(){}

    double averageWaitingTime(){}

    double averageTurnAroundTime(){}

    int getWaitingTime(Process p){}

    int getTurnAroundTime(Process p){}

}
