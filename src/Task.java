import java.time.LocalTime;

public class Task {
    private String taskTypeID;
    private double size;
    private Station station;
    private double duration;

    private LocalTime startTime;

    public Task(String taskTypeID) {
        this.taskTypeID=taskTypeID;
        this.size=0.0;
    }

    public Task() {
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

    public double getDuration() {return duration;}

    public void setDuration(double duration) {this.duration = duration;}
    public LocalTime getStartTime() {return startTime;}

    public void setStartTime(LocalTime startTime) {this.startTime = startTime;}

}
