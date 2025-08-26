import java.util.*;

public class SJF implements Scheduler {
    @Override
    public void schedule(Process[] processes) {
        // Sort by arrival time first
        Arrays.sort(processes, (a, b) -> {
            if (a.arrivalTime == b.arrivalTime) {
                return a.processID.compareTo(b.processID);
            }
            return a.arrivalTime - b.arrivalTime;
        });

        int n = processes.length;
        boolean[] done = new boolean[n];
        int completed = 0, currentTime = 0;

        while (completed < n) {
            int idx = -1;
            int minBT = Integer.MAX_VALUE;

            // Find process with smallest burst time among arrived processes
            for (int i = 0; i < n; i++) {
                if (!done[i] && processes[i].arrivalTime <= currentTime) {
                    if (processes[i].burstTime < minBT) {
                        minBT = processes[i].burstTime;
                        idx = i;
                    } else if (processes[i].burstTime == minBT) {
                        // tie-breaker: earlier arrival or smaller ID
                        if (processes[i].arrivalTime < processes[idx].arrivalTime ||
                                (processes[i].arrivalTime == processes[idx].arrivalTime &&
                                        processes[i].processID.compareTo(processes[idx].processID) < 0)) {
                            idx = i;
                        }
                    }
                }
            }

            if (idx == -1) {
                currentTime++; // no process has arrived yet
                continue;
            }

            // Execute the chosen process
            int startTime = Math.max(currentTime, processes[idx].arrivalTime);
            int completionTime = startTime + processes[idx].burstTime;

            processes[idx].turnaroundTime = completionTime - processes[idx].arrivalTime;
            processes[idx].waitingTime = processes[idx].turnaroundTime - processes[idx].burstTime;

            currentTime = completionTime;
            done[idx] = true;
            completed++;
        }
    }
}