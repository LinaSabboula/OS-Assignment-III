package com.company;

import java.util.*;

public class AGScheduler {

    public class ProcessScheduleElement{
        Process process;
        int timeFrom;
        int timeTo;
        ProcessScheduleElement(Process p, int tF){
            process = p;
            timeFrom = tF;
        }
        ProcessScheduleElement(Process p, int tF, int tT){
            process = p;
            timeFrom = tF;
            timeTo = tT;
        }
    }

    private Queue<Process> FCFSqueue;
    private List<Process> NPPqueue;
    private List<Process> SJFqueue;
    private int currentTimeTick = 0;
    private int currentProcessRunningTime = 1;
    private int currentProcessQueueLevel = 0;
    private Process currentRunningProcess;
    private HashMap<Process, ArrayList<Integer>> quantumTimes;
    private ArrayList<ProcessScheduleElement> processSchedule;


    public void run(Process[] processList, boolean goSlow){
        // hashmap of process and quantum time (mapped to processList)
        quantumTimes = new HashMap<>(processList.length);

        // init hashmap and fill with initial quantum time
        for (int i = 0; i < processList.length; i++) {
            quantumTimes.put(processList[i], new ArrayList<>());
            quantumTimes.get(processList[i]).add(processList[i].getQuantum());
        }

        // make the ready queues and the schedule
        processSchedule = new ArrayList<>();
        FCFSqueue = new LinkedList<Process>();
        NPPqueue = new ArrayList<Process>();
        SJFqueue = new ArrayList<Process>();

        int sleepingProcessCount = processList.length;
        currentProcessQueueLevel = 0;
        currentTimeTick = 0;
        currentProcessRunningTime = 0;
        currentRunningProcess = null;
        while (sleepingProcessCount > 0
                || currentRunningProcess != null
                || !areQueuesEmpty()){
            System.out.println("Time: " + currentTimeTick);

            System.out.println("    " + "Current process running time: " + currentProcessRunningTime);

            // Check for processes to be awoken at current time
            for (int i = 0; i < processList.length; i++){
                if (processList[i].getArrivalTime() == currentTimeTick
                    && processList[i].getRemainingTime() > 0) {
                    FCFSqueue.add(processList[i]);
                    sleepingProcessCount--;
                    System.out.println("    " + processList[i].getProcessName() + " arrives!");
                    if (currentProcessQueueLevel == 3 && currentRunningProcess != null){
                        System.out.println("    " + currentRunningProcess.getProcessName() + " leaves due to preemptiveness.");
                        currentRunningProcess.setQuantum((currentRunningProcess.getQuantum() * 2) - currentProcessRunningTime);
                        SJFqueue.add(currentRunningProcess);
                        unscheduleCurrentProcess();
                    }
                }
            }

            if (currentRunningProcess == null){
                // No process, get process from one of the queues.
                getNextProcess();
            }
            else{
                if (currentProcessRunningTime >= currentRunningProcess.getRemainingTime()){
                    // Process finished
                    System.out.println("    " + currentRunningProcess.getProcessName() + " has finished its task.");
                    currentProcessQueueLevel = 0;
                    currentRunningProcess.setTurnAroundTime(currentTimeTick - currentRunningProcess.getArrivalTime());
                    currentRunningProcess.setQuantum(0);
                    unscheduleCurrentProcess();
                }
                else if (currentProcessRunningTime >= currentRunningProcess.getQuantum()){
                    System.out.println("    " + currentRunningProcess.getProcessName() + " has finished its quantum");
                    switch(currentProcessQueueLevel){
                        case 1:
                            System.out.println("    " + currentRunningProcess.getProcessName() + " goes to NPP queue");
                            currentRunningProcess.setQuantum(currentRunningProcess.getQuantum() + 2);
                            NPPqueue.add(currentRunningProcess);
                            break;
                        case 2:
                        case 3:
                            System.out.println("    " + currentRunningProcess.getProcessName() + " goes to SJF queue");
                            currentRunningProcess.setQuantum(currentRunningProcess.getQuantum());
                            SJFqueue.add(currentRunningProcess);
                            break;
                    }
                    currentProcessQueueLevel = 0;
                    unscheduleCurrentProcess();
                }
                else if (currentProcessQueueLevel != 3 && currentProcessRunningTime >= Math.ceil(currentRunningProcess.getQuantum() * 0.25)){
                    System.out.println("    " + currentRunningProcess.getProcessName() + " has finished its quarter quantum");
                    switch(currentProcessQueueLevel){
                        case 1:
                            System.out.println("    " + currentRunningProcess.getProcessName() + " goes to NPP queue");
                            currentRunningProcess.setQuantum(currentRunningProcess.getQuantum() + 2);
                            NPPqueue.add(currentRunningProcess);
                            break;
                        case 2:
                            System.out.println("    " + currentRunningProcess.getProcessName() + " goes to SJF queue");
                            currentRunningProcess.setQuantum(currentRunningProcess.getQuantum());
                            SJFqueue.add(currentRunningProcess);
                            break;
                    }

                    unscheduleCurrentProcess();
                }
                else{
                    System.out.println("    " + currentRunningProcess.getProcessName() +
                            " continues running!");
                }
            }

            if (goSlow) {
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            printQueues();
            updateWaitingTimes();
            currentTimeTick++;
            currentProcessRunningTime++;
        }
        System.out.println("All processes finished!");
        System.out.println();

        double sumWaiting = 0;
        double sumTurnaround = 0;
        System.out.println("Process summary: ");
        for (Process i : processList){
            sumWaiting += i.getWaitingTime();
            sumTurnaround += i.getTurnAroundTime();
            System.out.println(i.getProcessName() + ": Waiting time: " + i.getWaitingTime() + "; Turnaround time " + i.getTurnAroundTime() + ".");
        }
        System.out.println();

        System.out.println("Average waiting time: " + (sumWaiting/processList.length));
        System.out.println("Average turnaround time: " + (sumTurnaround/processList.length));
        System.out.println();

        System.out.println("Process schedule summary: ");
        for (ProcessScheduleElement i : processSchedule){
            System.out.println(i.process.getProcessName() + " ran from " + i.timeFrom + " to " + i.timeTo + ".");
        }
        System.out.println();

        System.out.println("Process quantum summary: ");
        for (Process i : processList){
            System.out.print(i.getProcessName() + ": ");
            for (int j : quantumTimes.get(i)){
                System.out.print(j);
                System.out.print(", ");
            }
            System.out.println();
        }
        System.out.println();

    }

    private void getNextProcess(){
        if (!FCFSqueue.isEmpty()){
            currentRunningProcess = getFirstProcess();

            if (processSchedule.isEmpty() || processSchedule.get(processSchedule.size() - 1).process != currentRunningProcess){
                processSchedule.add(new ProcessScheduleElement(currentRunningProcess, currentTimeTick));
            }

            currentProcessQueueLevel = 1;
            System.out.println("    " + currentRunningProcess.getProcessName() +
                    " is now running!");
            System.out.println("    " + currentRunningProcess.getProcessName() + " remaining time: " + currentRunningProcess.getRemainingTime());
        }
        else if (!NPPqueue.isEmpty()){
            currentRunningProcess = getHighestPriorityProcess();

            if (processSchedule.isEmpty() || processSchedule.get(processSchedule.size() - 1).process != currentRunningProcess){
                processSchedule.add(new ProcessScheduleElement(currentRunningProcess, currentTimeTick));
            }

            currentProcessQueueLevel = 2;
            System.out.println("    " + currentRunningProcess.getProcessName() +
                    " is now running!");
            System.out.println("    " + currentRunningProcess.getProcessName() + " remaining time: " + currentRunningProcess.getRemainingTime());
        }
        else if (!SJFqueue.isEmpty()){
            currentRunningProcess = getShortestRemainingTimeProcess();

            if (processSchedule.isEmpty() || processSchedule.get(processSchedule.size() - 1).process != currentRunningProcess){
                processSchedule.add(new ProcessScheduleElement(currentRunningProcess, currentTimeTick));
            }

            currentProcessQueueLevel = 3;
            System.out.println("    " + currentRunningProcess.getProcessName() +
                    " is now running!");
            System.out.println("    " + currentRunningProcess.getProcessName() + " remaining time: " + currentRunningProcess.getRemainingTime());
        }
        else{
            System.out.println("    No process running.");
        }
    }

    private void updateWaitingTimes(){
        for (Process p : FCFSqueue){
            p.setWaitingTime(p.getWaitingTime() + 1);
        }
        for (Process p : NPPqueue){
            p.setWaitingTime(p.getWaitingTime() + 1);
        }
        for (Process p : SJFqueue){
            p.setWaitingTime(p.getWaitingTime() + 1);
        }
    }

    private void printQueues(){
        System.out.print("        FCFS queue: ");
        for (Process p : FCFSqueue){
            System.out.print(p.getProcessName() + ", ");
        }
        System.out.println();
        System.out.print("        NPP queue: ");
        for (Process p : NPPqueue){
            System.out.print(p.getProcessName() + ", ");
        }
        System.out.println();
        System.out.print("        SJF queue: ");
        for (Process p : SJFqueue){
            System.out.print(p.getProcessName() + ", ");
        }
        System.out.println();
    }

    private void unscheduleCurrentProcess(){
        if (currentRunningProcess == null) return;
        currentRunningProcess.setRemainingTime(currentRunningProcess.getRemainingTime() - currentProcessRunningTime);
        System.out.println("    " + "Process " + currentRunningProcess.getProcessName() + " exits!");
        System.out.println("    " + currentRunningProcess.getProcessName() + " remaining time: " + currentRunningProcess.getRemainingTime());
        processSchedule.get(processSchedule.size() - 1).timeTo = currentTimeTick;
        quantumTimes.get(currentRunningProcess).add(currentRunningProcess.getQuantum());
        currentRunningProcess = null;
        currentProcessRunningTime = 0;
        getNextProcess();
    }

    private Process getFirstProcess(){
        return FCFSqueue.poll();
    }

    private boolean areQueuesEmpty(){
        return FCFSqueue.isEmpty() && NPPqueue.isEmpty() && SJFqueue.isEmpty();
    }

    private Process getShortestRemainingTimeProcess(){
        if (SJFqueue.isEmpty()) return null;

        int shortestRTIndex = 0;
        for (int i = 1; i < SJFqueue.size(); i++){
            if (SJFqueue.get(i).getRemainingTime() < SJFqueue.get(shortestRTIndex).getRemainingTime()){
                shortestRTIndex = i;
            }
        }
        return SJFqueue.remove(shortestRTIndex);
    }

    private Process getHighestPriorityProcess(){
        if (NPPqueue.isEmpty()) return null;

        int highestPriorityIndex = 0;
        for (int i = 1; i < NPPqueue.size(); i++){
            if (NPPqueue.get(i).getProcessPriority() < NPPqueue.get(highestPriorityIndex).getProcessPriority()){
                highestPriorityIndex = i;
            }
        }
        return NPPqueue.remove(highestPriorityIndex);
    }
}
