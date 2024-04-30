import java.util.ArrayList;

public class EarlyJobMultiStation extends Station{

    private String stationID;
    private int maxcapacity;
    private boolean multiflag;
    private boolean fifoflag;

    public EarlyJobMultiStation(String stationID, int maxCapacity, boolean multiFlag, boolean fifoFlag) {
        this.stationID = stationID;
        this.maxcapacity = maxCapacity;
        this.multiflag = multiFlag;
        this.fifoflag = fifoFlag;
    }
    public void calculateStartTime(Task task) {
        double starttime = 0;
        for (int i = 0; i < task.getStation().getTasks().get(i).getDuration(); i++) {
            starttime += task.getStation().getTasks().get(i).getDuration();
        }
        System.out.println("Start time for the job is: " + starttime);
    }
    public void displayTheState(EarlyJobMultiStation s){
        if (s.getState()) {
            System.out.println("The state of the station is active.");
        }
        else {
            System.out.println("The state of the station is deactivated.");
        }
    }
    public void nextTask(EarlyJobMultiStation s, int currenttask){
        if (currenttask < s.getTasks().size() - 1) {
            currenttask++;
            s.setCurrenttask(currenttask);
            ArrayList<Task> t = new ArrayList<>(s.getTasks());
            String nextTask = t.get(currenttask).getTaskTypeID();
        } else {
            System.out.println("All the tasks are completed.");
        }
    }
    public void displayExecutingTasks(EarlyJobMultiStation ss){
        System.out.println("Tasks being executed at early job multistation:");
        for (Task s : ss.getTasks()) {
            String stationId = s.getStation().getStationID();
            int x = ss.getCurrenttask();
            System.out.println("Station" + stationId + ": " + x);
        }
    }
}
