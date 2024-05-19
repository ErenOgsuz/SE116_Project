import java.util.ArrayList;

public class JobType {
    private String jobTypeID;
    private ArrayList<Task> tasks;
    private int taskIndex=0;

    public JobType(String jobTypeID, ArrayList<Task> tasks) {
        this.jobTypeID = jobTypeID;
        this.tasks = tasks;
    }

    public JobType(){}

    public int getTaskIndex() {
        return taskIndex;
    }

    public void increaseTaskIndex(){
        this.taskIndex++;
    }

    public String getJobTypeID() {
        return jobTypeID;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

}
