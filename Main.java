import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Step 1: Input number of processes
        int n;
        while (true) {
            System.out.print("Enter the no. of process (3-10): ");
            String input = sc.next();

            boolean isNumeric = input.chars().allMatch(Character::isDigit);

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
                System.out.print("Enter process ID for Process " + (i + 1) + ": ");
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
        for (int i = 0; i < n; i++) {
            while (true) {
                System.out.print("Enter arrival time for " + processes[i].processID + ": ");

                if (sc.hasNextInt()) {
                    int at = sc.nextInt();
                    if (at < 0) {
                        System.out.println("Invalid input. Arrival time must be 0 or greater.");
                    } else {
                        processes[i].arrivalTime = at;
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

        // Step 5: Choose algorithm
        Scheduler scheduler = null;
        while (scheduler == null) {
            System.out.print("\nSelect the CPU scheduling algorithm (1-FCFS, 2-SJF): ");
            String choice = sc.next();

            if (choice.equals("1")) {
                scheduler = new FCFS();
            } else if (choice.equals("2")) {
                scheduler = new SJF();
            } else {
                System.out.println("Invalid choice. Please enter 1 or 2.");
            }
        }

        scheduler.schedule(processes);

        // Step 6: Display Process Table
        int totalWT = 0, totalTAT = 0;
        System.out.println("\nProcess\tArrival Time\tBurst Time\tWaiting Time\tTurnaround Time");
        for (Process p : processes) {
            System.out.println(p.processID + "\t" + p.arrivalTime + "\t\t" +
                    p.burstTime + "\t\t" +
                    p.waitingTime + "\t\t" +
                    p.turnaroundTime);
            totalWT += p.waitingTime;
            totalTAT += p.turnaroundTime;
        }

        // Step 7: Display Averages
        System.out.println("\nAverage waiting time: " + (float) totalWT / n);
        System.out.println("Average turn-around time: " + (float) totalTAT / n);

        sc.close();
    }
}
