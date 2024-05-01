import java.time.LocalTime;
import java.util.ArrayList;

public class FifoMultiStation extends Station{
    private String stationID;
    private int maxCapacity;
    private boolean state;
    private int speed;
    private ArrayList<Task> tasks;
    private ArrayList<Task> targetTasks;
    public boolean[] desks;

    public FifoMultiStation(String stationID, ArrayList<Task> tasks,int capacity){
        super(stationID,tasks,capacity);
    }

    public void addTask(Task task){
        getTargetTasks().add(task);
        task.setDuration(calculateDuration(task));
    }
    public LocalTime calculateStartTimePossibility(Task task){
        long minuteForStart=0L;
        for(Task task1: getTargetTasks()){
            minuteForStart+=(long)task1.getDuration();
        }
        LocalTime currentTime = LocalTime.now();
        LocalTime startTime = currentTime.plusMinutes(minuteForStart);
        task.setStartTime(startTime);
        return currentTime;
    }
    public void setTargetTasks(ArrayList<Task> targetTasks) {this.targetTasks = targetTasks;}
    public void displayTheState(FifoMultiStation s){
        if (s.getState()) {
            System.out.println("The state of the station is active.");
        }
        else {
            System.out.println("The state of the station is deactivated.");
        }
    }
    public void nextTask(FifoMultiStation s, int currenttask){
        if (currenttask < s.getTasks().size() - 1) {
            currenttask++;
            s.setCurrenttask(currenttask);
            ArrayList<Task> t = new ArrayList<>(s.getTasks());
            String nextTask = t.get(currenttask).getTaskTypeID();
        } else {
            System.out.println("All the tasks are completed.");
        }
    }
    public void displayExecutingTasks(FifoMultiStation ss){
        System.out.println("Tasks being executed at early job multistation:");
        for (Task s : ss.getTasks()) {
            String stationId = s.getStation().getStationID();
            int x = ss.getCurrenttask();
            System.out.println("Station" + stationId + ": " + x);
        }
    }
}
