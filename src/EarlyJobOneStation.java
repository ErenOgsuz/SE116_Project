import java.util.ArrayList;

public class EarlyJobOneStation extends Station {

    private String stationID;
    private int maxcapacity;
    private boolean multiflag;
    private boolean fifoflag;

    public EarlyJobOneStation(String stationID, int maxCapacity, boolean multiFlag, boolean fifoFlag) {
        this.stationID = stationID;
        this.maxcapacity = maxCapacity;
        this.multiflag = multiFlag;
        this.fifoflag = fifoFlag;
    }

    @Override
    public String getStationID() {
        return stationID;
    }

    @Override
    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public int getMaxcapacity() {
        return maxcapacity;
    }

    public void setMaxcapacity(int maxcapacity) {
        this.maxcapacity = maxcapacity;
    }

    public boolean isMultiflag() {
        return multiflag;
    }

    public void setMultiflag(boolean multiflag) {
        this.multiflag = multiflag;
    }

    public boolean isFifoflag() {
        return fifoflag;
    }

    public void setFifoflag(boolean fifoflag) {
        this.fifoflag = fifoflag;
    }

    public void addTask(Task task){}
    public void calculateStartTime(Task task) {
        double starttime = 0;
        for (int i = 0; i < task.getStation().getTasks().get(i).getDuration(); i++) {
            starttime += task.getStation().getTasks().get(i).getDuration();
        }
        System.out.println("Start time for the job is: " + starttime);
    }
    public void displayTheState(EarlyJobOneStation s){
        if (s.getState()) {
            System.out.println("The state of the station is active.");
        }
        else {
            System.out.println("The state of the station is deactivated.");
        }
    }
    public void nextTask(EarlyJobOneStation s, int currenttask){
        if (currenttask < s.getTasks().size() - 1) {
            currenttask++;
            s.setCurrenttask(currenttask);
            ArrayList<Task> t = new ArrayList<>(s.getTasks());
            String nextTask = t.get(currenttask).getTaskTypeID();
        } else {
            System.out.println("All the tasks are completed.");
        }
    }
    public void displayExecutingTasks(EarlyJobOneStation ss){
        System.out.println("Tasks being executed at early job multistation:");
        for (Task s : ss.getTasks()) {
            String stationId = s.getStation().getStationID();
            int x = ss.getCurrenttask();
            System.out.println("Station" + stationId + ": " + x);
        }
    }
}
