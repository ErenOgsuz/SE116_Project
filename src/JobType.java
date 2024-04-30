import java.util.ArrayList;

public class JobType {
    private String jobTypeID;
    private int startTime;
    private int duration;
    private ArrayList<Task> tasks;
    private int taskIndex;
    private int deadline;

    public JobType(String jobTypeID, ArrayList<Task> tasks) {
        this.jobTypeID = jobTypeID;
        this.tasks = tasks;
    }

    public JobType(String jobTypeID){
        this.jobTypeID=jobTypeID;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public String getJobID() {
        return jobTypeID;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public JobType(){}
}