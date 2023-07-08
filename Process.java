import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Process {
    private int processId;
    private int arrivalTime;
    private int burstTime;
    private int remainingTime;

    public Process(int processId, int arrivalTime, int burstTime) {
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }

    public int getProcessId() {
        return processId;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }
}

class Scheduler {
    private int quantum;
    private List<Process> processes;
    private Queue<Process> readyQueue;

    public Scheduler(int quantum) {
        this.quantum = quantum;
        this.processes = new ArrayList<>();
        this.readyQueue = new LinkedList<>();
    }

    public void addProcess(Process process) {
        processes.add(process);
    }

    public void executeRoundRobin() {
        int time = 0;

        while (!readyQueue.isEmpty() || !processes.isEmpty()) {
            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();

                int executionTime = Math.min(currentProcess.getRemainingTime(), quantum);

                for (int i = 0; i < executionTime; i++) {
                    System.out.println("Time " + time + ": Executing process " + currentProcess.getProcessId());
                    time++;
                }

                currentProcess.setRemainingTime(currentProcess.getRemainingTime() - executionTime);

                if (currentProcess.getRemainingTime() == 0) {
                    System.out.println("Time " + time + ": Process " + currentProcess.getProcessId() + " completed");
                } else {
                    readyQueue.offer(currentProcess);
                }
            }

            while (!processes.isEmpty() && processes.get(0).getArrivalTime() <= time) {
                Process process = processes.remove(0);
                readyQueue.offer(process);
            }

            if (readyQueue.isEmpty()) {
                time++;
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler(4); 

        Process p1 = new Process(1, 0, 12);
        Process p2 = new Process(2, 2, 6);
        Process p3 = new Process(3, 4, 8);
        Process p4 = new Process(4, 6, 10);

        scheduler.addProcess(p1);
        scheduler.addProcess(p2);
        scheduler.addProcess(p3);
        scheduler.addProcess(p4);

        scheduler.executeRoundRobin();
    }
}
