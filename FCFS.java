import java.lang.reflect.Array;
import java.util.Arrays;

public class FCFS implements Scheduler {
    @Override
    public void schedule(Process[] processes) {
        // Sort by arrival time (FCFS rule)
        Arrays.sort(processes, (a, b) -> {
            if (a.arrivalTime == b.arrivalTime) {
                return a.processID.compareTo(b.processID);
            }
            return a.arrivalTime - b.arrivalTime;
        });

        int currentTime = 0;
        for (Process p : processes) {
            int startTime = Math.max(currentTime, p.arrivalTime);
            int completionTime = startTime + p.burstTime;

            p.turnaroundTime = completionTime - p.arrivalTime;
            p.waitingTime = p.turnaroundTime - p.burstTime;

            currentTime = completionTime;
        }
    }
}