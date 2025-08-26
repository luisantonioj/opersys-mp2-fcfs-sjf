public class Process {
    String processID;
    int arrivalTime;
    int burstTime;
    int waitingTime;
    int turnaroundTime;

    public Process(String pid) {
        this.processID = pid;
    }
}
