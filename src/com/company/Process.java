package com.company;

public class Process {
    private int processPriority = 0;
    private int burstTime = 0;
    private int quantum = 0;
    private int remainingTime = 0;
    private int arrivalTime = 0;
    private String processName;

    public Process(String n,int at, int bt, int q, int p){
        processName=n;
        arrivalTime = at;
        burstTime = bt;
        remainingTime = burstTime;
        quantum = q;
        processPriority = p;
    }

    public int getArrivalTime() { return arrivalTime; }

    public int getBurstTime() {
        return burstTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getProcessPriority() {
        return processPriority;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setProcessPriority(int processPriority) {
        this.processPriority = processPriority;
    }

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

}
