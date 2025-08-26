import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

class Process {
    String processID;
    int waitingTime;
    int burstTime;
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
                } else if (usedProcessIDs.contains(pid)){
                    System.out.println("Invalid input. Process ID must be unique.");
                } else {
                    processes[i] = new Process(pid);
                    usedProcessIDs.add(pid);
                    break;
                }
            }
        }

        // Step 3: Input Waiting Times
        java.util.Set<Integer> usedWaitingTimes = new java.util.HashSet<>();
        for (int i = 0; i < n; i++) {
            while (true) {
                System.out.print("Enter waiting time for " + processes[i].processID + ": ");

                if (sc.hasNextInt()) {
                    int wt = sc.nextInt();

                    if (wt < 0) {
                        System.out.println("Invalid input. Waiting time must be 0 or greater.");
                    } else if (usedWaitingTimes.contains(wt)) {
                        System.out.println("Invalid input. Waiting time must not be repeated.");
                    } else {
                        processes[i].waitingTime = wt;
                        usedWaitingTimes.add(wt);
                        break; // ✅ valid input, exit loop
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid integer.");
                    sc.next(); // discard invalid token
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

        // Step 5: Compute Turnaround Times
        int totalWT = 0, totalTAT = 0;
        for (int i = 0; i < n; i++) {
            processes[i].turnaroundTime = processes[i].waitingTime + processes[i].burstTime;
            totalWT += processes[i].waitingTime;
            totalTAT += processes[i].turnaroundTime;
        }

        // Step 6: Display Process Table
        System.out.println("\nProcess\tWaiting Time\tBurst Time\tTurnaround Time");
        for (Process p : processes) {
            System.out.println(p.processID + "\t" + p.waitingTime + "\t\t" + p.burstTime + "\t\t" + p.turnaroundTime);
        }

        // Step 7: Display Averages
        System.out.println("\nAverage waiting: " + (float) totalWT / n);
        System.out.println("Average turn-around time: " + (float) totalTAT / n);

        sc.close();
    }
}