package com.company;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
//        System.out.print("Enter Number of process: ");
//        int n = s.nextInt();
//        System.out.print("\nEnter RR Quantum: ");
//        int rq = s.nextInt();
//        System.out.print("\nEnter Context Switch Time: ");
//        int t = s.nextInt();
//        Process[] processList = new Process[n];
//        for (int i = 0; i < n; i++) {
//            String name;
//            int arrivalTime, burstTime, priority, quantum;
//            System.out.print("\nEnter name of process " + (i + 1) + " : ");
//            name = s.next();
//            System.out.print("\nEnter Arrival Time of process " + (i + 1) + " : ");
//            arrivalTime = s.nextInt();
//            System.out.print("\nEnter Burst Time of process " + (i + 1) + " : ");
//            burstTime = s.nextInt();
//            System.out.print("\nEnter Priority of process " + (i + 1) + " : ");
//            priority = s.nextInt();
//            System.out.print("\nEnter Quantum of process " + (i + 1) + " : ");
//            quantum = s.nextInt();
//            Process p = new Process(name, arrivalTime, burstTime, quantum, priority);
//            processList[i] = p;
//        }
//
//        //sort processList first
//        Process temp;
//        for (int i = 1; i < processList.length; i++) {
//            for (int j = i; j > 0; j--) {
//                if (processList[j].getArrivalTime() < processList[j - 1].getArrivalTime()) {
//                    temp = processList[j];
//                    processList[j] = processList[j - 1];
//                    processList[j - 1] = temp;
//                }
//            }
//        }
        int t = 5;
        int rq = 6;
        Process[] processList = {
                new Process("P1", 0,17,7,4),
                new Process("P2", 2,6,9,7),
                new Process("P3", 5,11,4,3),
                new Process("P4", 15,4,6,6)
        };
        System.out.print("\nWhich process do you want to you use?: ");
        int choice = s.nextInt();
        if(choice == 1){
            System.out.println("SRJF: ");
            SJFS sjfs= new SJFS(processList,t);
            sjfs.Scheduler();
        }
        else if(choice == 2){
            System.out.println("RR: ");
            RR.RR(processList,rq,t);
        }
        else if(choice == 3){
            System.out.println("Priority Scheduling: ");
            PriorityScheduler priorityScheduler = new PriorityScheduler(processList);
            priorityScheduler.startScheduler();
        }
        else if(choice == 4){
            System.out.println("AG Scheduling: ");
            AGScheduler agScheduler = new AGScheduler();
            agScheduler.run(processList,false);
        }

    }
}
