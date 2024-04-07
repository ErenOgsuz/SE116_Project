import java.util.ArrayList;

public class Job {
    private String jobID;
    private int startTime;
    private int duration;
    private ArrayList<Task> tasks;
    private int taskIndex;
    private Station station;
    private int deadline;
    private String status;

    public Job(String jobID, int startTime, int duration, ArrayList<Task> tasks, int taskIndex, Station station, int deadline, String status) {
        this.jobID = jobID;
        this.startTime = startTime;
        this.duration = duration;
        this.tasks = tasks;
        this.taskIndex = taskIndex;
        this.station = station;
        this.deadline = deadline;
        this.status = status;
    }
}
