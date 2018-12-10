package com.company;

public class Process extends Thread{
    private int processPriority = 0;
    private int burstTime = 0;
    private int quantum = 0;
    private int remainingTime = 0;

    public Process(String n, int bt){
        super(n);
        burstTime = bt;
        remainingTime = burstTime;
    }
    public Process(String n, int bt, int q){
        super(n);
        burstTime = bt;
        remainingTime = burstTime;
        quantum = q;
    }
    public Process(String n, int bt, int q, int p){
        super(n);
        burstTime = bt;
        remainingTime = burstTime;
        quantum = q;
        processPriority = p;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getProcessPriority() {
        return processPriority;
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

    @Override
    public void run(){
        while (remainingTime > 0){
            System.out.println(getName() + " is running!");
            try {
                wait(1000);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
            remainingTime--;
        }
        System.out.println(getName() + " has finished!");
    }
}
