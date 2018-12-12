package com.company;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	Scanner s = new Scanner(System.in);
	System.out.println("Enter Number of process: ");
	int n= s.nextInt();
	System.out.println("Enter RR Quantam: ");
	int rq = s.nextInt();
	System.out.println("Enter Context Switch Time: ");
	int t = s.nextInt();
	ArrayList <Process> processList= new ArrayList<Process>();
	for(int i=0;i<n;i++) {
        Process p= new Process();
        String name, int arrivalTime, burstTime,priority, quantam;
        System.out.println("Enter name of process" + i+1 +" : "  );
        name= s.next();
        System.out.println("Enter Arrival Time of process" + i+1 +" : "  );
        arrivalTime= s.nextInt();
        System.out.println("Enter Burst Time of process" + i+1 +" : "  );
        burstTime= s.nextInt();
        System.out.println("Enter Priority of process" + i+1 +" : "  );
        priority= s.nextInt();
        System.out.println("Enter Quantum of process" + i+1 +" : "  );
        quantam=s.nextInt();
        p(name,arrivalTime,burstTime,priority,quantam);
	    processList.add(p);
        }


System.out.println("SRJF: " );
    // Call function

System.out.println("RR: " );
    // Call function

System.out.println("Priority Scheduling: ");
    //Call function

System.out.println("AG Scheduling: ");
    //Call function




    }
}
