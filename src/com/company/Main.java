package com.company;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter Number of process: ");
        int n = s.nextInt();
        System.out.print("\nEnter RR Quantum: ");
        int rq = s.nextInt();
        System.out.print("\nEnter Context Switch Time: ");
        int t = s.nextInt();
        Process[] processList = new Process[n];
        for (int i = 0; i < n; i++) {
            String name;
            int arrivalTime, burstTime, priority, quantum;
            System.out.print("\nEnter name of process " + (i + 1) + " : ");
            name = s.next();
            System.out.print("\nEnter Arrival Time of process " + (i + 1) + " : ");
            arrivalTime = s.nextInt();
            System.out.print("\nEnter Burst Time of process " + (i + 1) + " : ");
            burstTime = s.nextInt();
            System.out.print("\nEnter Priority of process " + (i + 1) + " : ");
            priority = s.nextInt();
            System.out.print("\nEnter Quantum of process " + (i + 1) + " : ");
            quantum = s.nextInt();
            Process p = new Process(name, arrivalTime, burstTime, priority, quantum);
            processList[i] = p;
        }

        //sort processList first
        Process temp;
        for (int i = 1; i < processList.length; i++) {
            for (int j = i; j > 0; j--) {
                if (processList[j].getArrivalTime() < processList[j - 1].getArrivalTime()) {
                    temp = processList[j];
                    processList[j] = processList[j - 1];
                    processList[j - 1] = temp;
                }
            }
        }

        System.out.println("SRJF: ");
        // Call function

        System.out.println("RR: ");
        // Call function

        System.out.println("Priority Scheduling: ");
        //Call function
        PriorityScheduler priorityScheduler = new PriorityScheduler(processList);
        priorityScheduler.startScheduler();
        System.out.println("======================================");

        System.out.println("AG Scheduling: ");
        //Call function

    }
}



