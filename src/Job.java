import java.util.ArrayList;

public class Job {
    private String jobID;
    private int startTime;
    private int duration;
    private ArrayList<Task> tasks;
    private int taskIndex;
    private int deadline;

    public Job(String jobID, ArrayList<Task> tasks) {
        this.jobID = jobID;
        this.tasks = tasks;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public String getJobID() {
        return jobID;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public Job(){}
}
