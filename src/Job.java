import java.time.LocalTime;
import java.time.Duration;
import java.util.ArrayList;


public class Job {
    private final String jobId;
    private final JobType jobType; // "Has-a"jobType
    private final double startTime;
    private final double duration;
    private double deadline;
    private String state;
    private Task executingTask;
    private Task waitingToExecute;
    private double delayTime;

    public void display() {
        System.out.println("Job ID: " + getJobId() + "\nJop Type: " + getJobType() + "\nStarting time: " + getStartTime()
                + "\nDuration: " + getDuration() + "\nEnding time: " + getDeadline() + "\nState: " + getState() + "\nCurrent executing task: "
                + getExecutingTask().getTaskTypeID() + "\nNext task to execute: " + getWaitingToExecute().getTaskTypeID());
        if (getDelayTime() > 0) {
            System.out.println("There is " + getDelayTime() + " seconds of delay.");
        }
        else {
            System.out.println("Task is executed in its time.");
        }
    }

    public Job(String jobId, JobType jobType, double startTime, double duration) {
        this.jobId = jobId;
        this.jobType = jobType;
        this.startTime = startTime;
        this.duration = duration;
        this.state="Waiting..";
    }

    // Getter methods
    public String getJobId() {
        return jobId;
    }

    public JobType getJobType() {
        return jobType;
    }

    public double getStartTime() {
        return startTime;
    }

    public double getDuration() {
        return duration;
    }

    public double getDeadline(){
        return deadline;
    }

    public void setState(double time) {
        if(time < getStartTime()){
            this.state = "Waiting..";
        }
        else if (time > getStartTime() && time < getStartTime() + getDuration()) {
            this.state = "Executing..";
        }
        else if (time >= getStartTime() + getDuration()) {
            this.state = "Executed..";
        }
        int amountofdone = 0;
        for(Task task: jobType.getTasks()) {
            if(task.getState().contains("Done.")) {
                amountofdone++;
            }
        }
        if(amountofdone == jobType.getTasks().size()) {
            state = "All tasks are finished.";
        }
    }
    public String getState(){
        return state;
    }

    public void setExecutingTask(){
        for(Task task:jobType.getTasks()){
            if(task.getState().contains("Executing..")){
                executingTask=task;
                break;
            }
            else {
                executingTask = jobType.getTasks().get(0);
            }
        }
    }
    public Task getExecutingTask(){
        for (Task t: this.jobType.getTasks()) {
            if (t.getState().equals("Executing..")) {
                this.executingTask = t;
            }
        }
        return executingTask;
    }

    public void setWaitingToExecute(){
        int size = jobType.getTasks().size();
        for (int i = 0; i < size; i++) {
            Task task = jobType.getTasks().get(i);
            if (task.getState().contains("Executing..")) {
                waitingToExecute =jobType.getTasks().get(i+1) ;
                break;
            }
        }
    }

    public Task getWaitingToExecute(){
        int indexOfCurrent = executingTask.getJobType().getTasks().indexOf(executingTask);
        waitingToExecute = executingTask.getJobType().getTasks().get(indexOfCurrent + 1);
        return waitingToExecute;
    }

    public double getDelayTime(){
        ArrayList<Task> jobTasks = jobType.getTasks();
        delayTime = jobTasks.get(jobTasks.size() - 1).getFinishTime()-this.deadline;
        return delayTime;
    }
    public void isJobExecuting(ArrayList<Job> jobs) {
        for (Job j: jobs) {
            if (j.getState().equals("Waiting..")) {
                System.out.println("Job " + j.getJobId() + " is waiting to be executed.");
                if (jobs.indexOf(j) - 1 >= 0) System.out.println("Previous job " + jobs.get(jobs.indexOf(j) - 1).getJobId() + "is executed.");
            }
            else if (j.getState().equals("Executing..")) {
                System.out.println("Job " + j.getJobId() + "is being executed.");
            }
        }
    }
}
