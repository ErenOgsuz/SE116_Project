import java.util.ArrayList;

public class Job {
    private String jobID;
    private int startTime;
    private int duration;
    private ArrayList<Task> tasks;
    private int taskIndex;
    private int deadline;

    public Job(String jobID, int startTime, ArrayList<Task> tasks, int taskIndex, int deadline) {
        this.jobID = jobID;
        this.startTime = startTime;
        this.tasks = tasks;
        this.taskIndex = taskIndex;
        this.deadline = deadline;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public Job(){}
}
