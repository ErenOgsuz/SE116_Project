import java.time.LocalTime;

public class Event {
    private String eventType;
    private Station station;
    private double deadLine;
    private double taskStartTime;
    private double eventStartTime;
    private Job job;
    private JobType jobType;
    private Task task;

    public Event(Job job,JobType jobType,double jobStartTime){
            eventType="JobStarting";
            this.job=job;
            this.eventStartTime=jobStartTime;
            this.jobType = jobType;
    }

    public Event(Job job, JobType jobType, Task task, Station station, double deadLine, double eventStartTime){
        eventType="TaskStarting";
        this.deadLine=deadLine;
        this.job=job;
        this.jobType=jobType;
        this.task=task;
        this.station=station;
        this.eventStartTime=eventStartTime;
    }
    public JobType getJobType() {
        return jobType;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        job = job;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }
    public double getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(double deadLine) {
        this.deadLine = deadLine;
    }

    public double getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(double eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public double getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(double taskStartTime) {
        this.taskStartTime = taskStartTime;
    }
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
