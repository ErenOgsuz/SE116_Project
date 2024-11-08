import java.time.Duration;
import java.time.LocalTime;

public class Task {
    private String taskTypeID;
    private double size;
    private double speed = 1.0;
    private double plusMinus= 0.0;
    private Station station; // it will assign at the taskScheduler.
    private double duration;
    private double startTime; // it will assign when the task picked

    private double finishTime; // it will assign when the task picked
    private String state;

    private Job job; // it will assign in TaskScheduler to create event
    private JobType jobType; // it will assign in TaskScheduler to create event

    public Task(String taskTypeID){
        this.taskTypeID=taskTypeID;
        this.size=0.0;
        this.state="Waiting..";
    }

    public Task(String taskTypeID, double size){
        this.taskTypeID=taskTypeID;
        this.size=size;
        this.state="Waiting..";
    }

    public Task(String taskTypeID, double size,double plusMinus){
        this.taskTypeID=taskTypeID;
        this.size=size;
        this.state="Waiting..";
        this.plusMinus=plusMinus;
    }

    public Task() {
    }

    public String getTaskTypeID() {
        return taskTypeID;
    }
    public double getSize() {
        return size;
    }
    public void setSize(double size) {this.size = size;}
    public Station getStation() {
        return station;
    }
    public void setStation(Station station) {
        this.station = station;
    }
    public double getDuration() {return duration;}
    public void setDuration(double duration) {this.duration = duration;}
    public void setStartTime(double startTime) {this.startTime = startTime;}
    public double getStarTime(){
        return  startTime;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public double getPlusMinus() {
        return plusMinus;
    }
    public void setPlusMinus(double plusMinus) {
        this.plusMinus = plusMinus;
    }
    public double getFinishTime(){
        return  finishTime;
    }
    public void setFinishTime(double finishTime) {
        this.finishTime = finishTime;
    }
    public void setStateDone() {
        this.state="Done.";
    }
    public void setStateExecuting(){
        this.state="Executing..";
    }
    public String getState() {return state;}
    public JobType getJobType() {
        return jobType;
    }
    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }
    public Job getJob() {
        return job;
    }
    public void setJob(Job job) {
        this.job = job;
    }
}
