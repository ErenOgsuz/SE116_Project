public class Task extends TaskType {
    private Station station;
    private int startTime;
    private int endTime;

    public Task(Station station, int startTime, int endTime) {
        this.station = station;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Task(int taskTypeID, int size, Station station, int startTime, int endTime) {
        super(taskTypeID, size);
        this.station = station;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
