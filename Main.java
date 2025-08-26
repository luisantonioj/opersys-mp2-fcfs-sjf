import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        boolean newSet = true; // controls whether to input new processes
        System.out.println("CPU Scheduling\n");
        while (newSet) {
            // Step 1: Input number of processes
            int n = 0;
            boolean validN = false;
            while (!validN) {
                System.out.print("Enter the no. of process (3-10): ");
                String input = sc.next();

                boolean isNumeric = input.chars().allMatch(Character::isDigit);

                if (isNumeric) {
                    n = Integer.parseInt(input);
                    if (n >= 3 && n <= 10) {
                        validN = true;
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
                boolean validPID = false;
                while (!validPID) {
                    System.out.print("Enter process ID for Process " + (i + 1) + ": ");
                    String pid = sc.next().trim();

                    if (pid.isEmpty()) {
                        System.out.println("Invalid input. Process ID cannot be empty.");
                    } else if (usedProcessIDs.contains(pid)) {
                        System.out.println("Invalid input. Process ID must be unique.");
                    } else {
                        processes[i] = new Process(pid);
                        usedProcessIDs.add(pid);
                        validPID = true;
                    }
                }
            }

            // Step 3: Input Arrival Times
            for (int i = 0; i < n; i++) {
                boolean validAT = false;
                while (!validAT) {
                    System.out.print("Enter arrival time for " + processes[i].processID + ": ");

                    if (sc.hasNextInt()) {
                        int at = sc.nextInt();
                        if (at < 0) {
                            System.out.println("Invalid input. Arrival time must be 0 or greater.");
                        } else {
                            processes[i].arrivalTime = at;
                            validAT = true;
                        }
                    } else {
                        System.out.println("Invalid input. Please enter a valid integer.");
                        sc.next();
                    }
                }
            }

            // Step 4: Input Burst Times
            for (int i = 0; i < n; i++) {
                boolean validBT = false;
                while (!validBT) {
                    System.out.print("Enter burst time for " + processes[i].processID + ": ");
                    if (sc.hasNextInt()) {
                        int bt = sc.nextInt();
                        if (bt <= 0) {
                            System.out.println("Invalid input. Burst time must be a positive integer.");
                        } else {
                            processes[i].burstTime = bt;
                            validBT = true;
                        }
                    } else {
                        System.out.println("Invalid input. Please enter a valid integer.");
                        sc.next();
                    }
                }
            }

            // Step 5: Run algorithms in a loop until user declines
            boolean tryAnotherAlgorithm = true;
            while (tryAnotherAlgorithm) {
                // Choose algorithm
                Scheduler scheduler = null;
                boolean validChoice = false;
                while (!validChoice) {
                    System.out.print("\nSelect the CPU scheduling algorithm (1-FCFS, 2-SJF): ");
                    String choice = sc.next();

                    if (choice.equals("1")) {
                        scheduler = new FCFS();
                        validChoice = true;
                    } else if (choice.equals("2")) {
                        scheduler = new SJF();
                        validChoice = true;
                    } else {
                        System.out.println("Invalid choice. Please enter 1 or 2.");
                    }
                }

                // Clone processes (so re-running doesn’t overwrite previous results)
                Process[] clonedProcesses = new Process[n];
                for (int i = 0; i < n; i++) {
                    clonedProcesses[i] = new Process(processes[i].processID);
                    clonedProcesses[i].arrivalTime = processes[i].arrivalTime;
                    clonedProcesses[i].burstTime = processes[i].burstTime;
                }

                scheduler.schedule(clonedProcesses);

                // Step 6: Display Process Table
                int totalWT = 0, totalTAT = 0;
                System.out.printf("\n%-10s %-15s %-15s %-15s %-15s\n",
                        "Process", "Arrival Time", "Burst Time", "Waiting Time", "Turnaround Time");

                for (Process p : clonedProcesses) {
                    System.out.printf("%-10s %-15d %-15d %-15d %-15d\n",
                            p.processID, p.arrivalTime, p.burstTime, p.waitingTime, p.turnaroundTime);
                    totalWT += p.waitingTime;
                    totalTAT += p.turnaroundTime;
                }

                // Step 7: Display Averages
                System.out.println("\nAverage waiting time: " + (float) totalWT / n);
                System.out.println("Average turn-around time: " + (float) totalTAT / n);

                // Ask if user wants another algorithm
                System.out.print("\nDo you want to try another algorithm with the same set? (y/n): ");
                String again = sc.next();
                tryAnotherAlgorithm = again.equalsIgnoreCase("y");
            }

            // Ask if user wants a new set
            System.out.print("\nDo you want to enter a new set of processes? (y/n): ");
            String newSetChoice = sc.next();
            newSet = newSetChoice.equalsIgnoreCase("y");
        }

        System.out.println("\nProgram terminated.");
        sc.close();
    }
}
