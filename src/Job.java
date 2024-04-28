public class Job {
    private String jobId;
    private String jobTypeId; //may be changed to "Has-a"jobType
    private int startTime;
    private int duration;
    private int deadline;

    public Job(String jobId, String jobTypeId, int startTime, int duration) {
        this.jobId = jobId;
        this.jobTypeId = jobTypeId;
        this.startTime = startTime;
        this.duration = duration;
        this.deadline = startTime + duration; // Calculate the deadline
    }

    // Getter methods
    public String getJobId() {
        return jobId;
    }

    public String getJobTypeId() {
        return jobTypeId;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getDuration() {
        return duration;
    }

    public int getDeadline() {
        return deadline;
    }
}
