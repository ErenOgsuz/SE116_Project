import java.time.LocalTime;

public class Event {
    private String eventType;
    private Station station;
    private double deadLine;
    private Job job;
    private JobType jobType;
    private Task task;

    public Event(Job job,JobType jobType,double time, String eventType){
            this.eventType=eventType;
            this.job=job;
            this.deadLine=time;
            this.jobType = jobType;
    }

    public Event(Task task, double time, String eventType){
        this.eventType=eventType; // "TaskStarting" or "TaskFinished"
        this.deadLine=time;
        this.task=task;
        this.station=task.getStation();
    }

    public Event(double time, String eventType){
        this.deadLine = time;
        this.eventType = eventType;
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

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
