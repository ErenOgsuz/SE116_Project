public class Task {
    private String taskTypeID;
    private double size;
    private Station station;
    private Job jobType;
    private int startTime;
    private int endTime;

    public Task(String taskTypeID){
        this.taskTypeID=taskTypeID;
        this.size=0.0;
        this.startTime=0;
        this.endTime=0;
        this.station=new Station();
        this.jobType=new Job();

    }

    public String getTaskTypeID() {
        return taskTypeID;
    }

    public void setTaskTypeID(String taskTypeID) {
        this.taskTypeID = taskTypeID;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Job getJobType() {
        return jobType;
    }

    public void setJobType(Job jobType) {
        this.jobType = jobType;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
}
