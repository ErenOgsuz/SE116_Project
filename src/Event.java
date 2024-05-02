import java.time.LocalTime;

public class Event {
    private String eventType;
    private Station station;
    private LocalTime deadLine;
    private LocalTime taskStartTime;
    private LocalTime eventStartTime;
    private Job job;
    private JobType JobType;
    private Task task;

    public Event(Job job,JobType jobType,LocalTime jobStartTime){
            eventType="JobStarting";
            this.job=job;
            this.eventStartTime=jobStartTime;
    }

    public Event(Job job, JobType jobType, Task task, Station station, LocalTime deadLine, LocalTime eventStartTime, LocalTime jobStartTime){
        eventType="TaskStarting";
        this.deadLine=deadLine;
        this.job=job;
        this.JobType=jobType;
        this.task=task;
        this.station=station;
        this.eventStartTime=eventStartTime;
        this.eventStartTime=jobStartTime;
    }
    public JobType getJobType() {
        return JobType;
    }

    public void setJobType(JobType jobType) {
        JobType = jobType;
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
    public LocalTime getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalTime deadLine) {
        this.deadLine = deadLine;
    }

    public LocalTime getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(LocalTime eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public LocalTime getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(LocalTime taskStartTime) {
        this.taskStartTime = taskStartTime;
    }
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
