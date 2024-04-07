import java.util.ArrayList;

public abstract class JobType {
    private int jobTypeID;
    private ArrayList <Task> tasks;

    public JobType(int jobTypeID, ArrayList<Task> tasks) {
        this.jobTypeID = jobTypeID;
        this.tasks = tasks;
    }

    public JobType() {
        this.jobTypeID = 0;
        this.tasks = new ArrayList<Task>();
    }

    public void jobDurationCalculator(){}
}
