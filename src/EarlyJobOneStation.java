import java.util.ArrayList;

public class EarlyJobOneStation extends Station {

    public EarlyJobOneStation(String stationID, ArrayList<Task> tasks,int capacity) {
        super(stationID,tasks,capacity);
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
