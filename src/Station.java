import java.util.ArrayList;

public class Station {
    private int stationID;
    private int maxCapacity;
    private boolean multiFlag;
    private boolean fifoFlag;
    private ArrayList<Task> tasks;

    public Station(int stationID, int maxCapacity, boolean multiFlag, boolean fifoFlag, ArrayList<Task> tasks) {
        this.stationID = stationID;
        this.maxCapacity = maxCapacity;
        this.fifoFlag = fifoFlag;
        this.multiFlag = multiFlag;
        this.tasks = tasks;
    }
}
