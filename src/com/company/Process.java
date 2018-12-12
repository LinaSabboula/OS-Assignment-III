package com.company;

public class Process {
    private int processPriority = 0;
    private int burstTime = 0;
    private int quantum = 0;
    private int remainingTime = 0;
    private int arrivalTime = 0;
    private int waitingTime = 0;
    private String processName;
    private int turnAroundTime = 0;

    public Process(String n,int at, int bt, int q, int p){
        processName=n;
        arrivalTime = at;
        burstTime = bt;
        remainingTime = burstTime;
        quantum = q;
        processPriority = p;
    }

    public int getArrivalTime() { return arrivalTime; }

    public int getTurnAroundTime(){return turnAroundTime;}

    public int getBurstTime() {
        return burstTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getProcessPriority() {
        return processPriority;
    }

    public int getWaitingTime(){return waitingTime;}

    public int getQuantum() {
        return quantum;
    }

    public void setRemainingTime(int remainingTime){ this.remainingTime = remainingTime;}

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setProcessPriority(int processPriority) {
        this.processPriority = processPriority;
    }

    public void setWaitingTime(int waitingTime){this.waitingTime = waitingTime;}

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public void setTurnAroundTime(int turnAroundTime){this.turnAroundTime = turnAroundTime;}

}
