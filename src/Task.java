public class Task {
    private String taskTypeID;
    private double size;
    private Station station;
    private int speed;

    public Task(String taskTypeID){
        this.taskTypeID=taskTypeID;
        this.size=0.0;
        this.station=new Station();
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
}
