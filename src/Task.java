import java.time.Duration;
import java.time.LocalTime;

public class Task {
    private String taskTypeID;
    private double size;
    private Station station;
    private Duration duration;
    private LocalTime startTime;
    private LocalTime finishTime;
    private String state;

    public Task(String taskTypeID){
        this.taskTypeID=taskTypeID;
        this.size=0.0;
        this.state="Waiting..";
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

    public Duration getDuration() {return duration;}

    public void setDuration(Duration duration) {this.duration = duration;}
    public LocalTime getStartTime() {return startTime;}

    public void setStartTime(LocalTime startTime) {this.startTime = startTime;}
    public LocalTime getStarTime(){
        return  startTime;
    }
    public void setFinishTime(){
        finishTime=startTime.plus(duration);
    }
    public LocalTime getFinishTime(){
        return  finishTime;
    }
    public void setStateDone() {
        this.state="Done.";
    }
    public void setStateExecuting(){
        this.state="Executing..";
    }
    public String getState() {return state;}


}
