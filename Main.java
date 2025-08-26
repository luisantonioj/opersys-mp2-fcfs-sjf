import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

class Process {
    String processID;
    int arrivalTime;
    int burstTime;
    int waitingTime;
    int turnaroundTime;

    Process(String pid) {
        processID = pid;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Step 1: Input number of processes
        int n;
        while (true) {
            System.out.print("Enter the no. of process (3-10): ");
            String input = sc.next();

            boolean isNumeric = true;
            for (char c : input.toCharArray()) {
                if (!Character.isDigit(c)) {
                    isNumeric = false;
                    break;
                }
            }

            if (isNumeric) {
                n = Integer.parseInt(input);
                if (n >= 3 && n <= 10) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number between 3 and 10.");
                }
            } else {
                System.out.println("Invalid input. Only positive integers are allowed.");
            }
        }

        // Step 2: Input Process IDs
        Process[] processes = new Process[n];
        Set<String> usedProcessIDs = new HashSet<>();

        for (int i = 0; i < n; i++) {
            while (true) {
                System.out.print("Enter process ID for Process" + (i + 1) + ": ");
                String pid = sc.next().trim();

                if (pid.isEmpty()) {
                    System.out.println("Invalid input. Process ID cannot be empty.");
                } else if (usedProcessIDs.contains(pid)) {
                    System.out.println("Invalid input. Process ID must be unique.");
                } else {
                    processes[i] = new Process(pid);
                    usedProcessIDs.add(pid);
                    break;
                }
            }
        }

        // Step 3: Input Arrival Times
        Set<Integer> usedArrivalTimes = new HashSet<>();
        for (int i = 0; i < n; i++) {
            while (true) {
                System.out.print("Enter arrival time for " + processes[i].processID + ": ");

                if (sc.hasNextInt()) {
                    int at = sc.nextInt();

                    if (at < 0) {
                        System.out.println("Invalid input. Arrival time must be 0 or greater.");
                    } else {
                        processes[i].arrivalTime = at;
                        usedArrivalTimes.add(at);
                        break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid integer.");
                    sc.next();
                }
            }
        }

        // Step 4: Input Burst Times
        for (int i = 0; i < n; i++) {
            while (true) {
                System.out.print("Enter burst time for " + processes[i].processID + ": ");

                if (sc.hasNextInt()) {
                    int bt = sc.nextInt();

                    if (bt <= 0) {
                        System.out.println("Invalid input. Burst time must be a positive integer.");
                    } else {
                        processes[i].burstTime = bt;
                        break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid integer.");
                    sc.next();
                }
            }
        }

        // Step 5: Compute Scheduling (FCFS)
        int totalWT = 0, totalTAT = 0;
        int currentTime = 0;

        // sort by arrival time (FCFS)
        java.util.Arrays.sort(processes, (a, b) -> a.arrivalTime - b.arrivalTime);

        for (int i = 0; i < n; i++) {
            int startTime = Math.max(currentTime, processes[i].arrivalTime);
            int completionTime = startTime + processes[i].burstTime;

            processes[i].turnaroundTime = completionTime - processes[i].arrivalTime;
            processes[i].waitingTime = processes[i].turnaroundTime - processes[i].burstTime;

            totalWT += processes[i].waitingTime;
            totalTAT += processes[i].turnaroundTime;

            currentTime = completionTime;
        }

        // Step 6: Display Process Table
        System.out.println("\nProcess\tArrival Time\tBurst Time\tWaiting Time\tTurnaround Time");
        for (Process p : processes) {
            System.out.println(p.processID + "\t" + p.arrivalTime + "\t\t" + p.burstTime + "\t\t" + p.waitingTime + "\t\t" + p.turnaroundTime);
        }

        // Step 7: Display Averages
        System.out.println("\nAverage waiting time: " + (float) totalWT / n);
        System.out.println("Average turn-around time: " + (float) totalTAT / n);

        sc.close();
    }
}